package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserPreferce
 * @Description: TODO
 * @Version 1.0
 * @Date: 2023-12-01 13:09
 * @Auther: UserXin
 */
@Data
@Document(collection = "userPreferences")
@AllArgsConstructor
public class UserPreferences {
    @Id
    private String id;
    @ApiModelProperty("用户id")
    private Integer uid;
    @ApiModelProperty("用户对动态的评分")
    private List<VideoScore> videoScores;
    @ApiModelProperty("用户对分区的评分")
    private List<PartitionScore> partitionScores;
    public UserPreferences(){
        this.videoScores = new ArrayList<>();
        this.partitionScores = new ArrayList<>();
    }

}
