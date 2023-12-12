package szu.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import szu.common.service.MinioService;
import szu.common.service.RedisService;
import szu.dao.*;
import szu.model.Partition;
import szu.model.Update;
import szu.model.Video;
import szu.service.UpdateService;
import szu.util.FileUtil;
import szu.util.JavaCvUtil;
import szu.util.TimeUtil;
import szu.util.VideoUtil;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Resource
    private MinioService minioService;
    @Resource
    private UpdateDao updatesDao;
    @Resource
    private PartitionDao partitionDao;
    @Resource
    private VideoDao videoDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UpdateHeatDao updateHeatDao;
    @Resource
    private RedisService redisService;
    @Resource
    private RedisWithMysqlImpl syncService;
    @Value("${minio.bucket-name}")
    private String bucketName;//minio桶名
    @Value("${const.minio.prefix.video-cache}")
    private String PREFIX_VIDEO_CACHE;
    @Value("${const.minio.prefix.video}")
    private String PREFIX_VIDEO;
    @Value("${const.minio.prefix.video-cover-cache}")
    private String PREFIX_VIDEO_COVER_CACHE;
    @Value("${const.minio.prefix.video-cover}")
    private String PREFIX_VIDEO_COVER;
    @Value("${const.redis.prefix.video-format-cache}")
    private String PREFIX_VIDEO_FORMAT_CACHE;


    /**
     * 常量
     **/
    private static final int UNCHECKED = 0;//未审核
    private static final int CHECKED = 1;//审核通过
    private static final int OFF_SHELF = 2;//下架
    private static final String ESSAY_PREFIX = "updates:photoUpdate:";//redis中essay的前缀
    private static final String VIDEO_PREFIX = "updates:videoUpdate:";//redis中video的前缀


    @Transactional
    @Override
    public void publishEssay(int uid, String content, MultipartFile[] files) {
        //没有图片,设置为空数组
        if (files == null) files = new MultipartFile[0];
        String[] urls = new String[files.length];
        int numOfUpload = 0;
        //使用事务，保证minio和数据库的一致性
        try {
            //上传minio，并记录url
            for (; numOfUpload < files.length; numOfUpload++) {
                //获取时间戳命名
                urls[numOfUpload] = System.currentTimeMillis() + files[numOfUpload].getOriginalFilename();
                //上传文件
                boolean isSuccessful = minioService.uploadFile(bucketName, urls[numOfUpload], files[numOfUpload].getInputStream());
                if (!isSuccessful) throw new Exception();
            }

            //调用dao层插入数据库
            Update new_ud = new Update(0, 0, uid, content, UNCHECKED,
                    null, JSON.toJSONString(urls));
            //插入update表
            if (updatesDao.insert(new_ud) != 1) throw new Exception();
            //TODO 通知管理员审核
            //新建一个update_heat记录
            updateHeatDao.insert(new_ud.getId(), 0, 0, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

    }


    @Transactional
    @Override
    public void deleteEssayById(int id) {
        try {
            //获取原动态信息
            Update updatesOriginal = findEssayById(id);
            if (updatesOriginal == null) return;//动态不存在，返回
            //删除原来的图片
            String[] urls = JSON.parseObject(updatesOriginal.getUrls(), String[].class);
            for (String url : urls) {
                minioService.deleteFile(bucketName, url);
            }
            //删除数据库中的记录
            updatesDao.deleteById(id);
            //删除update_heat中的记录
            updateHeatDao.deleteByUpdateId(id);
            //delete data in redis
            syncService.updateRedisByUpdateId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Delete essay failed", e);
        }
    }

    @Transactional
    @Override
    public boolean updateEssay(int id, String content, MultipartFile[] files) {
        try {
            //获取原动态信息
            Update updatesOriginal = findEssayById(id);
            if (updatesOriginal == null) return false;//动态不存在
            //更新动态信息
            updatesOriginal.setContent(content);
            //删除原来的图片
            String[] urls = JSON.parseObject(updatesOriginal.getUrls(), String[].class);
            for (String url : urls) {
                minioService.deleteFile(bucketName, url);
            }
            String[] urlsNew = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                //获取时间戳命名
                urlsNew[i] = System.currentTimeMillis() + files[i].getOriginalFilename();
                //上传文件
                minioService.uploadFile(bucketName, urlsNew[i], files[i].getInputStream());
            }
            updatesOriginal.setUrls(JSON.toJSONString(urlsNew));
            //更新数据库
            updatesDao.update(updatesOriginal.getId(), updatesOriginal.getVid(), updatesOriginal.getUid(),
                    updatesOriginal.getContent(), updatesOriginal.getStatus(),
                    String.valueOf(updatesOriginal.getUploadTime()),
                    updatesOriginal.getUrls());
            //update data in redis
            syncService.updateRedisByUpdateId(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Update essay failed", e);
        }
    }

    @Override
    public List<Update> findEssayInPage(int pageNum, int pageSize) {
        return updatesDao.findInPage(pageNum, pageSize);
    }

    @Override
    public List<Update> findEssayAll() {
        return updatesDao.findAll();
    }

    @Override
    public List<Update> findEssayByUid(int uid) {//获取指定用户的动态,返回List<Update>
        return updatesDao.findByUid(uid);
    }

    @Override
    public Update findEssayById(int id) {
        Map<Object, Object> objectMap = redisService.hGetAll(ESSAY_PREFIX + id);
        if (objectMap == null) {
            Update update = updatesDao.findById(id);
            if (update == null) return null;//数据库中没有该动态
            syncService.updateRedisByUpdateId(id);
            return update;
        }
        return JSON.parseObject(JSON.toJSONString(objectMap), Update.class);
    }

    @Override
    public byte[] getImage(String url) {
        //从minio中获取图片
        return minioService.downloadFile(bucketName, url);
    }

    @Transactional
    @Override
    public String publishVideo(Integer uid, String title, String content, Integer pid) {
        //先插入video表，获取vid；再插入update表
        try {
            String format = (String) redisService.get(PREFIX_VIDEO_FORMAT_CACHE + uid);
            if (format == null) return "视频文件缺失或视频过期";
            //获取视频的文件路径和封面路径
            String oldPath = PREFIX_VIDEO_CACHE + uid;
            String oldCoverPath = PREFIX_VIDEO_COVER_CACHE + uid + ".jpg";
            String videoFilePath = PREFIX_VIDEO + uid + "_" + System.currentTimeMillis() + "." + format;
            String videoHomePagePath = PREFIX_VIDEO_COVER + uid + "_" + System.currentTimeMillis() + ".jpg";
            if (!minioService.ifFileExist(bucketName, oldPath)
                    || !minioService.ifFileExist(bucketName, oldCoverPath))
                return "视频文件缺失或视频过期,请重新上传";
            //minio中的视频文件和封面文件存在，将原object移入新的object
            minioService.moveObject(bucketName, oldPath, videoFilePath);
            minioService.moveObject(bucketName, oldCoverPath, videoHomePagePath);
            Video new_vd = new Video(0, videoFilePath, 0, 0, 0, title, pid, 0, 0);
            //插入video表
            if (videoDao.insert(new_vd) != 1) throw new Exception();
            //插入update表
            Update new_ud = new Update(0, new_vd.getId(), uid, content, UNCHECKED,
                    null, JSON.toJSONString(new String[]{videoHomePagePath}));
            if (updatesDao.insert(new_ud) != 1) throw new Exception();
            //TODO 通知管理员审核
            //新建一个update_heat记录
            updateHeatDao.insert(new_ud.getId(), 0, 0, 0);

            return "发布成功";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Publish video failed", e);
        }
    }

    @Transactional
    @Override
    public String uploadVideo(MultipartFile video, int uid) {
        try {
            String videoPath = PREFIX_VIDEO_CACHE + uid;
            String pathOfHomePage = PREFIX_VIDEO_COVER_CACHE + uid + ".jpg";

            //生成封面,并存入minio
            MultipartFile snapshot = JavaCvUtil.snapshot(video.getInputStream());
            if (snapshot == null) {
                throw new RuntimeException("生成封面失败");
            }
            minioService.uploadFile(bucketName, pathOfHomePage, snapshot.getInputStream());
            // 将文件保存在本地
            String filePath = FileUtil.saveUploadedFile(video);
            int bitrate = VideoUtil.getBitRate(filePath);
            System.out.println("bitrate = " + bitrate);
            String outputFilePath = System.getProperty("user.dir")
                    + "\\admin\\src\\main\\resources\\temp\\output\\";

            String fileName = video.getOriginalFilename() + "_" + System.currentTimeMillis();
            VideoUtil.selectByOriginalBitrate(fileName, filePath, outputFilePath, bitrate);
            // TODO mpd和m4s的文件名，文件上传至minio
            //redis 存视频的编码格式
            String format = Objects.requireNonNull(video.getOriginalFilename())
                    .substring(video.getName().lastIndexOf(".") + 1);
            redisService.set(PREFIX_VIDEO_FORMAT_CACHE + uid, format, 43200);
            //存入minio,如果有之前上传的视频，会被覆盖
            minioService.uploadFile(bucketName, videoPath, video.getInputStream());
            //返回存储路径
            return pathOfHomePage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public String changeVideoCover(MultipartFile image, Integer uid) {
        try {
            String pathOfHomePage = PREFIX_VIDEO_COVER_CACHE + uid + ".jpg";
            minioService.uploadFile(bucketName, pathOfHomePage, image.getInputStream());
            //返回存储路径
            return pathOfHomePage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void deleteVideoByVid(int vid) {
        try {
            //删除原来的视频
            Video video = findVideoByVid(vid);
            if (video == null) return;
            minioService.deleteFile(bucketName, video.getUrl());
            //删除数据库中的记录
            videoDao.deleteById(vid);
            //删除update表中的记录
            Update byVid = updatesDao.findByVid(vid);
            String[] urls = JSON.parseObject(byVid.getUrls(), String[].class);
            for (String url : urls) {
                minioService.deleteFile(bucketName, url);
            }
            updatesDao.deleteById(byVid.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Delete video failed", e);
        }
    }

    @Override
    public Video findVideoByVid(int vid) {
        Map<Object, Object> objectMap = redisService.hGetAll(VIDEO_PREFIX + vid);
        if (objectMap == null) {
            if (!syncService.updateRedisByVideoId(vid)) return null;
            else {
                objectMap = redisService.hGetAll(VIDEO_PREFIX + vid);
            }
        }
        return JSON.parseObject(JSON.toJSONString(objectMap), Video.class);
    }

    /**
     * 分区管理
     */
    @Override
    public List<Partition> getPartitions() {
        return partitionDao.findAll();
    }

    @Override
    public void deletePartitionById(int id) {
        partitionDao.deleteById(id);
    }

    @Override
    public void addPartition(String name) {
        partitionDao.insert(name);
    }

    @Override
    public void updatePartition(int id, String name) {
        partitionDao.update(id, name);
    }


    /**
     * 面向前端
     */
    @Override
    public List<VideoVo> getHomePage(int pageSize) {
        //TODO 推荐算法
        //计算有多少个视频
        List<Integer> ids = videoDao.selectAllId();
        int videoNum = ids.size();
        if (videoNum == 0) return new ArrayList<>();
        //获取pageSize个随机int
        int[] randomNums = new int[pageSize];
        for (int i = 0; i < pageSize; i++) {
            randomNums[i] = ((int) (Math.random() * 10 * videoNum)) % videoNum;
        }
        //获取这些动态与视频,拼接vo
        List<VideoVo> videoVos = new ArrayList<>();
        for (int id : ids) {
            Video video = findVideoByVid(id);
            Update update = updatesDao.findByVid(id);
            VideoVo videoVo = new VideoVo();
            videoVo.setId(update.getId());
            videoVo.setUrl(video.getUrl());
            videoVo.setUploadTime(update.getUploadTime());
            videoVo.setUpName(userInfoDao.getNameById(update.getUid()));
            videoVo.setPlayNum(video.getPlayNum());
            videoVo.setDmNum(video.getDmNum());
            videoVo.setTotalTime(TimeUtil.secondsToHHMMSS(video.getTotalTime()));
            videoVo.setTitle(video.getTitle());
            videoVos.add(videoVo);
        }
        return videoVos;
    }

    @Override
    public Update findVideoUpdateByVid(int id) {
        try {
            return updatesDao.findByVid(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


}
