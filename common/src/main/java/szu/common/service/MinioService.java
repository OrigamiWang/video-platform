package szu.common.service;

import org.apache.http.HttpException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

public interface MinioService {
    boolean uploadFile(String bucketName, String objectName, InputStream stream);

    byte[] downloadFile(String bucketName, String objectName);
    void downloadFile(String bucketName, String objectName, String destFileName);

    void deleteFile(String bucketName, String objectName);

    boolean ifFileExist(String bucketName, String objectName);

    void moveObject(String bucketName, String srcObjectName, String destObjectName);

    String createPreviewLink(String bucketName, String objectName);

    ResponseEntity<Resource> viewImage(String bucketName, String objectName) throws HttpException;
}
