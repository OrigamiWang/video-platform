package szu.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class User implements Serializable {
    @ApiModelProperty(value = "uid")
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "性别")
    private Boolean gender;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "经验值")
    private Integer exp;

    @ApiModelProperty(value = "用户目前的状态")
    private byte[] status;

    @ApiModelProperty(value = "用户身份")
    private byte[] role;

    @ApiModelProperty(value = "粉丝数量")
    private Integer fan;

    @ApiModelProperty(value = "关注数量")
    private Integer follow;

    @ApiModelProperty(value = "获赞数量")
    private Integer like;

    @ApiModelProperty(value = "ip属地")
    private String ip;

    @ApiModelProperty(value = "电话/账号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "自我介绍")
    private String info;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public byte[] getStatus() {
        return status;
    }

    public void setStatus(byte[] status) {
        this.status = status;
    }

    public byte[] getRole() {
        return role;
    }

    public void setRole(byte[] role) {
        this.role = role;
    }

    public Integer getFan() {
        return fan;
    }

    public void setFan(Integer fan) {
        this.fan = fan;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", gender=").append(gender);
        sb.append(", level=").append(level);
        sb.append(", exp=").append(exp);
        sb.append(", status=").append(status);
        sb.append(", role=").append(role);
        sb.append(", fan=").append(fan);
        sb.append(", follow=").append(follow);
        sb.append(", like=").append(like);
        sb.append(", ip=").append(ip);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", info=").append(info);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}