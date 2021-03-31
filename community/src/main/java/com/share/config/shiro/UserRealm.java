package com.share.config.shiro;

import com.share.constant.Salt;
import com.share.dto.UserDto;
import com.share.entity.Message;
import com.share.entity.User;
import com.share.service.UserService;
import com.share.service.impl.MessageServiceImpl;
import com.share.service.impl.UserServiceImpl;
import com.share.utils.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description TODO:自定义Realm  extends AuthorizingRealm
 * @Author Kang
 * @Date 2020-01-16 10:25
 * @Version 1.0
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userServiceImpl;


    /**
     * 角色授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的对象--- 数据库授权
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        Integer authority = currentUser.getAuthority();
//        根据数字判断是否有用户权限
        if (authority != null && authority > 0) {
            info.addRole("user");
        }
        return info;
    }

    /**
     * 登录验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据Token中的username
        //从数据库获取对应用户名密码的用户
        User user = userServiceImpl.findUserByName(token.getUsername());
        /*        //为了安全起见，自己封装一个dto类，防止重要信息泄露
        UserDto userDto = userServiceImpl.findUserById(user.getUid());
        //记录新消息数
        Long rid = userDto.getUid();
        Integer newMessageTotal = messageServiceImpl.findNewMessageTotal(rid);
        */
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        String qqMark = (String)session.getAttribute("QQMark");

        /**
         * 判断第三方登录时，用户端传入密码为数据库密码（已经盐值加密），在和数据密码比较是还会加密一次
         * 所以比较会出现多了一次加密过程，比较出错
         */
        if (qqMark!=null && qqMark.equals("qqToken")){
//            使用后移除QQMark
        session.removeAttribute("QQMark");
//            密码需要二次加密
          String oldPassword =  user.getPassword();
          String  newPassword= SaltUtils.getPwdPlus(oldPassword);
          user.setPassword(newPassword);
        }
        /*加盐  salt*/
        ByteSource byteSource = ByteSource.Util.bytes(Salt.ADD_SALT);
        //密码认证
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                byteSource,
                getName());// 指定当前 Realm 的类名);
        return info;
    }
}
