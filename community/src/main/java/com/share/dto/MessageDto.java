package com.share.dto;

import java.util.Date;

/**
 * @Description TODO:返回前台消息
 * @Author Kang
 * @Date 2020-02-07 20:12
 * @Version 1.0
 */
public class MessageDto {

    //评论id
    private Integer id;
    //发送方用户
    private Long uid;
    private String nickname;
    //所在帖子
    private Integer aid;
    private String content;
    private Date createTime;
    //消息类别  0：评论文章  1：评论回复 2：系统消息
    private Integer status;
    //文章标题
    private String title;
    //是否已读
    private Integer readStatus;

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", aid=" + aid +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", readStatus=" + readStatus +
                '}';
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
