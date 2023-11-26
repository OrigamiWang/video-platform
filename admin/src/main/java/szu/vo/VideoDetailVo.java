package szu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("视频详情页的vo")
public class VideoDetailVo {
//    视频标题、播放量、弹幕量、点赞、收藏、投币、转发、作者名字、作者关注数、发布时间
    @ApiModelProperty("视频标题")
    private String title;
    @ApiModelProperty("视频播放量")
    private int playNum;
    @ApiModelProperty("视频上传时间")
    private LocalDateTime uploadTime;
    @ApiModelProperty("弹幕数量")
    private int dmNUm;
    @ApiModelProperty("点赞数量")
    private int like;
    @ApiModelProperty("投币数量")
    private int coin;
    @ApiModelProperty("转发数量")
    private int share;
    @ApiModelProperty("视频作者名字")
    private String upName;
    @ApiModelProperty("视频作者关注数")
    private int follow;
}
