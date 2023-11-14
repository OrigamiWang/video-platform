package szu.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import szu.common.config.MinioConfig;
import szu.common.service.impl.MinioServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@SpringBootTest(classes = MinioConfig.class)
class MinioTest {
    @Autowired
    private MinioServiceImpl minioService;
    @Test
    void testMinioUpload() {
        String bucketName = "test";
        String objectName = "bm.jpg";
        String endpoint = "http://localhost:9000";
        String filePath = "D:\\软工后端\\video-platform\\common\\src\\main\\resources\\bm.jpg";
        try {
            minioService.uploadFile(bucketName, objectName, new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMinioDownload() throws FileNotFoundException {
        String bucketName = "test";
        String objectName = "bm.jpg";
        String savePath = "D:\\软工后端\\video-platform\\common\\src\\main\\resources\\bm.jpg";
        byte[] bytes = minioService.downloadFile(bucketName, objectName);
        System.out.println(bytes.length);
        FileOutputStream fos = new FileOutputStream(savePath);
        byte[] buffer = new byte[1024];
        int len;
        try {
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMinioDelete() {
        String bucketName = "test";
        String objectName = "bm.jpg";
        minioService.deleteFile(bucketName, objectName);
    }
}
