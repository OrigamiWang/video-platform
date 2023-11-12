package szu.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRole {
    @ApiModelProperty("用户id")
    private Integer uid;
    @ApiModelProperty("权限id")
    private Integer rid;
}
