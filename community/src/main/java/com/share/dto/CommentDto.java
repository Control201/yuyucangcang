package com.share.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Description TODO:展示评论相关
 * @Author Kang
 * @Date 2020-02-04 15:46
 * @Version 1.0
 */
public class CommentDto {
    //用户
    private String uid;
    private String nickname;
    private String avatar;
    private  Integer exp;
    //评论内容 --id为评论id
    private Integer id;
    private String content;
    //对时间进行处理  防止美化时间出问题--主要手机适配
    //对特殊时间格式处理：2020-02-05T04:02:42.000+0000
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //评论类型（-1：已删除 0：普通评论 1：神评论）
    private Integer commentStatus;
    private Integer loveNum;
    //特殊字段   点赞用户的评论点赞状态
    private Integer status;

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", exp=" + exp +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", commentStatus=" + commentStatus +
                ", loveNum=" + loveNum +
                ", status=" + status +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
