package szu.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import szu.util.*;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Resource
    private EsUtil esUtil;
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
    @Value("${const.redis.prefix.video-ori-name}")
    private String PREFIX_VIDEO_ORI_NAME;
    @Value(("${const.redis.prefix.cover-format}"))
    private String COVER_FORMAT;
    private static final String VIDEO_CACHE_DIR = System.getProperty("user.dir")
            + "\\src\\main\\resources\\video_cache\\";
    private static final String INPUT_ROOT_DIR = System.getProperty("user.dir")
            + "\\src\\main\\resources\\video_cache\\input\\";
    private static final String OUTPUT_ROOT_DIR = System.getProperty("user.dir")
            + "\\src\\main\\resources\\video_cache\\output\\";
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
        if (objectMap == null || objectMap.isEmpty()) {
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
            //获取原视频名字
            String ori_name = (String) redisService.get(PREFIX_VIDEO_ORI_NAME + uid);
            /** 将封面移入正式存储区 **/
            //获取视频的文件路径和封面路径
            String coverFormat = (String) redisService.get(COVER_FORMAT+uid);
            String oldCoverPath = PREFIX_VIDEO_COVER_CACHE + uid;
            String videoHomePagePath = PREFIX_VIDEO_COVER + uid + "_" + System.currentTimeMillis() + "." + coverFormat;
            //将原object移入新的object
            minioService.moveObject(bucketName, oldCoverPath, videoHomePagePath);

            /** 将视频切片后移入正式存储区 **/
            //确保输出文件夹为空
            FileUtil.clearDir(OUTPUT_ROOT_DIR + uid);
            // 将文件保存在本地,位置为：src/main/resources/video_cache/input/uid/视频名
            String inputDir = INPUT_ROOT_DIR + uid;
            String outputDir = OUTPUT_ROOT_DIR + uid;
            if (!FileUtil.touchDir(inputDir)) throw new RuntimeException("创建文件夹失败");
            if (!FileUtil.touchDir(outputDir)) throw new RuntimeException("创建文件夹失败");
            String videoFile = inputDir + "\\" + ori_name;
            minioService.downloadFile(bucketName,
                    PREFIX_VIDEO_CACHE + uid, videoFile);
            //检测保存的视频是否存在
            if(!FileUtil.checkFileExist(videoFile)) throw new RuntimeException("minio中的缓存视频不存在");
            //切片并保存在本地，位置为：src/main/resources/video_cache/output/uid/时间戳+视频名/子文件名
            int bitrate = VideoUtil.getBitRate(videoFile);
            String ori_name_without_suffix = ori_name.substring(0, ori_name.lastIndexOf("."));
            VideoUtil.handleVideo(ori_name_without_suffix,
                    videoFile, outputDir, bitrate);

            /**将文件夹的文件上传至minio**/
            File[] files = new File(outputDir).listFiles();
            if (files == null) {
                throw new RuntimeException("文件夹为空，切片操作异常。");
            }
            ArrayList<String> mpds = new ArrayList<>();//保存mpd文件的url
            ArrayList<String> urls = new ArrayList<>();//保存所有文件的url
            String video_stamp = System.currentTimeMillis() + "";
            for (File file : files) {//遍历文件夹，上传文件，保存格式为：uid/时间戳+文件名/子文件名
                String obj = PREFIX_VIDEO + uid + "/" + video_stamp
                        + "/" + file.getName();
                minioService.uploadFile(bucketName, obj
                        , new FileInputStream(file));
                if (file.getName().endsWith(".mpd")) mpds.add(obj);//保存url,只需要mpd文件的url
                urls.add(obj);//记录所有文件的url
            }
            //将urls写入本地同一文件夹下命名为urls.json
            String urlsJson = JSON.toJSONString(urls);
            String urlsJsonPath = OUTPUT_ROOT_DIR + uid + "\\urls.json";
            //写入本地文件，然后上传至minio
            FileUtil.saveStringToFile(urlsJson, urlsJsonPath);
            minioService.uploadFile(bucketName, PREFIX_VIDEO + uid + "/" + video_stamp + "/urls.json",
                    new FileInputStream(urlsJsonPath));

            //删除minio中的缓存文件
            minioService.deleteFile(bucketName, PREFIX_VIDEO_CACHE + uid);
            minioService.deleteFile(bucketName, PREFIX_VIDEO_COVER_CACHE + uid + ".jpg");
            redisService.del(PREFIX_VIDEO_ORI_NAME + uid);
            //删除本地文件
            FileUtil.clearDir(INPUT_ROOT_DIR + uid);
            FileUtil.clearDir(OUTPUT_ROOT_DIR+uid);
            /**操作数据库**/
            //clear redis
            Video new_vd = new Video(0, JSON.toJSONString(mpds),
                    0, 0, 0, title, pid, 0, 0);
            //插入video表
            if (videoDao.insert(new_vd) != 1) throw new Exception();
            //插入update表
            Update new_ud = new Update(0, new_vd.getId(), uid, content, UNCHECKED,
                    null, JSON.toJSONString(new String[]{videoHomePagePath}));
            if (updatesDao.insert(new_ud) != 1) throw new Exception();
            //新建一个update_heat记录
            updateHeatDao.insert(new_ud.getId(), 0, 0, 0);
            //将新视频通过异步线程更新到es中
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                esUtil.insertNewVideoIntoEs(new_vd.getId());
            });
            executor.shutdown();
            //TODO 通知管理员审核
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
            //仅接收mp4格式的视频
            if (video.getOriginalFilename() == null || !video.getOriginalFilename().endsWith(".mp4")) {
                throw new RuntimeException("仅接收mp4格式的视频");
            }
            String videoPath = PREFIX_VIDEO_CACHE + uid;
            String pathOfCover = PREFIX_VIDEO_COVER_CACHE + uid;

            //生成封面,并存入minio
            MultipartFile snapshot = JavaCvUtil.snapshot(video.getInputStream());
            if (snapshot == null) {
                throw new RuntimeException("生成封面失败");
            }
            minioService.uploadFile(bucketName, pathOfCover, snapshot.getInputStream());
            //将原视频上传minio
            //redis保存原视频名字
            redisService.set(PREFIX_VIDEO_ORI_NAME + uid, video.getOriginalFilename(), 43200);
            redisService.set(COVER_FORMAT+uid,"jpg",43200);
            //存入minio,如果有之前上传的视频，会被覆盖
            minioService.uploadFile(bucketName, videoPath, video.getInputStream());
            //返回封面存储路径
            return pathOfCover;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> previewVideoCover(int uid) {
        try {
            String path = PREFIX_VIDEO_COVER_CACHE + uid + ".jpg";
            //返回存储路径
            return minioService.viewImage(bucketName, path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void touchDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Transactional
    @Override
    public String changeVideoCover(MultipartFile image, Integer uid) {
        try {
            String pathOfHomePage = PREFIX_VIDEO_COVER_CACHE + uid;
            String format = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".")+1);
            minioService.uploadFile(bucketName, pathOfHomePage, image.getInputStream());
            redisService.set(COVER_FORMAT+uid,format,43200);
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
            ArrayList<String> urls = JSON.parseObject(video.getUrl(), ArrayList.class);
            //解析其中的mpd文件，命名格式为 文件夹名/文件名
            //要找到其他文件，只需要找到文件夹下的urls.json文件，解析其中的url即可
            String mpd = urls.get(0);
            String prefix = mpd.substring(0, mpd.lastIndexOf("/"));
            String urlsJson = prefix + "/urls.json";
            String urlsJsonStr = new String(minioService.downloadFile(bucketName, urlsJson));
            ArrayList<String> urlsInJson = JSON.parseObject(urlsJsonStr, ArrayList.class);
            for (String url : urlsInJson) {
                minioService.deleteFile(bucketName, url);
            }
            minioService.deleteFile(bucketName, urlsJson);

            //删除数据库中的记录
            videoDao.deleteById(vid);
            //删除update表中的记录
            Update byVid = updatesDao.findByVid(vid);
            String[] covers = JSON.parseObject(byVid.getUrls(), String[].class);
            for (String cover : covers) {//删除封面
                minioService.deleteFile(bucketName, cover);
            }
            updatesDao.deleteById(byVid.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Delete video failed", e);
        }
    }

    @Override
    public Video findVideoByVid(int vid) {
        return videoDao.findById(vid);
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
            if (videoVos.size() == pageSize) break;
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
