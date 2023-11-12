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

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "用户状态")
    private Integer status;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

}