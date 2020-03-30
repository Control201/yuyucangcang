//发送方
var uid = $("#uid").val();
//接受方
var rid = $("#hid").val();

function modifyRelation() {
    if (uid == null) {
        layer.msg("亲，还没登录账号哦！", {time: 1500, icon: 0, shift: 6});
        return;
    }
    //获取元素对象
    var dom = $("#modifyRelation");
    if (dom.attr("relation") === 'false') {
        //添加关注
        $.post("/user/home/modifyRelation", {uid: uid, rid: rid, status: 1}, function (data) {
            if (data.status === 2007) {
                dom.removeClass("layui-btn-danger");
                //修改按钮颜色
                dom.attr("style","background-color:#646973");
                dom.attr("relation", true);
                dom.html("取消关注");
                layer.msg(data.message);
            } else {
                //异常返回
                layer.msg(data.message);
            }
        });
    } else {
        //取消关注
        $.post("/user/home/modifyRelation", {uid: uid, rid: rid, status: 0}, function (data) {
            if (data.status === 2008) {
                dom.addClass("layui-btn-danger");
                dom.removeAttr("style");
                dom.html("<em style='margin:0 4px 0 0px ;'>+</em>关注");
                dom.attr("relation", false);
                layer.msg(data.message);
            } else {
                //异常返回
                layer.msg(data.message);
            }
        });
    }
}

function chat() {
    if (uid == null) {
        layer.msg("亲，还没登录账号哦！", {time: 1500, icon: 0, shift: 6});
        return;
    }
    var nickName = $("#homeNickName").val();
    var indexPrompt = layer.prompt({
        formType: 2,
        btn: ['发送', '取消'],
        value: '请输入内容',
        title: '给' + nickName + '的私信',
        maxmin: true,
        area: 'auto'
    }, function (index) {
        //获取输入框的内容。value返回的是1--按钮值
        var text = $(".layui-layer-input").val();
        var content = text.replace(/\n/g, '').trim();
        if (content === '') {
            layer.msg("内容不能为空哦", {time: 1500, icon: 0, shift: 6});
            return;
        }
        if (content.length > 200) {
            layer.msg("内容字数太多了哦", {time: 1500, icon: 0, shift: 6});
            return;
        }
        var  con = utf16toEntities(content);
        $.post("/user/message/send", {uid: uid, rid: rid, content: con }, function (data) {
            if (data.status === 2005) {
                //关闭发送框
                layer.close(indexPrompt);
                layer.msg(data.message);
            } else {
                //异常返回
                layer.close(indexPrompt);
                layer.msg(data.message);
            }
        });
    });
}

//等级赋值
$(document).ready( function (){
    var exps = $("#hExp").val();
    $("#hRank").text(getRank(exps));
});