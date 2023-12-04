package szu.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
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
            return convertToMultipartFile(out);

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

    private static MultipartFile convertToMultipartFile(ByteArrayOutputStream out) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return "imageFile"; // 给定一个字段名
            }

            @Override
            public String getOriginalFilename() {
                return "image.jpg"; // 给定一个文件名
            }

            @Override
            public String getContentType() {
                return "image/jpeg"; // 给定文件类型
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
