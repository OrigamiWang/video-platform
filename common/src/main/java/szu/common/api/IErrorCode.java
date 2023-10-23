package szu.common.api;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common
 * @Author: Origami
 * @Date: 2023/10/2 10:32
 */
/**
 * 封装api的错误码
 */
public interface IErrorCode {

    long getCode();

    String getMessage();
}