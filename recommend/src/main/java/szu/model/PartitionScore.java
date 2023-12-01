package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName: PartitionScore
 * @Description: 分区得分
 * @Version 1.0
 * @Date: 2023-11-29 19:39
 * @Auther: UserXin
 */
@Data
@Document("partitionScore")
public class PartitionScore {
    @Id
    private String id;
    @ApiModelProperty("用户id")
    private Integer uid;
    @ApiModelProperty("分区id")
    private Integer pid;
    @ApiModelProperty("分区得分")
    private Integer score;

    public PartitionScore(){
        this.score = 0;
    }
}
