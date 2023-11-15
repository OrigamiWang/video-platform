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
public class Permission {
    @ApiModelProperty("权限id")
    private Integer id;
    @ApiModelProperty("权限名称")
    private String name;
}
