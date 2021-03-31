package com.share.entity;

import java.util.Date;

/**
 * @Description TODO:文章评论实体类
 * @Author Kang
 * @Date 2020-02-04 15:31
 * @Version 1.0
 */
public class Comment {
    //评论id
    private  Integer id;
    //文章id
    private Integer aid;
    //用户id
    private Long uid;
    //内容
    private String content;
    //创建时间
    private Date createTime;
    //评论类型（-1：已删除 0：普通评论 1：神评论）
    private Integer commentStatus;
    private Integer loveNum;
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", aid=" + aid +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", commentStatus=" + commentStatus +
                ", loveNum=" + loveNum +

                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(Integer loveNum) {
        this.loveNum = loveNum;
    }

}
