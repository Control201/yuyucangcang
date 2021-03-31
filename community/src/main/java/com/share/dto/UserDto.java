package com.share.dto;

import com.share.statusenum.AccountStatusEnum;

import java.util.Date;

/**
 * @Description TODO:数据传输层：有关user类
 * @Author Kang
 * @Date 2020-01-14 20:23
 * @Version 1.0
 */
public class UserDto {
    private Long uid;

    private String uidStr;
    //昵称
    private String nickName;
    //头像
    private String avatar;

    //性别：1是男，2是女
    private Integer sex;
    //性别字符类型
    private String sexStr;
    //注册账号时间
    private Date createTime;
    //账号状态：-1：异常（拉黑，无效） 0:删除（注销）  1：正常 -- 采用enum方式
    private Integer status;
    //账号状态字符类型
    private String statusStr;
    //最后登录时间
    private Date lastTime;
    //所属地点
    private String location;
    private String mobile;
    //生日
    private Date birthday;
    private String email;
    //签名-这家伙很懒，还没有写个性签名
    private String signature;
    //用户类型:(1.为普通用户,2.为管理员,3.为开发者)
    private Integer authority;
    //经验
    private Integer exp;



    public UserDto() {

    }

    public UserDto(Long uid, String nickName, String avatar, Integer sex, Date createTime, AccountStatusEnum accountStatusEnum,
                   Date lastTime, String location, Date birthday, String mobile, String email, String signature, Integer authority,Integer exp) {
        this.uid = uid;
        this.nickName = nickName;
        this.avatar = avatar;
        this.sex = sex;
        //直接调用本地getSexStr（）方法赋值 -- 必须在sex属性赋值后再运行，否则空指针异常
        this.sexStr = getSexStr();
        this.createTime = createTime;
        this.status = accountStatusEnum.getStatus();
        this.statusStr = accountStatusEnum.getInfo();
        this.lastTime = lastTime;
        this.location = location;
        this.birthday = birthday;
        this.mobile = mobile;
        this.email = email;
        this.signature = signature;
        this.authority = authority;
        this.exp = exp;
    }

    public String getUidStr() {
        uidStr = uid.toString();
        return uidStr;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public void setUidStr(String uidStr) {
        this.uidStr = uidStr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getSexStr() {
        if (sex == 1) {
            sexStr = "男";
        } else if (sex == 2) {
            sexStr = "女";
        }
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "UserDto{" +
                "uid=" + uid +
                ", uidStr='" + uidStr + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", sexStr='" + sexStr + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", lastTime=" + lastTime +
                ", location='" + location + '\'' +
                ", mobile='" + mobile + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                ", authority=" + authority +
                ", exp=" + exp +
                '}';
    }
}
