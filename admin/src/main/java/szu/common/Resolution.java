package szu.common;

/**
 * @BelongsProject: StreamMedia
 * @BelongsPackage: com.wyx.streammedia.common
 * @Author: Origami
 * @Date: 2023/11/22 19:55
 */
public enum Resolution {
    /**
     * RESOLUTION_1080P: the bit rate of 1080p
     * RESOLUTION_720P: the bit rate of 720p
     */
    RESOLUTION_1080P(3.84),
    RESOLUTION_720P(1.92);

    private final double bitrate;

    Resolution(double bitrate) {
        this.bitrate = bitrate;
    }

    public double getValue() {
        return bitrate;
    }

}
