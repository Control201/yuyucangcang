package com.share.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description TODO:
 * @Author Kang
 * @Date 2020-03-30 17:44
 * @Version 2.0
 */
@Component//注入到spring容器，方便后面使用
@ConfigurationProperties(prefix = "oauth")//对应application.yml中，oauth下参数
public class OAuthProperties {
    //获取applicaiton.yml下qq下所有的参数
    private QQProperties qq = new QQProperties();

    public QQProperties getQQ() {
        return qq;
    }

    public void setQQ(QQProperties qq) {
        this.qq = qq;
    }
}