package szu.common.util;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common.util
 * @Author: Origami
 * @Date: 2023/11/2 20:50
 */

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256加密工具类
 */
public class ShaUtil {
    public static String encode(String str) {
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
}
