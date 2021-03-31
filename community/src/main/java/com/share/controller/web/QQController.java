package com.share.controller.web;

import com.google.gson.Gson;
import com.share.dto.QQOpenidDTO;
import com.share.dto.ReslutTypeDto;
import com.share.dto.UserDto;
import com.share.entity.OAuthProperties;
import com.share.entity.User;
import com.share.service.QQService;
import com.share.service.impl.MessageServiceImpl;
import com.share.service.impl.UserServiceImpl;
import com.share.statusenum.AccountStatusEnum;
import com.share.statusenum.VisitorStatusEnum;
import com.share.utils.HttpsUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description TODO:qq登录相关
 * @Author Kang
 * @Date 2020-03-30 17:52
 * @Version 2.0
 */
@Controller
@RequestMapping("/user/qq")
public class QQController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private OAuthProperties oauth;

    @Autowired
    private QQService qqService;
    @Autowired
    private MessageServiceImpl messageServiceImpl;

    //QQ登陆对外接口，只需将该接口放置html的a标签href中即可
    @GetMapping("/login")
    public void loginQQ(HttpServletResponse response) {
        try {
            response.sendRedirect(oauth.getQQ().getCode_callback_uri() + //获取code码地址
                    "?client_id=" + oauth.getQQ().getClient_id()//appid
                    + "&state=" + UUID.randomUUID() + //这个说是防攻击的，就给个随机uuid吧
                    "&redirect_uri=" + oauth.getQQ().getRedirect_uri() +//这个很重要，这个是回调地址，即就收腾讯返回的code码
                    "&response_type=code");//授权模式，授权码模式
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //接收回调地址带过来的code码
    @GetMapping("/rollback")
    public String authorizeQQ(Map<String, String> msg, String code,HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", oauth.getQQ().getRedirect_uri());
        params.put("client_id", oauth.getQQ().getClient_id());
        params.put("client_secret", oauth.getQQ().getClient_secret());
        //获取access_token如：access_token=9724892714FDF1E3ED5A4C6D074AF9CB&expires_in=7776000&refresh_token=9E0DE422742ACCAB629A54B3BFEC61FF
        String result = HttpsUtils.doGet(oauth.getQQ().getAccess_token_callback_uri(), params);
        //对拿到的数据进行切割字符串
        String[] strings = result.split("&");
        //切割好后放进map
        Map<String, String> reulsts = new HashMap<>();
        for (String str : strings) {
            String[] split = str.split("=");
            if (split.length > 1) {
                reulsts.put(split[0], split[1]);
            }
        }
        //到这里access_token已经处理好了
        //下一步获取openid，只有拿到openid才能拿到用户信息
        String openidContent = HttpsUtils.doGet(oauth.getQQ().getOpenid_callback_uri() + "?access_token=" + reulsts.get("access_token"));
        //接下来对openid进行处理
        //截取需要的那部分json字符串
        String openid = openidContent.substring(openidContent.indexOf("{"), openidContent.indexOf("}") + 1);
        Gson gson = new Gson();
        //将返回的openid转换成DTO
        QQOpenidDTO qqOpenidDTO = gson.fromJson(openid, QQOpenidDTO.class);
        //接下来说说获取用户信息部分
        //登陆的时候去数据库查询用户数据对于openid是存在，如果存在的话，就不用拿openid获取用户信息了，而是直接从数据库拿用户数据直接认证用户，
        // 否则就拿openid去腾讯服务器获取用户信息，并存入数据库，再去认证用户
        //下面关于怎么获取用户信息，并登陆
/*        params.clear();
        params.put("access_token", reulsts.get("access_token"));//设置access_token
        params.put("openid", qqOpenidDTO.getOpenid());//设置openid
        params.put("oauth_consumer_key", qqOpenidDTO.getClient_id());//设置appid
        //获取用户信息
        String userInfo = HttpsUtils.doGet(oauth.getQQ().getUser_info_callback_uri(), params);
        //获取subject用于存放session
        QQDTO qqDTO = gson.fromJson(userInfo, QQDTO.class);*/
        //这里拿用户昵称，作为用户名，openid作为密码（正常情况下，在开发时候用openid作为用户名，再自己定义个密码就可以了）
        try {
//     openid: CB40309CEC6AD6259624831072A6D4CC
            int status = checkQQ(qqOpenidDTO.getOpenid(),request);
            if (status == 1) {
//                返回到绑定页面
                return "user/bind";
            }else if (status == -1){
                msg.put("msg", "访问异常！");
                return "user/login";
            }
        } catch (Exception e) {
            msg.put("msg", "第三方登陆失败,请联系站长！");
            return "user/login";
        }
//
        return "redirect:/index";
    }
//    检查qq是否注册绑定过--对第三方账号信息处理
    private int checkQQ(String openId,HttpServletRequest request) throws Exception {
//        没有openId直接报错转跳登录页面
        if (openId == null){
            return -1;
        }
//        查询是否qq用户是否第一次登陆
        Long uid = qqService.findUidByOpenId(openId);
        Subject subject = SecurityUtils.getSubject();
//        已经注册并绑定账号---可直接登录
        if (uid != null) {
            Session session = subject.getSession();
            session.setAttribute("QQMark","qqToken");
//            根据uid查询账号和密码
                User user = qqService.findUserByUid(uid);
//                直接放入session，因为不知道明文密码，则无法正常方式登录，即不可使用记住我功能
               UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(),user.getPassword(),true);
                subject.login(token);
                //更新登录数据。。
                //为了安全起见，自己封装一个dto类，防止重要信息泄露
                UserDto userDto = userServiceImpl.findUserById(user.getUid());
                //记录新消息数
                Long rid = userDto.getUid();
                Integer newMessageTotal = messageServiceImpl.findNewMessageTotal(rid);
                //放回前端页面
                if (newMessageTotal > 0) {
                    session.setAttribute("newMessageTotal", newMessageTotal);
                }
                session.setAttribute("loginUser", userDto);
                //更新登录ip 和登录时间一起。
                String remoteAddr = request.getRemoteAddr();
                user.setIp(remoteAddr);
                //更新最后登录时间
                userServiceImpl.updateLastTime(user);
                return 2;
        } else {
            Session session = subject.getSession();
            session.setAttribute("openId", openId);
//          先注册账号，再绑定qq;
            return 1;
        }
    }

    /**
     * 绑定qq,并登录
     *
     * @param username
     * @param password
     * @param verCode
     * @param request
     * @return
     */
    @PostMapping("/bindQQ")
    @ResponseBody
    public ReslutTypeDto bindQQ(String username,
                                String password,
                                String verCode,
                                Boolean rememberMe,
                                HttpServletRequest request) {
        // 获取session中的验证码
        String sessionCode = (String) request.getSession().getAttribute("captcha");
        // 判断验证码
        if (verCode == null || !sessionCode.equals(verCode.trim().toLowerCase())) {
            //"验证码不正确"
            return new ReslutTypeDto(VisitorStatusEnum.Captcha_Fail);
        }
//        获取session中缓存的openId
        String openId = (String) request.getSession().getAttribute("openId");
        if (openId == null) {
//            返回qq登陆绑定已过期请重新登录绑定
            return new ReslutTypeDto(VisitorStatusEnum.Bind_OpenId);
        }
        User user = userServiceImpl.findUserByName(username);
        //没有这个人
        if (user == null) {
            //抛出异常绑定账号不存在
            return new ReslutTypeDto(VisitorStatusEnum.Login_Fail);
        }
        String oId = qqService.findOpenIdByUid(user.getUid());
//        如果查询存在此值，则证明已经绑定了
        if (oId != null){
            return new ReslutTypeDto(VisitorStatusEnum.Bind_QQ_Exist);
        }
        Integer status = user.getStatus();
        if (!status.equals(1)) {
            return new ReslutTypeDto(AccountStatusEnum.ABNORMAL);
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //设置开启记住我
            token.setRememberMe(rememberMe);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            // 账号绑定qq
            qqService.addQQInfo(user.getUid(), openId);
            //更新登录数据。。
            //为了安全起见，自己封装一个dto类，防止重要信息泄露
            UserDto userDto = userServiceImpl.findUserById(user.getUid());
            //放回前端页面
            Session session = subject.getSession();
            session.setAttribute("loginUser", userDto);
            //更新登录ip 和登录时间一起。
            String remoteAddr = request.getRemoteAddr();
            user.setIp(remoteAddr);
            //更新最后登录时间
            userServiceImpl.updateLastTime(user);
            //登录成功直接转跳
            return new ReslutTypeDto(VisitorStatusEnum.Bind_QQ_OK);
        } catch (
                UnknownAccountException e) {
            //"账号错误"
            return new ReslutTypeDto(VisitorStatusEnum.Login_Fail);
        } catch (
                IncorrectCredentialsException e) {
            //"密码错误"
            return new ReslutTypeDto(VisitorStatusEnum.Login_Fail);
        } catch (Exception e) {
            //授权登录失败
            return new ReslutTypeDto(VisitorStatusEnum.Login_Fail);
        }
    }

}
