package com.share.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.share.constant.Salt;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description TODO:shiro配置
 * @Author YuYu
 * @Date 2020-01-16 10:19
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {
    /**
     * 3.ShiroFilterFactoryBean
     * 主要配置一些相应的URL的规则和访问权限
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理
        bean.setSecurityManager(defaultWebSecurityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        bean.setLoginUrl("/user/login");
        // 设置无权限时跳转的 url; ---提示无权限需要登录
        bean.setUnauthorizedUrl("/other/403");
         /*
            annon：表示可以匿名使用
            authc：表示需要认证（登录）才能使用，没有参数
            roles：参数可以写多个，多个时必须加上双引号，并且参数之间用逗号分隔
                例如/admins/user/**=perms["user:add:*,
                user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
            rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
            port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,
            其中schmal是协议http或https等，serverName是你访问的host,
            8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
            authcBasic：没有参数表示httpBasic认证
            ssl：表示安全的url请求，协议为https
            user：当登入操作时不做检查
            perms：该资源必须授予权限才可以访问
         */
        //设置拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();
        //开放登陆接口 -- 所有人可登录
        filterMap.put("index", "anon");
        filterMap.put("catalog", "anon");
        filterMap.put("/blog/index", "anon");
        filterMap.put("/blog/detail", "anon");
        filterMap.put("/blog/boutique", "anon");
        filterMap.put("/blog/announcement", "anon");
        filterMap.put("/user/reg", "anon");
        filterMap.put("/user/reg/sendEmail", "anon");
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/forget", "anon");
        filterMap.put("/user/captcha", "anon");
        filterMap.put("/user/home/*", "anon");
//        logout注销登录  shiro自带
        filterMap.put("/user/logout", "logout");
        filterMap.put("/blog/relation", "roles[user]");
        //用户其他，需要角色登录
        filterMap.put("/user/**", "roles[user]");
        filterMap.put("/admin/**", "roles[user]");
        filterMap.put("/blog/add", "roles[user]");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        //filterMap.put("/**", "authc");
        bean.setFilterChainDefinitionMap(filterMap);

        //解决记住我中session丢失
        Map<String, Filter> fmap = new HashMap<>(16);
        fmap.put("addPrincipal", addPrincipalToSessionFilter());
        bean.setFilters(fmap);
        return bean;
    }

    /**
     * 2.注入 SecurityManager
     *
     * @return
     * @Bean(name = "securityManager")  其name为指定bean关联名称。
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        //打开记住我
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }


    /**
     * 1.注入 自定义身份认证 realm;
     *
     * @return
     */
    @Bean
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        //加入加密规则
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    /**
     * 设置加密对象，加密规则
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //使用md5加密算法
        hashedCredentialsMatcher.setHashAlgorithmName(Salt.HASH_ALGORITHM_NAME);
        //设置散列次数：加密次数 -3次(Salt.HASH_INTERACTIONS)
        hashedCredentialsMatcher.setHashIterations(Salt.HASH_INTERACTIONS);
        //此处的设置，true加密用的hex编码，false用的base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * 整合ShiroDialect ；整合shiro  thymeleaf
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }


//记住我功能

    /**
     * cookie管理对象
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间 ,单位秒，默认保存七天
        simpleCookie.setMaxAge(60 * 60 * 24 * 7);
        return simpleCookie;
    }

/**
 * Shiro自定义过滤器，注意写在ShiroFilterFactoryBean上边  记住我功能（重要）
 */
    @Bean
    public OncePerRequestFilter addPrincipalToSessionFilter() {
        return new AddPrincipalToSessionFilter();
    }

}
