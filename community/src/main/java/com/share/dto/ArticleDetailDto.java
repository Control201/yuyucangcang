package com.share.dto;

import java.util.Date;

/**
 * @Description TODO:文章详细内容展示
 * @Author YuYu
 * @Date 2020-02-04 10:50
 * @Version 1.0
 */
public class ArticleDetailDto {
    //用户
    private Long uid;
    private String nickname;
    private String avatar;
    //文章
    private Integer aid;
    private String title;
    private Integer commentNum;
    private Date createTime;
    private Integer browseNum;
    private String content;
    private Integer articleType;
    private Integer open_procedure;
    private Integer top;
    //文章状态
    private Integer status;
    //是否可评论
    private Integer reviewStatus;
    //经验
    private Integer exp;

    @Override
    public String toString() {
        return "ArticleDetailDto{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", aid=" + aid +
                ", title='" + title + '\'' +
                ", commentNum=" + commentNum +
                ", createTime=" + createTime +
                ", browseNum=" + browseNum +
                ", content='" + content + '\'' +
                ", articleType=" + articleType +
                ", open_procedure=" + open_procedure +
                ", top=" + top +
                ", status=" + status +
                ", reviewStatus=" + reviewStatus +
                ", exp=" + exp +
                '}';
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
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

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Integer getOpen_procedure() {
        return open_procedure;
    }

    public void setOpen_procedure(Integer open_procedure) {
        this.open_procedure = open_procedure;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
