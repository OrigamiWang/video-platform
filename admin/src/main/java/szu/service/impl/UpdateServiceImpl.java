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
import szu.util.TimeUtil;
import szu.vo.VideoVo;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**常量**/
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
            int id = updatesDao.insert(0, uid, content, UNCHECKED, new Timestamp(System.currentTimeMillis()).toString(),
                    JSON.toJSONString(urls));

            //TODO 通知管理员审核
            //新建一个update_heat记录
            updateHeatDao.insert(id, 0, 0, 0);
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
    public void publishVideo(Integer id, String title, String content, Integer pid, MultipartFile video) {
        //先插入video表，获取vid；再插入update表

        try {
            //TODO 剪视频第一帧作为封面，上传minio
            // ffmpeg
            //上传视频
            String videoUrl = System.currentTimeMillis() + video.getOriginalFilename();
            minioService.uploadFile(bucketName, videoUrl, video.getInputStream());
            //插入video表
            int vid = videoDao.insert(new Video(0, videoUrl, 0, 0, 0, title, pid, 0, 0));
            //插入update表
            int new_id = updatesDao.insert(vid, id, content, UNCHECKED, new Timestamp(System.currentTimeMillis()).toString(),
                    JSON.toJSONString(new HashMap<>()));
            //TODO 通知管理员审核
            //新建一个update_heat记录
            updateHeatDao.insert(new_id, 0, 0, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Publish video failed", e);
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
            Video video = videoDao.findById(vid);
            if (video == null) return null;//数据库中没有该视频
            syncService.updateRedisByVideoId(vid);
            return video;
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
            videoVo.setId(id);
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
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
