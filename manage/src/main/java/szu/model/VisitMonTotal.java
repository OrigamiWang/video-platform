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
/**
 * 查询某个月访问总量的实体类，无数据库表对应
 */
public class VisitMonTotal {
    @ApiModelProperty(value = "访问接口，all为总量")
    private String api;
    @ApiModelProperty(value = "访问量")
    private Integer visits;
    @ApiModelProperty(value = "年份")
    private String year;
    @ApiModelProperty(value = "月份")
    private String mon;
}
