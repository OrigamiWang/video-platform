package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class User implements Serializable {
    @ApiModelProperty(value = "uid")
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "经验值")
    private Integer exp;

    @ApiModelProperty(value = "用户目前的状态")
    private Integer status;

    @ApiModelProperty(value = "用户身份")
    private Integer role;

    @ApiModelProperty(value = "粉丝数量")
    private Integer fan;

    @ApiModelProperty(value = "关注数量")
    private Integer follow;

    @ApiModelProperty(value = "获赞数量")
    private Integer like;

    @ApiModelProperty(value = "ip属地")
    private String ip;

    @ApiModelProperty(value = "电话/账号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "自我介绍")
    private String info;

    private static final long serialVersionUID = 1L;

}