package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserDetail implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @ApiModelProperty(value = "性别")
    private byte[] gender;

    @ApiModelProperty(value = "经验值")
    private Integer exp;

    @ApiModelProperty(value = "ip属地")
    private String ip;

    @ApiModelProperty(value = "自我介绍")
    private String info;

}