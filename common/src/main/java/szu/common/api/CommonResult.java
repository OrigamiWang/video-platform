package szu.common.api;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common
 * @Author: Origami
 * @Date: 2023/10/2 10:31
 */

import lombok.Getter;
import lombok.Setter;


/**
 * 通用返回对象
 */
@Getter
@Setter
public class CommonResult<T> {

    private long code;

    private String message;

    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }


    /**
     * 失败返回结果
     *
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }


    /**
     * 失败返回结果
     *
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data, String message) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), message, data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }


    /**
     * 自定义异常类的返回结果
     */
    public static <T> CommonResult<T> customizedException(long code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

    /**
     * 文件传输失败
     */
    public static <T> CommonResult<T> fileTransferException(T data) {
        return new CommonResult<>(ResultCode.FILE_TRANSFER_EXCEPTION.getCode(), ResultCode.FILE_TRANSFER_EXCEPTION.getMessage(), data);
    }



}
