package szu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;

@Data
@ApiModel
public class Barrage {
    @ApiModelProperty("视频id")
    private int vid;
    @ApiModelProperty("用户id")
    private int uid;
    @ApiModelProperty("弹幕文本")
    private String text;
    @ApiModelProperty("弹幕颜色")
    private String color;
    @ApiModelProperty("弹幕出现的视频播放时间，秒数")
    private int time;
    @ApiModelProperty("弹幕发送的现实时间")
    private LocalDateTime sendTime;
    @ApiModelProperty("弹幕模式，0滚动，1静止")
    private int mode;
}
