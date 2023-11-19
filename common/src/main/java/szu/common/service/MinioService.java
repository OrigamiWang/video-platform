package szu.common.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

public interface MinioService {
    boolean uploadFile(String bucketName, String objectName, InputStream stream);
    byte[] downloadFile(String bucketName, String objectName);

    InputStream getFileStream(String bucketName, String objectName) throws Exception;

    ResponseEntity<Resource> viewImage(String bucketName, String path);

    boolean deleteFile(String bucketName, String objectName);
}
