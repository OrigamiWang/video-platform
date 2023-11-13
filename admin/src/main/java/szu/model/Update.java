package szu.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class Update implements Serializable {
    @ApiModelProperty(value = "动态id")
    private Integer id;

    @ApiModelProperty(value = "发布者的用户id")
    private Integer uid;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "状态码")
    private Integer status;

    @ApiModelProperty(value = "动态类型")
    private Integer type;

    @ApiModelProperty(value = "时间")
    private Date datetime;

    @ApiModelProperty(value = "多媒体urls的json")
    private String urls;

    @ApiModelProperty(value = "分区id")
    private Integer pid;

    private static final long serialVersionUID = 1L;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", uid=" + uid +
                ", title=" + title +
                ", content=" + content +
                ", status=" + status +
                ", type=" + type +
                ", datetime=" + datetime +
                ", urls=" + urls +
                ", pid=" + pid +
                ", serialVersionUID=" + serialVersionUID +
                "]";
        return sb;
    }
}