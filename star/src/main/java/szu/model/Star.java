package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName: Star
 * @Description: 收藏夹实体类
 * @Version 1.0
 * @Date: 2023-11-23 19:31
 * @Auther: UserXin
 */
@Data
public class Star implements Serializable {
    @Serial
    private static final long serialVersionUID = 19826192849347L;
    @ApiModelProperty("主键id")
    private Integer id;  //主键id
    @ApiModelProperty("用户id")
    private Integer uid;  //用户id
    @ApiModelProperty("收藏夹名称")
    private String starName;  //收藏夹名称
    @ApiModelProperty("收藏数量")
    private Integer starNum;  //收藏数量
}
