package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.model
 * @Author: Origami
 * @Date: 2023/11/2 21:41
 */
@Data
public class Role {
    @ApiModelProperty("rid")
    private Integer id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色权限描述")
    private String describe;
}
