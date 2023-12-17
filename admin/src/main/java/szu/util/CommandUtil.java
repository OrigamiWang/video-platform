package szu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @BelongsProject: StreamMedia
 * @BelongsPackage: com.wyx.streammedia.util
 * @Author: Origami
 * @Date: 2023/11/20 22:32
 */
public class CommandUtil {

    /**
     * 在waitFor()之前，利用单独两个线程，分别处理process的getInputStream()和getErrorSteam()，防止缓冲区被撑满，导致阻塞
     */
    public static void execWithStream(String[] cmd) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            Process process = processBuilder.start();
            dealStream(process);
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String exec(String[] cmd) {
        StringBuilder res = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return res.toString();
    }

    private static void dealStream(Process process) {
        if (process == null) {
            return;
        }
        // 处理InputStream的线程
        new Thread(() -> {
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    System.out.println("output: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 处理ErrorStream的线程
        new Thread(() -> {
            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            try {
                while ((line = err.readLine()) != null) {
                    System.out.println("err: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    err.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
