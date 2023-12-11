package szu.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**

 * @ClassName: Comment
 * @Description: 评论实体类
 * @Version 1.0
 * @Date: 2023-11-09 21:46
 * @Auther: UserXin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="comment")//可以省略，如果省略，则默认使用类名小写映射集合
public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 19826192849347L;
    @ApiModelProperty("id，主键")
    @Id
    private String id;    //id，主键对应着mongodb的主键_id
    @ApiModelProperty("用户id")
    private Integer userId;  //用户id
    @ApiModelProperty("用户名")
    private String username;  //用户名
    @ApiModelProperty("评论内容")
    private String content;   //评论内容
    @ApiModelProperty("评论对象的id")
    private Integer foreignId;  //评论对象的id
    @ApiModelProperty("回复的用户")
    private String targetUsername;   //回复的用户
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("评论时间")
    private LocalDateTime createTime;   //评论时间
    @ApiModelProperty("点赞数量")
    private Integer likeNum;  //点赞数量
    @ApiModelProperty("回复数量")
    private Integer replyNum;  //回复数量
    @ApiModelProperty("是否置顶（1：置顶 0：不置顶）")
    private Integer isTop; //是否置顶
    @ApiModelProperty("子评论")
    private List<Comment> children;  //子评论
}

