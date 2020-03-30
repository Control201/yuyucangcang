package com.share.statusenum;

/**
 * @Description TODO:博客相关
 * @Author YuYu
 * @Date 2020-02-01 18:21
 * @Version 1.0
 */
public enum  BlogStatusEnum {
    /**
     * 博客相关
     */
    Blog_Publish_OK("帖子发布成功",3001),
    Blog_Publish_Fail("帖子发布异常",3002),
    Review_Blog_OK("发表评论成功",3003),
    Review_Blog_Fail("发表评论异常",3004),
    like_Comment_OK("点赞成功",3005),
    like_Comment_Fail("取消点赞",3006),
    //专指点赞
    like_Comment_Exception("操作异常",3007),
    del_Comment_OK("删除成功",3008),
    //专指删除评论
    del_Comment_Exception("操作异常",3009),
    del_Blog_OK("删除成功,正在转跳中..",3010),
    //专指删除帖子
    del_Blog_Exception("操作异常",3011),
    collect_Blog_OK("收藏成功",3012),
    collect_Blog_Fail("取消收藏",3013),
    //专指收藏文章
    collect_Blog_Exception("操作异常",3014);

    private String message;
    private int status;

    BlogStatusEnum(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
