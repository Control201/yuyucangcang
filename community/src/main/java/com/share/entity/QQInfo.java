package com.share.entity;

import java.util.Date;
import java.util.Objects;

/**
 * @Description TODO:qq信息管理
 * @Author YuYu
 * @Date 2020-04-01 16:07
 * @Version 2.0
 */
public class QQInfo {
    //    qq表id
    private Integer id;
    //    用户id
    private Long uid;
    // qq唯一id
    private String openId;
    //   注册时间
    private Date createTime;

    @Override
    public String toString() {
        return "QQInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", openId='" + openId + '\'' +
                ", createTime=" + createTime +
                '}';
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
