layui.use(['form'], function () {
    var form = layui.form
        , layer = layui.layer;
    form.verify({
        verCode: function (value) {
            var message = '';
            var checked = '';
            checked = document.getElementById('rememberMe').checked;
            if (value.length != 4) {
                return '验证码是4个字符啊';
            }
            $.ajax({
                type: "POST",
                url: "/user/login",
                data: {
                    verCode: value,
                    password: $("#L_pass").val().trim(),
                    username: $("#L_email").val().trim(),
                    rememberMe : checked
                },
                async: false, //改为同步请求
                dataType: 'json',
                success: function (data) {
                    //登录成功
                    if (data.status === 1000) {
                        layer.msg(data.message,{time: 1000});
                        var current_time = 2;
                        Countdown = setInterval(function () {
                            current_time--;
                            if (current_time === 1){
                                layer.load();
                                setTimeout(function(){
                                    layer.closeAll('loading');
                                }, 1000);
                            }
                            if (current_time < 1) {
                                clearInterval(Countdown);
                                window.location.href = "/"; //#设定跳转的链接地址
                            }
                        }, 1000);
                    } else {
                        $("#L_vercode").val('');
                        // layer.msg(data.result,{time: 2000, icon: 5, shift: 6});
                        message = data.message;
                        refresh();
                    }
                },
                error: function (data) {
                    message = data.message;
                    window.location.href = "/user/login";
                    refresh();
                }
            });
            //需要注意 需要将返回信息写在ajax方法外
            if (message !== '')
                return message;
        },
        pass: [
            /^[\S]{8,18}$/
            , '密码必须8到18位，且不能出现空格'
        ]
    });
});

//回车键确定登录
$(document).keydown(function (e) {
    if (e.keyCode === 13) {
        $("#dologin_btn").trigger("click");
    }
});
//点击图片更换验证码    拼接时间戳 --一定要关闭thymeleaf缓存。。
//刷新验证码
function refresh() {
    var img = document.getElementById("imageCode");
    img.src = "/user/captcha?" + new Date().getTime();
}

