package szu.util;

import java.util.Random;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.util
 * @Author: Origami
 * @Date: 2023/11/4 13:36
 */

/**
 * 生成随机六位数字验证码
 */
public class PinUtil {
    public static String generatePin() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randInt = r.nextInt(10);
            sb.append(randInt);
        }
        return sb.toString();
    }
}
