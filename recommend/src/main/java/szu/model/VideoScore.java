package szu.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class VideoScore {
    @Id
    private String id;
    @ApiModelProperty("动态id")
    private Integer updateId;
    @ApiModelProperty("评分（观看：1 点赞：2 投币：3 收藏：4）")
    private Float score;

    public VideoScore(){
        this.score = 0f;
    }

}
