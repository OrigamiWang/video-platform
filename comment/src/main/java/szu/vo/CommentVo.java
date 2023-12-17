package szu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import szu.model.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName: CommentVo
 * @Description: 评论VO类
 * @Version 1.0
 * @Date: 2023-11-22 20:23
 * @Auther: UserXin
 */
@Data
public class CommentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 19826192849347L;
    private String id;    //id，主键对应着mongodb的主键_id
    private Integer userId;  //用户id
    private String username;  //用户名
    private String content;   //评论内容
    private Integer foreignId;  //评论对象的id
    private String targetUsername;   //回复的用户
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;   //评论时间
    private Integer likeNum;  //点赞数量
    private Integer replyNum;  //回复数量
    private Boolean isLiked; //是否被点赞
    private List<CommentVo> children;  //子评论

}
