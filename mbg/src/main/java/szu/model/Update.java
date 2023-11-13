package szu.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Update implements Serializable {
    @ApiModelProperty(value = "动态id")
    private Integer id;

    @ApiModelProperty(value = "发布者的用户id")
    private Integer uid;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "正文")
    private String content;

    @ApiModelProperty(value = "状态码,状态码：0-未通过审核；1-通过审核；2-已下架")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", status=").append(status);
        sb.append(", type=").append(type);
        sb.append(", datetime=").append(datetime);
        sb.append(", urls=").append(urls);
        sb.append(", pid=").append(pid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}