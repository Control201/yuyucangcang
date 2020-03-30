//时间美化
//直接查询评论时的实现美化展示--html静态页面不能直接使用js函数
$(document).ready(function () {
    var arrayLength = $("#arrayLength").val();
    for (var i = 0; i < arrayLength; i++) {
        var date = $('#date' + i).val();
        var time = new Date(date);
        //因为时区问题减14小时(美国时间西六区)。
        document.getElementById('time' + i).innerHTML = beautify_time(time.getTime() - 14 * 3600 * 1000);
    }
});

//时间转换timestamp是毫秒值 --加10秒防止毫秒小于10000
function beautify_time(timestamp) {
    var mistiming = Math.round((Date.now() - timestamp + 10000) / 1000);
    var arrr = ['年', '个月', '周', '天', '小时', '分钟', '秒'];
    var arrn = [31536000, 2592000, 604800, 86400, 3600, 60, 1];
    for (var i = 0; i < arrn.length; i++) {
        var inm = Math.floor(mistiming / arrn[i]);
        if (inm > 0) {
            if (i === 6) {
                return '刚刚';
            }
            return inm + arrr[i] + '前';
        } else if (inm < 0) {
            //inm为负数，则是未来的时间
            return '来自未来的评论';
        }
    }
    //防止时间异常--返回空字符串代替
    return '';
}

//foot的js代码
$("#friend_link").click(function () {
    layer.alert("请附友链,发送邮箱到:1057559465@qq.com",
        {
            skin: 'layui-layer-lan'
            , closeBtn: 0
            , title: '申请友链'
            , anim: 0 //动画类型
        });
});
layui.use(['element', 'layer'], function () {
    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    var layer = layui.layer;
    var $ = layui.$;
    var util = layui.util;
    $("#box").click(function () {
        layer.alert("有想法,请发送邮件到1057559465@qq.com",
            {
                skin: 'layui-layer-lan'
                , closeBtn: 0
                , title: '联系站长'
                , anim: 1 //动画类型
            })
    });
    $("#office").click(function () {
        layer.alert("办公人员方可进入，您的权限不够哦！",
            {
                skin: 'layui-layer-lan'
                , closeBtn: 0
                , title: '通知'
                , anim: 4 //动画类型
            })
    });
    $("#ad").click(function () {
        layer.msg('老板,要投个币吗？', {
            time: 0 //不自动关闭
            , btn: ['支付宝，微信', '残忍拒绝']
            , yes: function (index) {
                layer.close(index);
                layer.msg('谢谢老板心意，好人一生平安', {
                    icon: 6
                    , time: 0
                    , btn: ['退下吧', '今晚留下加班']
                });
            }
        });
    });
    element.init();//显示二级菜单栏
    //监听导航点击
    element.on('nav(demo)', function (elem) {
        console.log(elem);
        layer.msg(elem.text());
    });
});

//检测字符长度
function getLength(str) {
    ///<summary>获得字符串实际长度，中文和其他2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128)
            realLength += 1;
        else
            realLength += 2;
    }
    return realLength;
};


//手机设备的简单适配(在user登录后的操作界面添加左边树形显示框)
var treeMobile = $('.site-tree-mobile')
    , shadeMobile = $('.site-mobile-shade');

treeMobile.on('click', function () {
    $('body').addClass('site-mobile');
});
shadeMobile.on('click', function () {
    $('body').removeClass('site-mobile');
});


//搜索
$('.fly-search').on('click', function () {
    layer.open({
        type: 1
        , title: false
        , closeBtn: false
        //,shade: [0.1, '#fff']
        , shadeClose: true
        , maxWidth: 10000
        , skin: 'fly-layer-search'
        , content: ['<form action="http://cn.bing.com/search">'
            , '<input autocomplete="off" placeholder="必应搜索内容，回车跳转" type="text" name="q">'
            , '</form>'].join('')
        , success: function (layero) {
            var input = layero.find('input');
            input.focus();
            layero.find('form').submit(function () {
                var val = input.val();
                if (val.replace(/\s/g, '') === '') {
                    return false;
                }
                input.val('' + input.val());
            });
        }
    })
});


// 将表情转为字符--以防数据库utf-8报错
// var str="表情😀123456";
function utf16toEntities(str) {
    var patt = /[\ud800-\udbff][\udc00-\udfff]/g; // 检测utf16字符正则
    str = str.replace(patt, function (char) {
        var H, L, code;
        if (char.length === 2) {
            H = char.charCodeAt(0); // 取出高位
            L = char.charCodeAt(1); // 取出低位
            code = (H - 0xD800) * 0x400 + 0x10000 + L - 0xDC00; // 转换算法
            return "&#" + code + ";";
        } else {
            return char;
        }
    });
    return str;
}

// var str01=utf16toEntities(str);
// console.log(str01);

// 将字符转为表情 ---html会自动转义emoji表情
function uncodeUtf16(str) {
    var reg = /\&#.*?;/g;
    var result = str.replace(reg, function (char) {
        var H, L, code;
        if (char.length === 9) {
            code = parseInt(char.match(/[0-9]+/g));
            H = Math.floor((code - 0x10000) / 0x400) + 0xD800;
            L = (code - 0x10000) % 0x400 + 0xDC00;
            return unescape("%u" + H.toString(16) + "%u" + L.toString(16));
        } else {
            return char;
        }
    });
    return result;
};
// var str02=uncodeUtf16(str01)
// console.log(str02)

//签到获取经验计算
function signInExp(conDay) {
    if (conDay == null) {
        return 5;
    } else if (conDay < 5) {
        return 5;
    } else if (conDay >= 5 && conDay < 15) {
        return 10;
    } else if (conDay >= 15 && conDay < 30) {
        return 15;
    } else if (conDay >= 30) {
        return 20;
    }
}

//计算等级
function getRank(exps) {
    var exp = parseInt(exps);
    if (exp < 10) {
        return "VIP1";
    } else if (exp < 50) {
        return "VIP2";
    } else if (exp < 200) {
        return "VIP3";
    } else if (exp < 500) {
        return "VIP4";
    } else if (exp < 1500) {
        return "VIP5";
    } else if (exp < 4000) {
        return "VIP6";
    } else if (exp < 100000) {
        return "VIP7";
    } else {
        return "QAQ";
    }
}
//等级赋值
$(document).ready( function (){
    var exps = $("#exp").val();
    $("#uRank").text(getRank(exps));
});




