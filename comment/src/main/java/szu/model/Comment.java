package szu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description comment
 * @author UserXin
 * @date 2023-11-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;    //id，主键
    private Integer userId;  //用户id
    private String username;  //用户名
    private String content;   //评论内容
    private Integer foreignId;  //评论对象的id
    private Integer foreignType;  //评论对象的类型
    private Integer pid;  //父级评论的id
    private String targetUsername;   //回复的用户
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;   //评论时间

    @Transient
    private List<Comment> children;  //不在数据库中存储只做临时使用

}