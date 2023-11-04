package szu.common.service;

import java.io.InputStream;

public interface MinioService {
    boolean uploadFile(String bucketName, String objectName, InputStream stream);
    byte[] downloadFile(String bucketName, String objectName);
    boolean deleteFile(String bucketName, String objectName);
}
