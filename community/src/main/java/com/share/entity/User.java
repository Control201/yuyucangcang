package com.share.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * @Description TODO:User实体类 对应数据库
 * @Author Kang
 * @Date 2020-01-14 11:31
 * @Version 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 4268974200929711853L;

    private Integer id;
    //用户id 唯一
    private Long uid;
    //昵称
    private String nickname;
    private String password;
    //头像
    private String avatar;
    //性别：1是男，2是女
    private Integer sex;
    private String ip;
    //注册账号时间
    private Date createTime;
    //修改账号信息时间
    private Date updateTime;
    //账号状态：-1：异常（拉黑，无效） 0:删除（注销）  1：正常
    private Integer status;
    //最后登录时间
    private Date lastTime;
    //所属地点
    private String location;
    //生日
    private Date birthday;
    //注册渠道编号 默认1 自注册 2:qq 3:微博
    private Integer channelId;
    //手机号 唯一
    private String mobile;

    private String email;
    //备注 -- 用于管理员管理账号
    private String remark;
    //签名-这家伙很懒，还没有写个性签名
    private String signature;
    //用户类型:(1.为普通用户,2.为管理员,3.为开发者)
    private Integer authority;

    //经验
    private Integer exp;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                ", lastTime=" + lastTime +
                ", location='" + location + '\'' +
                ", birthday=" + birthday +
                ", channelId=" + channelId +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                ", signature='" + signature + '\'' +
                ", authority=" + authority +
                ", exp=" + exp +
                '}';
    }
}
