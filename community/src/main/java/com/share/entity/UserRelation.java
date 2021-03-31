package com.share.entity;

import java.util.Date;

/**
 * @Description TODO:用户关系表
 * @Author Kang
 * @Date 2020-02-20 12:52
 * @Version 1.0
 */
public class UserRelation {
    //关系id
    private Integer id;
    //主动方id
    private Long uid;
    //被动接受方rid
    private Long rid;
    //创建时间
    private Date createTime;
    //关系类别  0：无关系 1：粉丝（只开通粉丝关系） 2：朋友
    private Integer status;
    //修改关系时间
    private Date modifyTime;

    @Override
    public String toString() {
        return "UserRelation{" +
                "id=" + id +
                ", uid=" + uid +
                ", rid=" + rid +
                ", createTime=" + createTime +
                ", status=" + status +
                ", modifyTime=" + modifyTime +
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}
