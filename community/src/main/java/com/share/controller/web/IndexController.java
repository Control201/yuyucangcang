package com.share.controller.web;

import com.share.constant.SignIn;
import com.share.dto.ArticleDto;
import com.share.dto.UserDto;
import com.share.dto.UserInfoDto;
import com.share.entity.Blog;
import com.share.entity.Page;
import com.share.service.impl.BlogServiceImpl;
import com.share.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO:社区主页---所有人访问
 * @Author Kang
 * @Date 2020-02-02 14:36
 * @Version 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 初始化社区主页
     *
     * @return
     */
    @GetMapping(value = {"/", "/index", "/index.html"})
    public ModelAndView index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        ModelAndView modelAndView = new ModelAndView();
//        判断是否签到
        if (loginUser != null) {
            Map<String, Object> map = checkSign(loginUser.getUid());
            modelAndView.addObject("signIn", map);
        }
        //获取当前时间
        String time = DateUtils.date2String(new Date(), "MM-dd");
        //设置查询方式--查询置顶文章 1:为置顶 4：为文章条数
        //设置查询方式--查询最新文章（普通文章）
        Page topPage = new Page();
        //查询方式
        topPage.setTopStatus(1);
        //查询条数
        topPage.setRows(4);
        List<ArticleDto> topArticleList = blogServiceImpl.homePageBlog(topPage);
        //设置查询方式--查询最新文章（普通文章）
        Page newPage = new Page();
        newPage.setTopStatus(0);
        newPage.setRows(10);
        List<ArticleDto> articleList = blogServiceImpl.homePageBlog(newPage);
        //热点查询
        List<ArticleDto> hotSpotArticleList = blogServiceImpl.hotSpot();
        //设置页面
        modelAndView.setViewName("index");
        //放置参数
        modelAndView.addObject("hotSpotArticleList", hotSpotArticleList);
        modelAndView.addObject("articleList", articleList);
        modelAndView.addObject("topArticleList", topArticleList);
        modelAndView.addObject("time", time);
        return modelAndView;
    }

    /**
     * 查看签到情况
     * @param uid
     * @return
     */
    private Map<String, Object> checkSign(Long uid) {
        HashMap<String, Object> map = new HashMap<>(16);
        String cid = "conSign:" + uid;
        //查询连续时间
        String continuousDay = redisTemplate.opsForValue().get(cid);
        if (continuousDay == null) {
            //如果为null则放入一个0；
            continuousDay = "0";
        }
        map.put("continuousDay", continuousDay);
        //获取当天时间记录
        String yyyymmdd = DateUtils.date2String(new Date(), "yyyyMMdd");
        String sid = "signIn:" + yyyymmdd;
        Integer checkSign = checkSign(sid, uid.toString());
        //checkSign为-1则未签到，>-1为连续签到次数
        map.put("checkSign", checkSign);
        //连续签到啥时间
        int conDay = Integer.parseInt(continuousDay);
        if (checkSign != -1) {
            //   已签到 的连续时间多一天但是经验是已经获得的
            map.put("exp", getExp(conDay - 1));
        } else {
            //未签到
            map.put("exp", getExp(conDay));
        }
        return map;
    }

    /**
     * 计算可获取经验
     *
     * @param conDay
     * @return
     */
    private Integer getExp(Integer conDay) {
        if (conDay < SignIn.FIRST_STAGE) {
            return 5;
        } else if (conDay < SignIn.SECOND_STAGE) {
            return 10;
        } else if (conDay < SignIn.THIRD_STAGE) {
            return 15;
        } else {
            return 20;
        }
    }

    /**
     * 检测今天是否签到
     *
     * @param uid
     * @return
     */
    private Integer checkSign(String key, String uid) {
        //查询所有进行遍历
        List<String> stringList = redisTemplate.opsForList().range(key, 0, -1);
        if (stringList == null) {
            return -1;
        }
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).equals(uid)) {
//                查找到就返回第几个签到的，否则返回-1（未签到）
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * 小游戏
     * @return
     */
    @GetMapping("/game")
    public String game(){
        return "add/game";
    }
}
