package szu.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import szu.common.service.MinioService;
import szu.dao.*;
import szu.model.Partition;
import szu.model.Update;
import szu.model.Video;
import szu.service.UpdateService;
import szu.vo.VideoVo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private MinioService minioService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UpdateDao updatesDao;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PartitionDao partitionDao;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private VideoDao videoDao;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserInfoDao userInfoDao;
    @Value("${minio.bucket-name}")
    private String bucketName;
    @Value("${shen_he.unchecked}")
    private int UNCHECKED;
    @Value("${shen_he.checked}")
    private int CHECKED;
    @Value("${shen_he.off_shelf}")
    private int OFF_SHELF;
    //shen_he:
    //    unchecked: 0
    //    checked: 1
    //    off_shelf: 2

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
            updatesDao.insert(0, uid, content, UNCHECKED, new Timestamp(System.currentTimeMillis()).toString(),
                    JSON.toJSONString(urls));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Publish essay failed", e);
        }

    }

    @Override
    public void deleteEssayById(int id) {
        //获取原动态信息
        Update updatesOriginal = updatesDao.findById(id);
        if (updatesOriginal == null) return;

        //删除原来的图片
        String[] urls = JSON.parseObject(updatesOriginal.getUrls(), String[].class);
        for (String url : urls) {
            minioService.deleteFile(bucketName, url);
        }
        //删除数据库中的记录
        updatesDao.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateEssay(int id, String content, MultipartFile[] files) {
        //获取原动态信息
        try {
            Update updatesOriginal = updatesDao.findById(id);
            if (updatesOriginal == null) return false;
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
    public List<Update> findEssayByUid(int uid) {
        return updatesDao.findByUid(uid);
    }

    @Override
    public Update findEssayById(int id) {
        return updatesDao.findById(id);
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
            int vid = videoDao.insert(new Video(0, videoUrl, 0, 0, "0", title, pid, 0, 0));
            //插入update表
            updatesDao.insert(vid, id, content, UNCHECKED, new Timestamp(System.currentTimeMillis()).toString(),
                    JSON.toJSONString(new HashMap<>()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Publish video failed", e);
        }
    }

    @Transactional
    @Override
    public void deleteVideoById(int id) {
        try {
            //删除原来的视频
            Video video = videoDao.findById(id);
            if (video == null) return;
            minioService.deleteFile(bucketName, video.getUrl());
            //删除数据库中的记录
            videoDao.deleteById(id);
            //删除update表中的记录
            Update byVid = updatesDao.findByVid(id);
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
        int videoNum =ids.size();
        //获取pageSize个随机int
        int[] randomNums = new int[pageSize];
        for (int i = 0; i < pageSize; i++) {
            randomNums[i] = ((int) (Math.random() * 10*videoNum))%videoNum;
        }
        //获取这些动态与视频,拼接vo
        List<VideoVo> videoVos = new ArrayList<>();
        for (int id: ids) {
            Video video = videoDao.findById(id);
            Update update = updatesDao.findByVid(id);
            VideoVo videoVo = new VideoVo();
            videoVo.setId(id);
            videoVo.setUrl(video.getUrl());
            videoVo.setUploadTime(update.getUploadTime());
            videoVo.setUpName(userInfoDao.getNameById(update.getUid()));
            videoVo.setPlayNum(video.getPlayNum());
            videoVo.setDmNUm(video.getDmNum());
            videoVo.setTotalTime(video.getTotalTime());
            videoVo.setTitle(video.getTitle());
            videoVos.add(videoVo);
        }
        return videoVos;
    }


}
