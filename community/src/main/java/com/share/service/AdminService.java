package com.share.service;

/**
 * @Description TODO:管理员
 * @Author Kang
 * @Date 2020-03-04 14:40
 * @Version 1.0
 */
public interface AdminService {
    /**
     * 设置置顶
     * @param top
     * @param aid
     * @return
     */
    Integer setTopBlog( Integer top,Integer aid );

    /**
     * 设置精贴
     * @param article_type
     * @param aid
     * @return
     */
    Integer setBoutiqueBlog(Integer article_type,Integer aid );
}
