package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UpdateHeat
 * @Description: 动态热度实体类
 * @Version 1.0
 * @Date: 2023-11-29 9:37
 * @Auther: UserXin
 */
@Data
public class UpdateHeat {
    @ApiModelProperty("动态id")
    private Integer updateId;
    @ApiModelProperty("点赞数量")
    private Integer likeNum;
    @ApiModelProperty("评论数量")
    private Integer commentNum;
    @ApiModelProperty("收藏数量")
    private Integer shareNum;
}
