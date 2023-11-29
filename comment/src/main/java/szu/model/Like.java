package szu.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @ClassName: Like
 * @Description: TODO
 * @Version 1.0
 * @Date: 2023-11-22 18:49
 * @Auther: UserXin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "like")
public class Like implements Serializable {
    @Serial
    private static final long serialVersionUID = 19826192849347L;

    @Id
    @ApiModelProperty("主键id")
    private String id; //对应mongo的主键id
    @ApiModelProperty("当前登录的用户id")
    private Integer uid; //当前登录的用户id
    @ApiModelProperty("点赞的（评论|动态）的id和发布该（评论|动态）的用户id")
    private HashMap<String,Integer> fidMap; //点赞的（评论|动态）的id和发布该（评论|动态）的用户id

}
