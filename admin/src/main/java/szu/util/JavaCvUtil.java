package szu.util;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class JavaCvUtil {
    /***
     * 获取视频的第一帧图片
     * @param videoStream 视频流
     */
    public static MultipartFile snapshot(InputStream videoStream) {
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoStream);
        try {
            ff.start();
            int length = ff.getLengthInFrames();
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前100帧，避免出现全黑的图片
                f = ff.grabFrame();
                if ((i > 100) && (f.image != null)) {
                    break;
                }
                i++;
            }
            // 截取的帧图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage srcBi = converter.getBufferedImage(f);
            int owidth = srcBi.getWidth();
            int oheight = srcBi.getHeight();
            // 对截取的帧图片进行等比例缩放
            int width = 800;
            int height = (int) (((double) width / owidth) * oheight);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            // 输出小截图,用byte[]存储
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", out);
            // 转换为MultipartFile
            return convertToMultipartFile(out, "snapshot.jpg", "image/jpeg", "snapshot");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ff.stop();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public static MultipartFile convertAviToM3u8(InputStream videoStream) {
        //TODO
        return null;
//        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoStream);
//        try {
//            grabber.start();
//
//            File file = File.createTempFile("temp", ".m3u8");
//            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(file, grabber.getImageWidth(), grabber.getImageHeight());
//            recorder.setAudioChannels(grabber.getAudioChannels());
//            recorder.setFormat("hls");
//            recorder.setOption("hls_time", "5");
//            recorder.setOption("hls_list_size", "0");
//            recorder.setOption("hls_flags", "delete_segments");
//            recorder.setOption("hls_delete_threshold", "1");
//            recorder.setOption("hls_segment_type", "mpegts");
//            recorder.start();
//
//            Frame frame;
//            while ((frame = grabber.grab()) != null) {
//                recorder.record(frame);
//            }
//
//            recorder.stop();
//            grabber.stop();
//            //输出file的大小
//            System.out.println(file.length());
//
//            // Create a new MultipartFile with the converted content
//            return convertToMultipartFile(file, "m3u8",
//                    "application/x-mpegURL", "file");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                grabber.stop();
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return null;
    }

    private static MultipartFile convertToMultipartFile(File file, String fileName, String contentType, String file1) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return file1; // 给定一个字段名
            }

            @Override
            public String getOriginalFilename() {
                return fileName; // 给定一个文件名
            }

            @Override
            public String getContentType() {
                return contentType; // 给定文件类型
            }

            @Override
            public boolean isEmpty() {
                return file.length() == 0;
            }

            @Override
            public long getSize() {
                return file.length();
            }

            @NotNull
            @Override
            public byte[] getBytes() throws IOException {
                return Files.readAllBytes(file.toPath());
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(file);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            @Override
            public void transferTo(java.nio.file.Path dest) throws IOException, IllegalStateException {
                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            }
        };
    }

    private static MultipartFile convertToMultipartFile(ByteArrayOutputStream out,
                                                        String fileName, String contentType, String fieldName) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return fieldName; // 给定一个字段名
            }

            @Override
            public String getOriginalFilename() {
                return fileName; // 给定一个文件名
            }

            @Override
            public String getContentType() {
                return contentType; // 给定文件类型
            }

            @Override
            public boolean isEmpty() {
                return out.size() == 0;
            }

            @Override
            public long getSize() {
                return out.size();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return out.toByteArray();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(out.toByteArray());
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.copy(new ByteArrayInputStream(out.toByteArray()), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            @Override
            public void transferTo(java.nio.file.Path dest) throws IOException, IllegalStateException {
                Files.copy(new ByteArrayInputStream(out.toByteArray()), dest, StandardCopyOption.REPLACE_EXISTING);
            }
        };

    }
}
