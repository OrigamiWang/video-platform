package szu.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import szu.common.service.MinioService;
import szu.dao.UpdatesDao;
import szu.model.Updates;
import szu.service.UpdatesService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class UpdatesServiceImpl implements UpdatesService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MinioService minioService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UpdatesDao updatesDao;
    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public boolean publish(int uid, String title, String content, String type, MultipartFile[] files) {
        try {
            if (files == null) files = new MultipartFile[0];
            String[] urls = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                //获取时间戳命名
                urls[i] = System.currentTimeMillis() + files[i].getOriginalFilename();
                //上传文件
                boolean isSuccessful = minioService.uploadFile(bucketName, urls[i], files[i].getInputStream());
                if (!isSuccessful) {
                    //上传失败,删除已上传的文件
                    for (int j = 0; j < i; j++) {
                        minioService.deleteFile(bucketName, urls[j]);
                    }
                    return false;
                }
            }
            //调用dao层插入数据库
            updatesDao.insert(uid, title, content, type, new Timestamp(System.currentTimeMillis()).toString(), JSON.toJSONString(urls));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void deleteById(int id) {
        updatesDao.deleteById(id);
    }

    @Override
    public List<Updates> findAll() {
        return updatesDao.findAll();
    }

    @Override
    public List<Updates> findByType(String type) {
        return updatesDao.findByType(type);
    }

    @Override
    public List<Updates> findByUid(int uid) {
        return null;
    }

    @Override
    public byte[] getImage(String url) {
        //从minio中获取图片
        return minioService.downloadFile(bucketName, url);
    }

    @Override
    public boolean update(Updates updates, MultipartFile[] files) {
        //获取原动态信息
        Updates updatesOriginal = updatesDao.findById(updates.getId());
        if (updatesOriginal == null) return false;
        if (updates.getTitle() != null) updatesOriginal.setTitle(updates.getTitle());
        if (updates.getContent() != null) updatesOriginal.setContent(updates.getContent());
        if (updates.getType() != null) updatesOriginal.setType(updates.getType());
        if (files != null) {//新的修改中包含图片的修改
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
        updatesDao.update(updatesOriginal.getId(),updatesOriginal.getUid(),updatesOriginal.getTitle(), updatesOriginal.getContent(),
                updatesOriginal.getType(), updatesOriginal.getDatetime().toString(), updatesOriginal.getUrls());
        return true;
    }
}
