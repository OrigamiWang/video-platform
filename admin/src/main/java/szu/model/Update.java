package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Update implements Serializable {
    @ApiModelProperty(value = "动态id")
    private Integer id;

    @ApiModelProperty("视频id")
    private Integer vid;

    @ApiModelProperty(value = "发布者的用户id")
    private Integer uid;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "状态码")
    private Integer status;

    @ApiModelProperty(value = "时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "多媒体urls的json")
    private String urls;

    private static final long serialVersionUID = 1L;

}