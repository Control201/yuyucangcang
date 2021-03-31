package com.share.entity;

import java.util.Date;

/**
 * @Description TODO:收藏文章实体类
 * @Author Kang
 * @Date 2020-02-17 12:41
 * @Version 1.0
 */
public class CollectBlog {
    //评论id
    private Integer aid;
    //用户id
    private Long uid;
    //创建时间
    private Date createTime;
    //状态  0：取消收藏 1：收藏
    private Integer status;

    @Override
    public String toString() {
        return "CollectBlog{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
}
