package szu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频搜索传递的参数
 */
@ApiModel("视频搜索传递的参数，若不传则默认为空")
@Data
public class VideoSearchParams {
    @ApiModelProperty("搜索框内输入的搜索关键词")
    private String key;
    @ApiModelProperty("搜索分类，有综合、视频、番剧、影视、直播、专栏、用户可选，传入分类id")
    private int classificationId;
    @ApiModelProperty("排序字段，按什么排序，有最多播放、最新发布、最多弹幕、最多收藏可选，传入排序id")
    private int sortBy;
    @ApiModelProperty("播放时长划分，10分钟以下、10-30分钟、30-60分钟、60分钟以上，传入时间id")
    private int time;
    @ApiModelProperty("分区id")
    private int pid;
}
