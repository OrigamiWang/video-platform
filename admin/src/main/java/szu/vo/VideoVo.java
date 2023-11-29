package szu.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("返回视频列表的vo")
@Data
public class VideoVo {
    @ApiModelProperty("动态id")
    private int id;
    @ApiModelProperty("视频封面url")
    private String url;
    @ApiModelProperty("视频作者名字")//uid从user表查
    private String upName;
    @ApiModelProperty("视频上传时间")
    private LocalDateTime uploadTime;
    //以上是update表中可以查询

    @ApiModelProperty("视频播放量")
    private int playNum;
    @ApiModelProperty("弹幕数量")
    private int dmNum;
    @ApiModelProperty("总时长")
    private String totalTime;
    @ApiModelProperty("视频标题")
    private String title;
    //video表中
}
