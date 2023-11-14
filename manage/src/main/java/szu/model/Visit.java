package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * 访问量实体类
 */
public class Visit implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "上次更新时间")
    private LocalDateTime lastUpdate;
    @ApiModelProperty(value = "访问接口，all为所有接口的总和")
    private String api;
    @ApiModelProperty(value = "访问量")
    private Integer visits;
    @ApiModelProperty(value = "yyyy-mm-dd的时间id")
    private String timeId;
}
