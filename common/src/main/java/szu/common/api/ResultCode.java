package szu.common.api;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common
 * @Author: Origami
 * @Date: 2023/10/2 10:33
 */
public enum ResultCode implements IErrorCode {


    /**
     *  SUCCESS: 操作成功
     *  FAILED: 操作失败
     *  VALIDATE_FAILED: 参数校验失败
     *  UNAUTHORIZED: 暂未登录或token已过期
     *  FORBIDDEN: 没有相关权限
     *
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数校验失败"),
    UNAUTHORIZED(401, "暂未登录或token已过期"),
    FORBIDDEN(401, "没有相关权限"),

    FILE_TRANSFER_EXCEPTION(501, "文件传输失败"),

    CUSTOMIZED_EXCEPTION(555, "自定义异常类错误");



    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    private final long code;

    private final String message;


    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
