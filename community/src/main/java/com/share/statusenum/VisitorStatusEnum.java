package com.share.statusenum;

/**
 * @Description TODO:游客端 登录，注册  ajax响应返回信息
 * @Author Kang
 * @Date 2020-01-19 14:55
 * @Version 1.0
 */
public enum VisitorStatusEnum {
    /**
     * 游客端 登录，注册  ajax响应返回信息
     */
    Login_OK("登录成功,正在转跳中..", 1000),
    Login_Fail("账号或密码错误~",1001),
    Captcha_Fail("验证码错误~",1002),
    Email_Captcha_Fail("邮箱验证码错误~",1003),
    Email_Captcha_Invalid("邮箱验证码无效，请重新获取~",1004),
    Send_Email_Captcha_OK("发送成功，快去邮箱查收吧！",1005),
    Register_OK("注册成功！", 1006),
    Register_Fail("注册失败~",1007),
    Server_Busy("服务器繁忙,请稍后再试~",1008),
    Email_Invalid("邮箱无效哦！",1009),
    Account_Registered("该账号已被注册哦，换一个试试吧！",1010),
    Account_Invalid("账号不存在哦！",1011),
    Password_Modify_OK("密码修改成功！",1012),
    Password_Modify_Fail("密码修改失败~",1013),
    Bind_QQ_Exist("账号已被绑定",1014),
    Bind_OpenId("授权ID失效,请重新登录",1015),
    Bind_QQ_OK("授权成功,正在转跳中..",1016);


    private String message;
    private int status;
    VisitorStatusEnum(String message, int status) {
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
