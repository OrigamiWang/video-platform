package szu.util;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.Frame;
import szu.common.Resolution;


/**
 * @BelongsProject: StreamMedia
 * @BelongsPackage: com.wyx.streammedia.util
 * @Author: Origami
 * @Date: 2023/11/20 22:55
 */
public class VideoUtil {
    private static final int M = 1000000;
    private static final String P1080 = "1080P";
    private static final String P720 = "720P";


    /**
     * 处理视频
     */
    public static void handleVideo(String fileName, String inputFileName,
                                   String outputDir, int originalBitrate) {
        String outputFileName;
        // 原画
        outputFileName = fileName + "_original.mp4";
        System.out.println("outputFileName = " + outputFileName);
        slice(fileName, inputFileName, "original", outputDir);

        // 1080P
        Resolution resolution1080p = Resolution.RESOLUTION_1080P;
        int bitrate1080 = (int) (resolution1080p.getValue() * M);
        if (originalBitrate > bitrate1080) {
            outputFileName = outputDir + fileName + "_1080p.mp4";
            encodeVideo(inputFileName, outputFileName, bitrate1080);
            slice(fileName, inputFileName, P1080, outputDir);
        }

        // 720P
        Resolution resolution720p = Resolution.RESOLUTION_720P;
        int bitrate720 = (int) (resolution720p.getValue() * M);
        if (originalBitrate > bitrate720) {
            outputFileName = outputDir + fileName + "_720p.mp4";
            encodeVideo(inputFileName, outputFileName, bitrate720);
            slice(fileName, inputFileName, P720, outputDir);
        }


    }

    public static int getBitRate(String filePath) {
        String[] cmd = {"ffprobe", "-i", filePath, "-show_entries", "format=bit_rate", "-v", "quiet", "-of", "csv=\"p=0\""};
        return Integer.parseInt(CommandUtil.exec(cmd));
    }


    /**
     * 分段视频
     *
     * @param fileName   文件名(不带后缀)
     * @param filePath   文件路径
     * @param resolution 分辨率
     * @param dirPath    输出文件夹
     */
    public static void slice(String fileName, String filePath, String resolution, String dirPath) {
        long timestamp = System.currentTimeMillis();
        // 初始化分段文件的名称模式
        String initSegmentName = dirPath + "\\init-stream_" + fileName + "_" + resolution + "_$RepresentationID$.m4s";
        // 媒体分段文件的名称模式
        String mediaSegmentName = dirPath + "\\chunk-stream_" + fileName + "_" + resolution + "_$RepresentationID$-$Number%05d$.m4s";
        String[] cmd = {
                "ffmpeg",
                "-i",
                filePath,
                "-c:v",
                "libx264",
                "-c:a",
                "aac",
                "-f",
                "dash",
                "-init_seg_name",
                initSegmentName,
                "-media_seg_name",
                mediaSegmentName,
                dirPath + "\\" + resolution + "_" + fileName + timestamp + ".mpd"
        };
        CommandUtil.execWithStream(cmd);
    }


    // 设置不同的码率来压缩视频
    public static void encodeVideo(String inputFileName, String outputFileName, int bitrate) {
        try {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFileName);
            grabber.setFormat("mp4");
            FFmpegLogCallback.set();
            grabber.start();
            FFmpegFrameRecorder
                    recorder = new FFmpegFrameRecorder(outputFileName, grabber.getImageWidth(), grabber.getImageHeight());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            // FIX ME: set audio_channel=1 to avoid error, it may set other vals
            recorder.setAudioChannels(1);
            recorder.setVideoBitrate(bitrate);
            recorder.start();
            Frame frame;
            while ((frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
            }
            recorder.stop();
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void splitVideo(String inputFileName, String outputFileName) {
        // ffmpeg -i input.mp4 -vcodec copy -an v1.mp4
        String[] cmd = {
                "ffmpeg",
                "-i",
                inputFileName,
                "-vcodec",
                "copy",
                "-an",
                outputFileName
        };
        CommandUtil.exec(cmd);
    }


    public static void splitAudio(String inputFileName, String outputFileName) {
        // ffmpeg -i input.mp4 -acodec copy -vn a1.mp4
        String[] cmd = {
                "ffmpeg",
                "-i",
                inputFileName,
                "-acodec",
                "copy",
                "-vn",
                outputFileName
        };
        CommandUtil.exec(cmd);
    }

}
