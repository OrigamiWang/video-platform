package szu.util;

import java.io.File;
import java.io.FileWriter;
import java.util.Stack;

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

    public static void clearDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                clearDir(f.getAbsolutePath());
            }
            file.delete();
        }
    }

    public static boolean checkFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean touchDir(String inputRootDir) {
        try {
            //按//分割路径
            String[] dirs = inputRootDir.split("\\\\");
            Stack<String> stack = new Stack<>();
            String existDir = inputRootDir;
            while (!new File(existDir).exists()) {
                stack.push(existDir);
                existDir = existDir.substring(0, existDir.lastIndexOf("\\"));
            }
            while (!stack.isEmpty()) {
                existDir = stack.pop();
                new File(existDir).mkdir();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
