package szu.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtil {
    public static String saveUploadedFile(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                // 保存文件到指定路径
                String filePath = System.getProperty("user.dir")
                        + "\\admin\\src\\main\\resources\\temp\\output\\" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
