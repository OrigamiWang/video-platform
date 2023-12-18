package szu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * @author 郑榆达
 */
@Data
@AllArgsConstructor
@ApiModel(description = "历史记录视频列表的vo")
public class HistoryVo {
    @ApiModelProperty("视频对应的id")
    private Integer uid;
    @ApiModelProperty("观看到的时间")
    private String watchedAt;
    @ApiModelProperty("历史记录的时间")
    private String hisTime;
    @ApiModelProperty("视频的标题")
    private String title;
    @ApiModelProperty("up主的名字")
    private String upName;
    @ApiModelProperty("视频的封面")
    private String coverUrl;
}
