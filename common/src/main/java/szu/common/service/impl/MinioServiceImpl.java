package szu.common.service.impl;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import szu.common.service.MinioService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioServiceImpl implements MinioService {
    @Autowired
    private MinioClient minioClient;

    /***
     * 上传文件
     * 返回上传是否成功
     */
    @Override
    public boolean uploadFile(String bucketName, String objectName, InputStream stream) {
        try {
            checkBucket(bucketName);
            // 指定流式上传的InputStream和块大小
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(stream, -1, 5 * 1024 * 1024)
                    .build());
            stream.close();
            return true;
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }

    /***
     * 下载文件
     * 返回文件字节数组
     */
    @Override
    public byte[] downloadFile(String bucketName, String objectName) {
        try {
//            checkBucket(bucketName);
//            if (!doesObjectExist(bucketName, objectName)) {
//                return null;
//            }
            // 读取MinIO对象数据并返回
            InputStream is = new BufferedInputStream(minioClient
                    .getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build()));
            byte[] bytes = new byte[]{};
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                byte[] newBytes = new byte[bytes.length + len];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                System.arraycopy(buffer, 0, newBytes, bytes.length, len);
                bytes = newBytes;
            }
            return bytes;
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void downloadFile(String bucketName, String objectName, String destFileName) {
        try {
//            checkBucket(bucketName);
//            if (!doesObjectExist(bucketName, objectName)) {
//                return;
//            }
            minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(destFileName).build());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println(e.getMessage());
        }
    }

    /***
     * 删除文件,如果删除成功或文件不存在则返回true
     */
    @Override
    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println(e.getMessage());
        }
    }


    public boolean doesObjectExist(String bucketName, String objectName) throws ServerException,
            InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build()) != null;
    }

    /***
     * 检查存储桶是否存在，不存在则创建
     */
    public void checkBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException,
            IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
            InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @Override
    public boolean ifFileExist(String bucketName, String objectName) {
        try {
            return doesObjectExist(bucketName, objectName);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void moveObject(String bucketName, String srcObjectName, String destObjectName) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder().bucket(bucketName).object(destObjectName)
                    .source(CopySource.builder().bucket(bucketName).object(srcObjectName).build()).build());
//            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(srcObjectName).build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
