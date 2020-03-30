package com.share.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description TODO:文章相关缩略展示页面
 * @Author YuYu
 * @Date 2020-02-02 16:25
 * @Version 1.0
 */
public class ArticleDto  implements Serializable {
    private static final long serialVersionUID = 3662882499649723582L;

    private Long uid;
    private String nickname;
    private String avatar;
    private Integer aid;
    private String title;
    private Integer commentNum;
    private Integer articleType;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy/MM/dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    private Date updateTime;
    private Integer browseNum;

    @Override
    public String toString() {
        return "ArticleDto{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", aid=" + aid +
                ", title='" + title + '\'' +
                ", commentNum=" + commentNum +
                ", articleType=" + articleType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", browseNum=" + browseNum +
                '}';
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
