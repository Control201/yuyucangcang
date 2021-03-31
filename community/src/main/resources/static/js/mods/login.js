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
            //锁定按钮
            lockTime("#dologin_btn",4000);
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
                        // layer.msg(data.message,{time: 1000});
                        //登录成功加载层
                        _success();
                        var current_time = 1;
                        Countdown = setInterval(function () {
                            current_time--;
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
                    $("#L_vercode").val('');
                    message = '操作异常';
                    // window.location.href = "/user/login";
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
//第三方登录返回信息
    $(document).ready(function () {
        var msg = $('#rollback').val();
        if (msg!=null &&msg.length>1){
            layer.msg(msg,{time: 2000, icon: 7, shift: 6});
        }
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
    var img2 = document.getElementById("imageCode2");
    img2.src = img.src;
}

function _success() {
    zeroModal.success({ content: '登录成功',
        width:'250px',
        height:'200px',
        close: false,
        ok: false,
        overlay:'true',
        contentDetail: '<div style="font-size:15px">正在转跳首页中...</div>'});
}


