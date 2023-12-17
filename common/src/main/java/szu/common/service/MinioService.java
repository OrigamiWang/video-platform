package szu.common.service;

import java.io.InputStream;

public interface MinioService {
    boolean uploadFile(String bucketName, String objectName, InputStream stream);

    byte[] downloadFile(String bucketName, String objectName);
    void downloadFile(String bucketName, String objectName, String destFileName);

    void deleteFile(String bucketName, String objectName);

    boolean ifFileExist(String bucketName, String objectName);

    void moveObject(String bucketName, String srcObjectName, String destObjectName);
}
