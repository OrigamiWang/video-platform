package szu.common.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.common.model
 * @Author: Origami
 * @Date: 2023/11/2 22:34
 */
@Component
public class GlobalPermissionMap {
    private static final Map<Integer, Integer> MAP = new HashMap<>();

    private GlobalPermissionMap() {
    }

    public static Map<Integer, Integer> getInstance() {
        return MAP;
    }
}
