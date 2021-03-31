//主页js

layui.use(['layer', 'carousel', 'jquery'], function () {
    var carousel = layui.carousel;
    var $ = layui.$;
    var layer = layui.layer;
    layer.tips('好想看演唱会呀！', '#jay', {
        tips: [1, '#c2c2c2']
    });
    $("#jay").click(function () {
        layer.tips('要一起看嘛？(*^▽^*)', '#jay', {
            tips: [2, '#C88FD6']
        });
    });
    $("#game").click(function () {
        layer.tips('怀恋超级玛丽奥呀', '#game', {
            tips: [1, '#FFB800']
        });
    });
    $("#allExps").click(function () {
        var exps = $("#exp").val();
        layer.tips('当前经验值：' + exps, '#allExps', {
            tips: [1, '#2F4056']

        });
    });

    var W = $("#test10").width();
    var aHeight = (W / 1.5).toString() + "px";
    if (W / 1.5 > 420) {
        //最大高度420
        aHeight = "420px";
    }
    //图片轮播
    carousel.render({
        elem: '#test10'
        , width: '100%'
        , height: aHeight
        , interval: 3000
    });
});

//签到模块
var elemSigninHelp = $('#LAY_signinHelp')
    , elemSigninDays = $('.fly-signin-days')
    , flySignInMain = $('.fly-signin-main');

//今日签到
$('#LAY_signin').click(function () {
    var uid = $("#uid").val();
    if (uid == null) {
        loginAlter();
        layer.msg("请先登录", {
            shift: 6,
            time: 1500
        });
        return;
    }
    $.post('/user/signIn', {uid: uid.toString()}, function (data) {
        console.info(data);
        //签到成功
        if (data.code === 4001) {
            //设置签到按钮
            flySignInMain.html(" <button class=\"layui-btn layui-btn-disabled\">今日已签到</button>\n" +
                " <div style=\"padding: 4px 0;\">今天第<cite>" + data.checkSign + "</cite>个签到,获得了<cite>" + signInExp(data.conDay - 1) + "</cite>经验</div>");
            layer.msg(data.message);
            //更新经验 和等级 连续签到
            $("#exp").val(parseInt($("#exp").val()) + signInExp(data.conDay - 1));
            var exps = $("#exp").val();
            $("#uRank").text(getRank(exps));

            elemSigninDays.html("已连续签到<cite id>" + data.conDay + "</cite>天");
        } else if (data.code === 4000) {
            layer.msg(data.message);
        } else {
            layer.msg(data.message);
        }
    })
});
//签到说明
elemSigninHelp.on('click', function () {
    layer.open({
        type: 1
        , title: '签到说明'
        , area: '300px'
        , shade: 0.8
        , shadeClose: true
        , content: ['<div class="layui-text" style="padding: 20px;">'
            , '<blockquote class="layui-elem-quote">“签到”可获得经验，提升账号等级，规则如下</blockquote>'
            , '<table class="layui-table">'
            , '<thead>'
            , '<tr><th>连续签到天数</th><th>每天签到可获经验</th></tr>'
            , '</thead>'
            , '<tbody>'
            , '<tr><td>＜5</td><td>5</td></tr>'
            , '<tr><td>≥5</td><td>10</td></tr>'
            , '<tr><td>≥15</td><td>15</td></tr>'
            , '<tr><td>≥30</td><td>20</td></tr>'
            , '</tbody>'
            , '</table>'
            , '<ul>'
            , '<li>中间若有间隔，则连续天数重新计算</li>'
            , '<li style="color: #FF5722;">不可利用程序自动签到，否则经验清零</li>'
            , '</ul>'
            , '</div>'].join('')
    });
});

$("#rank").on('click', function () {
    layer.open({
        type: 1
        , title: '等级说明'
        , area: '300px'
        , shade: 0.8
        , shadeClose: true
        , content: ['<div class="layui-text" style="padding: 20px;">'
            , '<blockquote class="layui-elem-quote">可通过“签到”,“发帖”,“评论”获得经验，提升账号等级，规则如下</blockquote>'
            , '<table class="layui-table">'
            , '<thead>'
            , '<tr><th>等级</th><th>所需经验</th></tr>'
            , '</thead>'
            , '<tbody>'
            , '<tr><td style="color: #A1A1A1;">Lv.1</td><td>＜25</td></tr>'
            , '<tr><td style="color: #C278E7;">Lv.2</td><td>≥25</td></tr>'
            , '<tr><td style="color: #418AC3;">Lv.3</td><td>≥100</td></tr>'
            , '<tr><td style="color: #10B4D2;">Lv.4</td><td>≥400</td></tr>'
            , '<tr><td style="color: #42CEA3;">Lv.5</td><td>≥1000</td></tr>'
            , '<tr><td style="color: #FC7123;">Lv.6</td><td>≥2000</td></tr>'
            , '<tr><td style="color: #F34C54;">Lv.7</td><td>≥4000</td></tr>'
            , '</tbody>'
            , '</table>'
            , '<ul>'
            , '<li style="color:#E8CA2D">等级越高有机会获取顶级徽章哦</li>'
            , '<li style="color:red">Lv.3以上用户可获得专属印记~</li>'
            , '</ul>'
            , '</div>'].join('')
    });
});

//印记赋值
$(document).ready(function () {
    var topSize = $("#topSize").val();
    var indexSize = $("#indexSize").val();
    //置顶
    for (let i = 0; i < topSize; i++) {
        var exps = $("#topExp" + i).val();
        stamp(exps, '#topAddStamp' + i);
    }
    //首页
    for (let i = 0; i < indexSize; i++) {
        var exps = $("#indexExp" + i).val();
        stamp(exps, '#indexAddStamp' + i);
    }
});


