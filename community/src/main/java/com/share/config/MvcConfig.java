package com.share.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description TODO:配置mvc
 * @Author Kang
 * @Date 2020-01-15 12:19
 * @Version 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * 只能通过以下设定urlPath方式来访问页面
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/blog/detail.html").setViewName("blog/detail");
        registry.addViewController("/blog/index.html").setViewName("blog/index");
        registry.addViewController("/user/login.html").setViewName("user/login");
        registry.addViewController("/user/forget.html").setViewName("user/forget");
        registry.addViewController("/user/reg.html").setViewName("user/reg");

    }
    //添加资源处理器  便于网页访问本地资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:/var/file/upload/img/");
//        registry.addResourceHandler("/img/**").addResourceLocations("file:D:/project/communityFile/");
    }
}
