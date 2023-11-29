package szu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: StarVo
 * @Description: 收藏夹内视频展示
 * @Version 1.0
 * @Date: 2023-11-28 11:16
 * @Auther: UserXin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 19826192849347L;
    @ApiModelProperty("视频id")
    private Integer vid;
    @ApiModelProperty("视频标题")
    private String title;
    @ApiModelProperty("视频播放量")
    private Integer playNum;
    @ApiModelProperty("视频收藏数量")
    private Integer starNum;
    @ApiModelProperty("视频作者名称")
    private String upName;
    @ApiModelProperty("视频上传时间")
    private LocalDateTime uploadTime;
    @ApiModelProperty("收藏时间")
    private LocalDateTime starDate;
}
