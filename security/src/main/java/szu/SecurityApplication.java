package szu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.security
 * @Author: Origami
 * @Date: 2023/10/28 15:05
 */
@SpringBootApplication
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }


}
