package com.share.entity;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO:文章实体类
 * @Author YuYu
 * @Date 2020-02-01 18:45
 * @Version 1.0
 */
public class Blog {
    //文章id
    private Integer aid;
    //标题
    private String title;
    //用户id
    private Long uid;
    //创建时间
    private Date createTime;
    //浏览量
    private Integer browseNum;
    //内容
    private String content;
    //文章状态
    private Integer status;
    //是否置顶
    private Integer top;
    //文章类型  0：普通帖子，1：精贴  2：公告
    private Integer articleType;
    //公开方式  0：公开，1：私密  2；朋友可见
    private Integer openProcedure;
    //是否允许评论
    private Integer reviewStatus;
    //评论数
    private Integer commentNum;
    //点赞数
    private Integer loveNum;
    //临时文件名-- 数组
    private String tempFile;

    //时间权重  用于排行显示
    private Date updateTime;


    @Override
    public String toString() {
        return "Blog{" +
                "aid=" + aid +
                ", title='" + title + '\'' +
                ", uid=" + uid +
                ", createTime=" + createTime +
                ", browseNum=" + browseNum +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", top=" + top +
                ", articleType=" + articleType +
                ", openProcedure=" + openProcedure +
                ", reviewStatus=" + reviewStatus +
                ", commentNum=" + commentNum +
                ", loveNum=" + loveNum +
                ", tempFile='" + tempFile + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Blog() {
    }

    public String getTempFile() {
        return tempFile;
    }

    public void setTempFile(String tempFile) {
        this.tempFile = tempFile;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Integer getOpenProcedure() {
        return openProcedure;
    }

    public void setOpenProcedure(Integer openProcedure) {
        this.openProcedure = openProcedure;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(Integer loveNum) {
        this.loveNum = loveNum;
    }
}
