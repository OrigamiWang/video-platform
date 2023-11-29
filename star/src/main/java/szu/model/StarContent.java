package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: StarContent
 * @Description: 收藏夹内容
 * @Version 1.0
 * @Date: 2023-11-23 19:34
 * @Auther: UserXin
 */
@Data
public class StarContent {
    @ApiModelProperty("主键id")
    private Integer id;  //主键id
    @ApiModelProperty("视频id")
    private Integer vid;  //视频id
    @ApiModelProperty("收藏夹id")
    private Integer sid;  //收藏夹id
    @ApiModelProperty("收藏时间")
    private LocalDateTime starDate;  //收藏时间
}
