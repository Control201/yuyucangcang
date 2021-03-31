package com.share.dto;

/**
 * @Description TODO:编写有关博客类
 * @Author Kang
 * @Date 2020-02-01 11:17
 * @Version 1.0
 */
public class BlogDto {
    //用户id
    private String uid;
    //标题
    private String title;
    //内容含html
    private String content;
    //上传图片时使用
    private String file;
    //是否允许评论
    private String reviewStatus;
    //公开方式
    private String openProcedure;

    @Override
    public String toString() {
        return "BlogDto{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", context='" + content + '\'' +
                ", file='" + file + '\'' +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", openProcedure='" + openProcedure + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getOpenProcedure() {
        return openProcedure;
    }

    public void setOpenProcedure(String openProcedure) {
        this.openProcedure = openProcedure;
    }
}
