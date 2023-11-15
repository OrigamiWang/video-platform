package szu.common;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import szu.common.config.MinioConfig;
import szu.common.service.MinioService;
import szu.common.service.impl.MinioServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

class CommonApplicationTests {
    @Test
    void contextLoads() {
    }
}
