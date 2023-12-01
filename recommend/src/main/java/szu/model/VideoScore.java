package szu.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName: VideoScore
 * @Description: 视频评分
 * @Version 1.0
 * @Date: 2023-11-29 19:37
 * @Auther: UserXin
 */
@Data
@Document(collection = "videoScore")
public class VideoScore {
    @Id
    private String id;
    @ApiModelProperty("用户id")
    private Integer uid;
    @ApiModelProperty("动态id")
    private Integer updateId;
    @ApiModelProperty("评分（观看：1 点赞：2 投币：3 收藏：4）")
    private Float score;
}
