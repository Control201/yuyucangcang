package com.share.entity;

import java.util.Date;

/**
 * @Description TODO:用户消息类
 * @Author Kang
 * @Date 2020-02-07 18:02
 * @Version 1.0
 */
public class Message {
    //消息id
    private Integer id;
    //文章id
    private Integer aid;
    //发送用户id
    private Long uid;
    //接受用户id
    private Long rid;
    //创建时间
    private Date createTime;
    //状态
    private Integer status;
    //消息类容
    private String content;
    //是否已读
    private Integer readStatus;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", aid=" + aid +
                ", uid=" + uid +
                ", rid=" + rid +
                ", createTime=" + createTime +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", readStatus=" + readStatus +
                '}';
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
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
