package szu.util;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common
 * @Author: Origami
 * @Date: 2023/10/30 20:42
 */
public class UuidUtil {
    public static String getUuid() {
        // 传入网卡信息，基于时间制作出生成器
        UUID uuid = Generators.timeBasedGenerator().generate();
        return uuid.toString().replaceAll("-", "");
    }

}
