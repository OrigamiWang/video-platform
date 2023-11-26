package szu.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 视频搜索对应的实体类
 */
@Data
@NoArgsConstructor
@ApiModel("视频搜索文档实体类")
public class VideoSearchDoc {
    @ApiModelProperty("视频id")
    private int id;
    @ApiModelProperty("视频标题")
    private String title;
    @ApiModelProperty("作者名字")
    private String name;
    @ApiModelProperty("封面url")
    private String url;
    @ApiModelProperty("播放量")
    private int playNum;
    @ApiModelProperty("弹幕数量")
    private int dmNum;
    @ApiModelProperty("视频时长")
    private String playtime;
    @ApiModelProperty("上传日期，以yyyy-mm-dd的形式")
    private String uploadTime;
    @ApiModelProperty("分区id")
    private int pid;
    @ApiModelProperty("收藏数量")
    private int star;
    @ApiModelProperty("补全字段")
    private List<String> suggestion;
}
