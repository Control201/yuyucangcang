package com.share.controller.user;

import com.share.constant.SignIn;
import com.share.dto.UserDto;
import com.share.service.impl.UserServiceImpl;
import com.share.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO:签到与等级相关
 * @Author YuYu
 * @Date 2020-03-02 19:58
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class SignController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * id为key
     * 签到方法
     */
    @PostMapping("/signIn")
    @ResponseBody
    public Map<String, Object> signIn(String uid, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(16);
        //获取当天时间记录
        String yyyymmdd = DateUtils.date2String(new Date(), "yyyyMMdd");
        String id = "signIn:" + yyyymmdd;
        try {
            Integer checkSign = checkSign(id, uid);
            // 当天已经签到了
            if (checkSign != -1) {
                map.put("message", "今天已签到");
                map.put("code", 4000);
                //第几个签到。
                map.put("checkSign", checkSign);
            } else {
                //插入输入签到数据，并设置时间
                redisTemplate.opsForList().rightPush(id, uid);
                redisTemplate.expire(id, DateUtils.countDown(), TimeUnit.SECONDS);
                //连续签到
                Integer conDay = continuousSignIn(uid);
                Integer rank = checkSign(id, uid);
                //增加经验
                Integer exp = addExp(conDay);
                try {
                    HttpSession session = request.getSession();
                    UserDto loginUser = (UserDto) session.getAttribute("loginUser");
                    userServiceImpl.addExpByUid(Long.valueOf(uid),exp);
                    //session中得数据得同步：比如增加经验值
                    loginUser.setExp(loginUser.getExp()+exp);
                    session.setAttribute("loginUser", loginUser);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                map.put("message", "签到成功");
                map.put("code", 4001);
                map.put("conDay", conDay);
                //第几个签到。
                map.put("checkSign", rank);
            }
        } catch (ParseException e) {
            map.put("message", "签到异常");
            map.put("code", 4002);
            return map;
        }
        return map;
    }

    /**
     * 连续签到
     */
    private Integer continuousSignIn(String uid) {
        String id = "conSign:" + uid;
        String continuousDay = redisTemplate.opsForValue().get(id);
        //默认一天
        int day = 1;
        long time = 0;
        try {
            time = DateUtils.countDown() + 24 * 60 * 60;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (continuousDay != null) {
            day = Integer.parseInt(continuousDay) + 1;
            redisTemplate.opsForValue().set(id, day + "", time, TimeUnit.SECONDS);
            return day;
        } else {
            redisTemplate.opsForValue().set(id, day + "", time, TimeUnit.SECONDS);
            return day;
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
//                查找到就返回第几个否则返回-1
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * 增加经验
     *
     * @param continuousDay
     * @return
     */
    private Integer addExp(Integer continuousDay) {
        int conDay = continuousDay - 1;
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
}
