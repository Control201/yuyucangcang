package com.share.entity;

import java.util.Date;

/**
 * @Description TODO:点赞记录实体类
 * @Author YuYu
 * @Date 2020-02-06 12:11
 * @Version 1.0
 */
public class LoveRecord {
    //点赞id
    private  Integer id;
    //评论id
    private Integer acid;
    //用户id
    private Long uid;

    //创建时间
    private Date createTime;
    //状态  0：没点赞 1：点赞
    private Integer status;

    @Override
    public String toString() {
        return "LoveRecord{" +
                "id=" + id +
                ", acid=" + acid +
                ", uid=" + uid +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcid() {
        return acid;
    }

    public void setAcid(Integer acid) {
        this.acid = acid;
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
