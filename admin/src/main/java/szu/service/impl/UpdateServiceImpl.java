package szu.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import szu.common.service.MinioService;
import szu.dao.PartitionDao;
import szu.dao.UpdateDao;
import szu.model.Partition;
import szu.model.Update;
import szu.service.UpdateService;

import java.sql.Timestamp;
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
    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public boolean publish(int uid, String title, String content, int type, int pid, MultipartFile[] files) {
        //没有图片,设置为空数组
        if (files == null) files = new MultipartFile[0];
        //校验参数，如果type或pid不存在，返回false
        if (partitionDao.findById(pid) == null) return false;

        String[] urls = new String[files.length];
        int numOfUpload = 0;
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
            updatesDao.insert(uid, title, content, 0,
                    type, new Timestamp(System.currentTimeMillis()).toString(),
                    JSON.toJSONString(urls), pid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败,删除已上传的文件
            for (int j = 0; j < numOfUpload; j++)
                minioService.deleteFile(bucketName, urls[j]);
            return false;
        }
    }


    @Override
    public void deleteById(int id) {
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

    @Override
    public boolean update(HashMap<String, Object> params, MultipartFile[] files) {
        //获取原动态信息
        Update updatesOriginal = updatesDao.findById((Integer) params.get("id"));
        if (updatesOriginal == null) return false;

        //更新动态信息
        if (params.get("title") != null) updatesOriginal.setTitle((String) params.get("title"));
        if (params.get("content") != null) updatesOriginal.setContent((String) params.get("content"));
        if (params.get("type") != null) {
            updatesOriginal.setType((Integer) params.get("type"));
        }
        if (params.get("pid") != null) {
            if (partitionDao.findById((Integer) params.get("pid")) == null) return false;
            updatesOriginal.setPid((Integer) params.get("pid"));
        }
        if (params.get("status") != null) {
            updatesOriginal.setStatus((Integer) params.get("status"));
        }
        if (files != null) {//修改中包含图片的修改
            //删除原来的图片
            String[] urls = JSON.parseObject(updatesOriginal.getUrls(), String[].class);
            for (String url : urls) {
                minioService.deleteFile(bucketName, url);
            }
            String[] urlsNew = new String[files.length];
            //上传新的图片
            try {
                for (int i = 0; i < files.length; i++) {
                    //获取时间戳命名
                    urlsNew[i] = System.currentTimeMillis() + files[i].getOriginalFilename();
                    //上传文件
                    minioService.uploadFile(bucketName, urlsNew[i], files[i].getInputStream());
                }
            } catch (Exception e) {
                for (String url : urlsNew) {//上传失败,删除已上传的文件
                    minioService.deleteFile(bucketName, url);
                }
                return false;
            }
            updatesOriginal.setUrls(JSON.toJSONString(urlsNew));
        }
        //更新数据库
        updatesDao.update(updatesOriginal.getId(), updatesOriginal.getUid(),
                updatesOriginal.getTitle(), updatesOriginal.getContent(),
                updatesOriginal.getStatus(), updatesOriginal.getType(),
                new Timestamp(System.currentTimeMillis()).toString(),
                updatesOriginal.getUrls(), updatesOriginal.getPid());
        return true;
    }

    @Override
    public List<Update> findAll() {
        return updatesDao.findAll();
    }

    @Override
    public List<Update> findByPartition(int pid) {
        return updatesDao.findByPid(pid);
    }

    @Override
    public List<Update> findByType(int type) {
        return updatesDao.findByType(type);
    }

    @Override
    public List<Update> findByUid(int uid) {
        return updatesDao.findByUid(uid);
    }

    @Override
    public byte[] getImage(String url) {
        //从minio中获取图片
        return minioService.downloadFile(bucketName, url);
    }

    @Override
    public Update findById(int id) {
        return updatesDao.findById(id);
    }

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
}
