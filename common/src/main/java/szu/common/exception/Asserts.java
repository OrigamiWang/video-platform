package szu.common.exception;

/**
 * 断言处理类，用于抛出各种API异常
 * @author HJH201314
 */
public abstract class Asserts {

    public static void isNull(Object o, String message) {
        if (o != null) {
            throw new ApiException(message);
        }
    }

    public static void notNull(Object o, String message) {
        if (o == null) {
            throw new ApiException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ApiException(message);
        }
    }
}
