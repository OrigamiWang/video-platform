package szu.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserStatistics implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "粉丝数量")
    private Integer fan;

    @ApiModelProperty(value = "关注数量")
    private Integer follow;

    @ApiModelProperty(value = "获赞数量")
    private Integer like;

    @ApiModelProperty("投稿视频数量")
    private Integer videoNum;

    @ApiModelProperty("视频总播放量")
    private Integer videoTotalPlay;

}