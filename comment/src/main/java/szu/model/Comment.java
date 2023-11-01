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
    private Integer id;
    private Integer userId;
    private String username;
    private String content;
    private Integer foreignId;
    private Integer foreignType;
    private Integer pid;
    private String targetUsername;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Transient
    private List<Comment> children;  //不在数据库中存储只做临时使用

}