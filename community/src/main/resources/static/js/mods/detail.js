//评论点赞--index为元素下标
function likeMark(index) {
    var commentatorId = $("#uid").val();
    if (commentatorId == null) {
        layer.msg("亲，登录后才能点赞哦！", {time: 2000, icon: 0, shift: 6});
        return;
    }
    //获取元素对象
    var dom = $('#love' + index);
    //评论id
    var commentId = $("#commentId" + index).val();
    //点赞者id
    if (dom.attr("data-like") === 'false') {
        //点赞
        $.post("/user/comment/likeMark", {"mark": 1, "id": commentId, "uid": commentatorId}, function (data) {
            if (data.status == 3005) {
                layer.msg(data.message);
                $("#style" + index).addClass("zanok");
                dom.attr("data-like", true);
                $("#love_Num" + index).text(parseInt($("#love_Num" + index).text()) + 1);
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    } else {
        //取消点赞
        $.post("/user/comment/likeMark", {"mark": 0, "id": commentId, "uid": commentatorId}, function (data) {
            if (data.status == 3006) {
                layer.msg(data.message);
                $("#style" + index).removeClass("zanok");
                dom.attr("data-like", false);
                $("#love_Num" + index).text(parseInt($("#love_Num" + index).text()) - 1);
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    }
};

//删除帖子
function delBlog(aid){
    var authority = $("#authority").val();
    var title = "提示";
    if (authority === "2"){
        title = "超级管理员:";
    }
    layer.confirm('您确定要永久删除帖子？', {
        title: title,
        btn: ['确定', '取消'] //按钮
    }, function () {
        //确定
        $.post("/blog/del", {"aid": aid}, function (data) {
            if (data.status === 3010) {
                layer.msg(data.message,{time: 1000});
                var current_time = 2;
                Countdown = setInterval(function () {
                    current_time--;
                    if (current_time === 1){
                        layer.load(1);
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
                //异常返回
                layer.msg(data.message);
            }
        })
    }, function () {
        //取消
    });
}

//收藏文章
function collect() {
    var uid = $("#uid").val();
    var aid = $("#blogId").val();
    if (uid == null) {
        layer.msg("亲，登录后才能收藏哦！", {time: 2000, icon: 0, shift: 6});
        return;
    }
    //获取按钮元素对象
    var dom = $("#collect");
    if (dom.attr("data-collect") === 'false') {
        //收藏
        $.post("/blog/collect", {"mark": 1, "aid": aid, "uid": uid}, function (data) {
            if (data.status === 3012) {
                layer.msg(data.message);
                dom.addClass("layui-btn-primary");
                dom.removeClass("layui-btn-danger");
                dom.attr("data-collect", true);
                dom.text("取消收藏");
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    } else {
        //取消收藏
        $.post("/blog/collect", {"mark": 0, "aid": aid, "uid": uid}, function (data) {
            if (data.status === 3013) {
                layer.msg(data.message);
                dom.removeClass("layui-btn-primary");
                dom.addClass("layui-btn-danger");
                dom.attr("data-collect", false);
                dom.text("收藏");
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    }
}
//置顶帖子
function topBlog() {
    var authority = $("#authority").val();
    var aid = $("#blogId").val();
    if (authority !== "2") {
        layer.msg("亲，权限不够哦", {time: 2000, icon: 0, shift: 6});
        return;
    }
    //获取按钮元素对象
    var dom = $("#top");
    if (dom.attr("data-top") === 'false') {
        //置顶
        $.post("/admin/setTop", {"top": 1, "aid": aid}, function (data) {
            if (data.status === 6001) {
                layer.msg(data.message);
                dom.attr("data-top", true);
                dom.text("取消置顶");
                $("#topBlog").html('<span class="layui-badge layui-bg-black" >置顶</span>');
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    } else {
        //取消置顶
        $.post("/admin/setTop", {"top": 0, "aid": aid}, function (data) {
            if (data.status === 6001) {
                layer.msg(data.message);
                dom.attr("data-top", false);
                dom.text("置顶");
                $("#topBlog").empty();
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    }
}
//加精帖子
function boutiqueBlog() {
    var authority = $("#authority").val();
    var aid = $("#blogId").val();
    if (authority !== "2") {
        layer.msg("亲，权限不够哦", {time: 2000, icon: 0, shift: 6});
        return;
    }
    //获取按钮元素对象
    var dom = $("#boutique");
    if (dom.attr("data-boutique") === 'false') {
//加精帖子
        $.post("/admin/setBoutique", {"boutique": 1, "aid": aid}, function (data) {
            if (data.status === 6002) {
                layer.msg(data.message);
                dom.attr("data-boutique", true);
                dom.text("取消加精");
                $("#boutiqueBlog").html('<span class="layui-badge layui-bg-red">精帖</span>');
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    } else {
        //取消加精帖子
        $.post("/admin/setBoutique", {"boutique": 0, "aid": aid}, function (data) {
            if (data.status === 6002) {
                layer.msg(data.message);
                dom.attr("data-boutique", false);
                dom.text("加精");
                $("#boutiqueBlog").empty();
            } else {
                //异常返回
                layer.msg(data.message);
            }
        })
    }
}
//等级赋值
$(document).ready( function (){
    var exps = $("#bExp").val();
    $("#bRank").text(getRank(exps));
});

