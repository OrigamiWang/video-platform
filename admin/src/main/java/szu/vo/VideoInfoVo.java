package szu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import szu.model.Update;
import szu.model.User;
import szu.model.Video;

@ApiModel(description = "返回视频信息的vo")
@Data
public class VideoInfoVo {
    @ApiModelProperty("动态信息")
    private Update update;
    @ApiModelProperty("用户信息")
    private User user;
    @ApiModelProperty("视频信息")
    private Video video;
}
