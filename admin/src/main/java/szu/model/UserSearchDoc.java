package szu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("用户搜索文档实体类")
public class UserSearchDoc {
    @ApiModelProperty("用户uid")
    private int uid;
    @ApiModelProperty("用户昵称")
    private String name;
    @ApiModelProperty("用户等级")
    private int level;
    //以上在user表

    @ApiModelProperty("用户粉丝数量")
    private int fan;
    @ApiModelProperty("用户视频数量")
    private int video;
    @ApiModelProperty("用户简介")
    private String info;
}