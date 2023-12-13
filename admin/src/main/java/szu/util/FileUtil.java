package szu.util;

import java.io.File;
import java.io.FileWriter;

public class FileUtil {
//    public static String saveUploadedFile(MultipartFile file) {
//        if (!file.isEmpty()) {
//            try {
//                String fileName = file.getOriginalFilename();
//                // 保存文件到指定路径
//                String filePath = System.getProperty("user.dir")
//                        + "\\admin\\src\\main\\resources\\temp\\output\\" + file.getOriginalFilename();
//                file.transferTo(new File(filePath));
//                return filePath;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public static void saveStringToFile(String content, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
