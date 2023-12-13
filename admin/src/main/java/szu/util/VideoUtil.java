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
     * 通过原画帧数来选择压缩的帧数
     */
    public static void selectByOriginalBitrate(String fileName, String inputFileName, String outputFilePath, int originalBitrate) {
        System.out.println("originalBitrate = " + originalBitrate);
        long timestamp = System.currentTimeMillis();
        String outputFileName;
        // 原画
        outputFileName = fileName + "_original.mp4";
        System.out.println("outputFileName = " + outputFileName);
        slice(fileName, inputFileName, "original");

        // 1080P
        Resolution resolution1080p = Resolution.RESOLUTION_1080P;
        int bitrate1080 = (int) (resolution1080p.getValue() * M);
        if (originalBitrate > bitrate1080) {
            outputFileName = outputFilePath + fileName + "_1080p.mp4";
            encodeVideo(inputFileName, outputFileName, bitrate1080);
            slice(fileName, inputFileName, P1080);
        }

        // 720P
        Resolution resolution720p = Resolution.RESOLUTION_720P;
        int bitrate720 = (int) (resolution720p.getValue() * M);
        if (originalBitrate > bitrate720) {
            outputFileName = outputFilePath + fileName +  "_720p.mp4";
            encodeVideo(inputFileName, outputFileName, bitrate720);
            slice(fileName, inputFileName, P720);
        }


    }

    public static int getBitRate(String filePath) {
        String[] cmd = {"ffprobe", "-i", filePath, "-show_entries", "format=bit_rate", "-v", "quiet", "-of", "csv=\"p=0\""};
        return Integer.parseInt(CommandUtil.exec(cmd));
    }

    public static void slice(String fileName, String filePath, String resolution) {
        String dirPath = filePath.substring(0, filePath.lastIndexOf('\\'));
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
                dirPath + "\\" + resolution + "_" + timestamp + ".mpd"
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
        // ffmpeg -i test.mp4 -vcodec copy -an v1.mp4
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
        // ffmpeg -i test.mp4 -acodec copy -vn a1.mp4
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
