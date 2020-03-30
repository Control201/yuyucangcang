package com.share.config.shiro;


import com.share.dto.UserDto;
import com.share.entity.User;
import com.share.service.impl.MessageServiceImpl;
import com.share.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description TODO:解决session丢失,记住我功能（重要）
 * @Author YuYu
 * @Date 2020-02-25 10:54
 * @Version 1.0
 */
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    MessageServiceImpl messageServiceImpl;
    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        //查询当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        //由于是继承的OncePerRequestFilter，没办法设置session
        //这里发现可以将servletReques强转为子类，所以使用request.getsiion())
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        //判断用户是不是通过自动登录进来的
        //当session为空的时候，重新更新数据
        //   同时满足一下条件就可以执行一次数据交互，否则多次运行过滤器，加大服务器负载
        if (subject.isRemembered() && session.getAttribute("loginUser") == null) {
            User userPrincipal = (User) subject.getPrincipal();
            String userName = userPrincipal.getEmail();
            //用户名为空,则退出
            if (userName == null) {
                return;
            }
            //根据用户名查询该用户的信息
            //为了安全起见，自己封装一个dto类，防止重要信息泄露
            UserDto userDto = userServiceImpl.findUserById(userPrincipal.getUid());
            if (userDto == null) {
                return;
            }
            //记录新消息数
            Long rid = userDto.getUid();
            Integer newMessageTotal = messageServiceImpl.findNewMessageTotal(rid);
            //放回前端页面
            if (newMessageTotal > 0) {
                session.setAttribute("newMessageTotal", newMessageTotal);
            }
            //更新登录ip 和最近一次登录时间一起。
            String remoteAddr = request.getRemoteAddr();
            userPrincipal.setIp(remoteAddr);
            //更新最后登录时间
            userServiceImpl.updateLastTime(userPrincipal);
            //把查询到的用户信息设置为session，时效为3600秒，springboot默认保存30分钟session
            session.setAttribute("loginUser", userDto);
            session.setMaxInactiveInterval(3600);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
