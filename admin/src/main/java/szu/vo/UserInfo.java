package szu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息vo
 */
@Data
@ApiModel(description = "用户外显信息")
public class UserInfo implements Serializable {
    @ApiModelProperty(value = "uid")
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "性别")
    private byte gender;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "粉丝数量")
    private Integer fan;

    @ApiModelProperty(value = "关注数量")
    private Integer follow;

    @ApiModelProperty(value = "获赞数量")
    private Integer like;

    @ApiModelProperty(value = "ip属地")
    private String ip;

    @ApiModelProperty(value = "自我介绍")
    private String info;

    @Serial
    private static final long serialVersionUID = 4782763419834L;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", name=" + name +
                ", gender=" + String.valueOf(gender) +
                ", level=" + level +
                ", fan=" + fan +
                ", follow=" + follow +
                ", like=" + like +
                ", ip=" + ip +
                ", info=" + info +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}