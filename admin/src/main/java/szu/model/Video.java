package szu.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@ApiModel("video实体类（数据库字段）")
@NoArgsConstructor
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
    private int totalTime;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("分区id")
    private int pid;
    @ApiModelProperty("收藏数量")
    private int starNum;
    @ApiModelProperty("投币数量")
    private int coinNum;
}
