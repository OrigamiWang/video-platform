package szu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 视频搜索对应的实体类
 */
@Data
@NoArgsConstructor
@ApiModel(description = "视频搜索文档实体类")
public class VideoSearchDoc {
    @ApiModelProperty("动态id")
    private int id;
    @ApiModelProperty("视频标题")
    private String title;
    @ApiModelProperty("封面url")
    private String url;
    @ApiModelProperty("播放量")
    private int playNum;
    @ApiModelProperty("弹幕数量")
    private int dmNum;
    @ApiModelProperty("视频时长，统一转换为秒数便于排序与筛选")
    private int totalTime;
    @ApiModelProperty("分区id")
    private int pid;
    @ApiModelProperty("收藏数量")
    private int starNum;
    //以上在video表中查询

    //以下在updates表中查询，其中name通过updates下的uid查user表
    @ApiModelProperty("作者名字")
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("上传日期")
    private LocalDateTime uploadTime;

    //补全字段由标题和作者名称组成
    @ApiModelProperty("补全字段")
    private List<String> suggestion;
}
