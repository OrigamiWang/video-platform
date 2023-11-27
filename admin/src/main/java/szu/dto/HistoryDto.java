package szu.dto;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.dto
 * @Author: Origami
 * @Date: 2023/11/25 11:01
 */
@Data
public class HistoryDto {
    private Integer userId;
    private Integer mediaId;
    private Integer mediaType;
    private Time watchedAt;
}
