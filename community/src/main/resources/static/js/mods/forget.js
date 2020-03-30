// 忘记密码
layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = layui.layer;
    form.verify({
        // email_code为verify的值，验证顺序和标签排放顺序有关
        repass: function (value) {
            var L_pass = $("#L_pass").val().trim();
            var L_repass = $("#L_repass").val().trim();
            var L_username = $("#L_username").val().trim();
            var email_code = $("#email_code").val().trim();
            var message = '';
            var reg = new RegExp("^[\\S]{8,18}$");
            if (!reg.test(value)) {
                return '密码必须8到18位，且不能出现空格';
            }
            if (value != L_pass) {
                return '两次密码不一样哦！';
            }
            $.ajax({
                type: "POST",
                url: "/user/forget",
                data: {
                    verCode: email_code,
                    username: L_username,
                    password: value,
                },
                async: false, //改为同步请求
                dataType: 'json',
                success: function (data) {
                    //设置成功
                    if (data.status === 1012) {
                        layer.msg(data.message, {time: 1000, icon: 6});
                        setTimeout(function () {
                            layer.msg(data.message + ',正在转跳登录页面中。。', {
                                time: 2000,
                                icon: 16, shade: 0.01
                            });
                        }, 1000);
                        var current_time = 3;
                        Countdown = setInterval(function () {
                            current_time--;
                            if (current_time < 1) {
                                clearInterval(Countdown);
                                window.location.href = "/user/login"; //#设定跳转的链接地址
                            }
                        }, 1000);
                    } else {
                        message = data.message;
                    }
                },
                error: function (data) {
                    message = data.message;
                    window.location.href = "/user/forget";
                }
            });
            //需要注意 需要将返回信息写在ajax方法外
            if (message !== '')
                return message;
        },
        email_code: [
            /^[A-Za-z0-9]{4}$/
            , '验证码是4个字符啊'
        ],
        pass: [
            /^[\S]{8,18}$/
            , '密码必须8到18位，且不能出现空格'
        ]
    });
});
//邮箱验证
var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
//发送短信验证码
function sendMessage() {
    var email = $("#L_username").val();
    if (email == null || email === '') {
        layer.msg("亲,你的邮箱还没填呢！", {time: 2000, icon: 5, shift: 6});
        return false;
    }
    var reg = new RegExp("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
    if (!reg.test(email)) {
        layer.msg("邮箱格式不正确", {time: 2000, icon: 5, shift: 6});
        return false;
    }
    $.get("/user/reg/sendEmail", {"email": email, "emailStatus": '1001'}, function (data) {
        if (data.status === 1005) {
            //发送成功
            layer.msg(data.message, {time: 2000, icon: 6});
        } else {
            layer.msg(data.message, {time: 2000, icon: 5, shift: 6});
        }
    });
    curCount = count;
    // 设置button效果，开始计时
    document.getElementById("btnSendCode").setAttribute("disabled", "true");//设置按钮为禁用状态
    document.getElementById("btnSendCode").value = curCount + "秒后重获";//更改按钮文字
    InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器timer处理函数，1秒执行一次
}
//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {//超时重新获取验证码
        window.clearInterval(InterValObj);// 停止计时器
        document.getElementById("btnSendCode").removeAttribute("disabled");//移除禁用状态改为可用
        document.getElementById("btnSendCode").value = "重获验证码";
    } else {
        curCount--;
        document.getElementById("btnSendCode").value = curCount + "秒后重获";
    }
}