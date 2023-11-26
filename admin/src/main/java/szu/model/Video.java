package szu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("video实体类（数据库字段）")
public class Video {
    @ApiModelProperty("视频id")
    private int id;
    @ApiModelProperty("视频url")
    private String url;
    @ApiModelProperty("播放量")
    private int playNum;
    @ApiModelProperty("弹幕数量")
    private int dmNum;
    @ApiModelProperty("总时长")
    private String totalTime;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("作者uid")
    private int uid;
    @ApiModelProperty("上传时间")
    private LocalDateTime uploadTime;
    @ApiModelProperty("分区id")
    private int pid;
    @ApiModelProperty("收藏数量")
    private int star;
}
