package szu.model;

import io.swagger.models.auth.In;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.model
 * @Author: Origami
 * @Date: 2023/11/25 10:34
 */
@Data
public class History {
    private Integer id;
    private Integer userId;
    private Integer mediaId;
    private Integer mediaType;
    private Time watchedAt;
    private Timestamp hisTime;
}
