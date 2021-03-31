//设置信息页面

layui.use(['form', 'layer', 'laydate', 'upload'], function () {
    var form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer,
        $ = layui.$,
        upload = layui.upload;
    //获取header.html上hidden的隐藏域
    var uid = $("#uid").val();
    form.verify({
        // email_code为verify的值，验证顺序和标签排放顺序有关
        repass: function (value) {
            var L_pass = $("#L_pass").val().trim();
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

            //按钮加载中
            lock('#setPassword','提交中...','确定修改');
            $.ajax({
                type: "POST",
                url: "/user/change",
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
                            layer.msg(data.message + ',正在转跳首页中。。', {
                                time: 2000,
                                icon: 16, shade: 0.01
                            });
                        }, 1000);
                        var current_time = 3;
                        Countdown = setInterval(function () {
                            current_time--;
                            if (current_time < 1) {
                                clearInterval(Countdown);
                                window.location.href = "/user/logout"; //#设定跳转的链接地址
                            }
                        }, 1000);
                    } else {
                        message = data.message;
                    }
                },
                error: function (data) {
                    message = data.message;
                    window.location.href = "/user/set/" + uid;
                }
            });
            //需要注意 需要将返回信息写在ajax方法外
            if (message !== '')
                return message;
        },
        email_code: [
            /^[A-Za-z0-9]{4}$/
            , '验证码是4个字符啊!'
        ],
        pass: [
            /^[\S]{8,18}$/
            , '密码必须8到18位，且不能出现空格'
        ],
        mobile: function (value) { //value：表单的值、item：表单的DOM对象  /^[1][3,4,5,7,8][0-9]{9}$/
            if (value != '') {
                if (!new RegExp("^1[34578]\\d{9}$").test(value)) {
                    return '请输入正确的手机号哦！';
                }
            }
        },
        nickName: function (value) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '昵称不能有特殊字符';
            }
            //空格判断
            if (value.indexOf(" ") != -1) {
                return '昵称不能有特殊字符';
            }
            var length = getLength(value);
            if (length > 18) {
                return '昵称太长了哦';
            }
        },
        location: function (value) {
            if (!new RegExp("^[a-zA-Z_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '城市名是字母或汉字呢';
            }
            //空格判断
            if (value.indexOf(" ") != -1) {
                return '城市名是字母或汉字呢';
            }
            var length = getLength(value);
            if (length > 18) {
                return '城市名太长了哦';
            }
        },
        signature: function (value) {
            var length = getLength(value);
            if (length > 50) {
                return '用简短的点签名介绍自己吧';
            }
        }
    });
    //加载日历
    laydate.render({
        elem: '#test29',
        theme: '#409eff',
        min: '1970-1-1',
        max: -1
    });

    //更新个人信息
    form.on('submit(setInfo)', function (data) {
        //按钮加载中
        lock('#setUserInfo','提交中...','提交');
        //对个性签名进行处理
        var signature = $("#L_signature").val().trim();
        data.field.signature = utf16toEntities(signature.replace(/[\r\n]/g, ''));
        $.ajax({
            url: "/user/set/" + uid,
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: "json",
            success: function (data) {

                if (data.status === 2001) {
                    //更新名字
                    $("#sNickname").text($("#L_nickname").val());
                    //修改成功成功
                    layer.msg(data.message, {time: 2000, icon: 6});
                } else {

                    layer.msg(data.message, {time: 2000, icon: 5, shift: 6});
                }
            },
            error: function (data) {

                layer.msg(data.message, {time: 2000, icon: 5, shift: 6});
            }
        });

    });



    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        , url: '/blog/upload/set' //改成您自己的上传接口
        , size: 2 * 1024 //限制文件大小，单位 KB
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败，重新尝试');
            } else {
                //上传成功--修改旧头像显示
                $('#userAvatar').attr('src', res.data.src);
                return layer.msg(res.msg, {time: 2000, icon: 6});
            }
        }
        , error: function () {
            return layer.msg('上传失败，重新尝试');
        }
    });


});
//邮箱验证
var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
//发送邮箱验证码
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