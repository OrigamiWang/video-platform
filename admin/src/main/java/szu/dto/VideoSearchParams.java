package szu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频搜索传递的参数
 */
@ApiModel("视频搜索传递的参数，其他id默认为0，表示不区分")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSearchParams {
    @ApiModelProperty("搜索框内输入的搜索关键词，key不能为空，key为空查询结果返回null")
    private String key;
    @ApiModelProperty("搜索分类，有综合、视频、番剧、影视、直播、专栏、用户可选，传入分类id,目前暂定0为默认，1为用户")
    private int classificationId;
    @ApiModelProperty("播放时长划分，10分钟以下、10-30分钟、30-60分钟、60分钟以上，传入时间id(1,2,3,4)，默认0表示全部时长")
    private int time;
    @ApiModelProperty("分区id,默认0表示全部分区")
    private int pid;
    @ApiModelProperty("排序字段，按什么排序，有最多播放、最新发布、最多弹幕、最多收藏可选，传入排序id(1,2,3,4), 默认0表示不作排序" +
            "如果classificationId为1，表示搜索用户，则1,2,3,4的含义变更为粉丝数高到低、粉丝数低到高、等级高到低，等级低到高，默认0")
    private int sortBy;
    @ApiModelProperty("页码，默认为1")
    private int page;
    @ApiModelProperty("size,不需要传，默认为每页最大30")
    private int size;

    public VideoSearchParams(String key){
        this.key = key;
    }
}
