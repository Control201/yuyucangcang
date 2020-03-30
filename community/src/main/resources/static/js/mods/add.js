layui.use(['form', 'jquery', 'layedit', 'upload'], function () {
    var $ = layui.$;
    var form = layui.form;
    var layedit = layui.layedit;
    var upload = layui.upload;
    layedit.set({
        uploadImage: {
            url: '/blog/upload' //接口url
            , type: 'post' //默认post
            , done: function (data) {
                console.log(data);
            }
        }
    });
    var contentIndex = layedit.build('L_content', {
        height: 300,  //设置编辑器高度
        tool: [
            'strong' //加粗
            , 'italic' //斜体
            , 'underline' //下划线
            , 'del' //删除线
            , '|' //分割线
            , 'left' //左对齐
            , 'center' //居中对齐
            , 'right' //右对齐
            , 'link' //超链接
            , 'unlink' //清除链接
            , 'image' //插入图片
        ]
    }); //建立编辑器
    form.verify({
        title: function (value) {
            var length = getLength(value);
            if (length > 50) {
                return '标题太长了哦';
            } else if (length < 2) {
                return '标题再长点呗';
            }
        },
        content: function (value) {
            layedit.sync(contentIndex); //数据同步到表单中
            var imgReg = /<img.*?(?:>|\/>)/gi;//匹配<img>标签
            var content = layedit.getContent(contentIndex);//文章的html形式
            var text = layedit.getText(contentIndex).trim();//文章的纯文本
            var match = content.match(imgReg);
            if (text === '' && match == null) {
                return '内容不能为空哦';
            }
            var length = getLength(text);
            if (length > 1000) {
                return '内容不能超过500个字符哦';
            }
            if (match != null && match.length > 3) {
                return '最多只能上传三张照片呢';
            }
        }
    });
    var uid = $("#uid").val();
    form.on('submit(publishArticle)', function (data) {
        //加上对emoji进行处理 utf16toEntities();
        data.field.content = utf16toEntities(layedit.getContent(contentIndex).replace(/<br>/ig, ''));//获取编译器内容，并赋值给content
        data.field.title = utf16toEntities($('#L_title').val().replace(/\s+/g, ""));
        data.field.uid = uid;
        $.ajax({
            url: "/blog/add",
            type: "post",
            async: false,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data.field),
            dataType: "json",
            success: function (data) {
                //发布成功
                if (data.status === 3001) {
                    layer.msg(data.message, {time: 1000, icon: 6});
                    setTimeout(function () {
                        layer.msg('<div style="color:#00D5FF;">分享是阳光，社区因你更温暖</div>', {
                            time: 3000,
                            icon: 16,
                            shade: 0.1
                        });
                    }, 1000);
                    var current_time = 3;
                    Countdown = setInterval(function () {
                        current_time--;
                        if (current_time < 1) {
                            clearInterval(Countdown);
                            window.location.href = "/"; //#设定跳转的链接地址
                        }
                    }, 1000);
                } else {
                    layer.msg(data.message, {time: 2000, icon: 5, shift: 6});
                }
            },
            error: function (data) {
                layer.msg(data.message, {time: 2000, icon: 5, shift: 6});
            }
        });
    });

});