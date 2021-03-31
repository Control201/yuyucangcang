//弹窗式登录
layui.use(['form'], function () {
    var form = layui.form
        , layer = layui.layer;
    form.verify({
        verCode: function (value) {
            var message = '';
            //默认记住我
            var checked = true;
            if (value.length != 4) {
                return '验证码是4个字符啊';
            }
            //锁定按钮
            lockTime("#dologin_btn2",4000);
            $.ajax({
                type: "POST",
                url: "/user/login",
                data: {
                    verCode: value,
                    password: $("#L_pass2").val().trim(),
                    username: $("#L_email2").val().trim(),
                    rememberMe : checked
                },
                async: false, //改为同步请求
                dataType: 'json',
                success: function (data) {
                    //登录成功
                    if (data.status === 1000) {
                        layer.closeAll('page'); //关闭所有页面层
                        layer.msg('登录成功',{time: 1000,shade: 0.3});
                        //刷新页面
                        var current_time = 1;
                        Countdown = setInterval(function () {
                            current_time--;
                            if (current_time < 1) {
                                clearInterval(Countdown);
                                window.location.reload();//刷新当前页
                            }
                        }, 1000);
                    } else {
                        $("#L_vercode2").val('');
                        // layer.msg(data.result,{time: 2000, icon: 5, shift: 6});
                        message = data.message;
                        refresh();
                    }
                },
                error: function (data) {
                    $("#L_vercode2").val('');
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
    //登录弹框
    window.loginAlter = function()  {
        layer.open({
            type: 1,
            title: '<div style="font-size: 18px">账号登录</div>',
            fixed: true,
            move: false,//禁止移动
            skin: 'layui-layer-rim', //加上边框
            anim: 1, //动画样式
            area: ['320px', '400px'], //宽高
            content: '<div class="layui-container " style="width: 320px;height: 300px;margin-top: 0px;">\n' +
                '    <div class="fly-panel fly-panel-user" pad20 style="padding: 10px; width: 300px">\n' +
                '        <div class="layui-tab layui-tab-brief" lay-filter="user" >\n' +
                '            <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 0px 0;">\n' +
                '                <div class="layui-tab-item layui-show" >\n' +
                '                    <div class="layui-form layui-form-pane">\n' +
                '                        <form method="post"  th:action="@{/user/login}" >\n' +
                '                            <div class="layui-form-item" style="width: 310px">\n' +
                '                                <label for="L_email" class="layui-form-label">邮箱</label>\n' +
                '                                <div class="layui-input-inline ">\n' +
                '                                    <input type="text" id="L_email2" name="username" required lay-verify="required|email"\n' +
                '                                            placeholder="请输入您的邮箱"  class="layui-input ">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="layui-form-item"  style="width: 310px">\n' +
                '                                <label for="L_pass" class="layui-form-label">密码</label>\n' +
                '                                <div class="layui-input-inline">\n' +
                '                                    <input type="password" id="L_pass2" name="password" required\n' +
                '                                           lay-verify="required|pass" autocomplete="off" placeholder="请输入您的密码"\n' +
                '                                           class="layui-input">\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="layui-form-item" style="width: 310px">\n' +
                '                                <label for="L_vercode" class="layui-form-label">验证码</label>\n' +
                '                                <div class="layui-input-inline" style="padding-bottom: 0.3cm">\n' +
                '                                    <input type="text" id="L_vercode2" name="verCode" required\n' +
                '                                           lay-verify="required|verCode" placeholder="请输入验证码" autocomplete="off"\n' +
                '                                           class="layui-input">\n' +
                '                                </div>\n' +
                '                                <div class="layui-form-mid" style="padding: 0!important;">\n' +
                '                                    <img src="/user/captcha" id="imageCode2" width="130px" height="48px" alt="验证码"\n' +
                '                                         onclick="refresh2()" title="点击刷新一下"\n' +
                '                                         style="position: relative; margin-top: -10px;cursor: pointer;"/>\n' +
                '                                </div>\n' +
                '                            </div>\n' +
                '                            <div class="layui-form-item" style="width: 310px">\n' +
                '                                <button class="layui-btn layui-btn-danger" id="dologin_btn2" lay-filter="*" lay-submit\n' +
                '                                        type="button">\n' +
                '                                    立即登录\n' +
                '                                </button>\n' +
                '                                <span style="padding-left:20px;">\n' +
                '                                <a href="/user/forget">忘记密码？</a>\n' +
                '                                </span>\n' +
                '                            </div>\n' +
                '                            <div class="layui-form-item " style="margin-bottom: 0px;width: 310px">\n' +
                '                                <span>使用社交账号登入</span>\n' +
                '                                <a href="/user/qq/login" onclick="layer.msg(\'正在通过QQ登入...\', {icon:16, shade: 0.1, time:1000})" title="QQ登入"\n' +
                '                                   class="iconfont icon-qq" style="color: #7CA9C9"></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: #00a1d6;" href="/user/reg">没有账号,立即注册></a>\n' +
                '                            </div>\n' +
                '                        </form>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>\n' +
                '    </div>\n' +
                '</div>'
        });
    }
});
//回车键确定登录
$(document).keydown(function (e) {
    if (e.keyCode === 13) {
        layer.closeAll(); //关闭所有层
    }
});

//点击图片更换验证码    拼接时间戳 --一定要关闭thymeleaf缓存。。
//刷新验证码
function refresh2() {
    var img2 = document.getElementById("imageCode2");
    img2.src = "/user/captcha?" + new Date().getTime();
}



