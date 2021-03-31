package com.share;


import com.share.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * id为key
     * 签到方法
     */
    @Test
    public void test(){
        Map<String, Object> map = signIn("3333");
        System.out.println(map);
    }

    public Map<String, Object> signIn(String uid) {
        Map<String, Object> map = new HashMap<>();
        //获取当天时间记录
        String yyyyMMdd = DateUtils.date2String(new Date(), "yyyyMMdd");
        String id = "signIn:" + yyyyMMdd;
        try {
            Integer checkSign = checkSign(id, uid);
            // 当天已经签到了
            if (checkSign != -1) {
                map.put("message", "今天已签到");
                map.put("code", 4000);
                map.put("checkSign", checkSign);
            } else {
                //插入输入签到数据，并设置时间
                redisTemplate.opsForList().rightPush(id, uid);
                redisTemplate.expire(id,DateUtils.countDown(),TimeUnit.SECONDS);
                //连续签到
                Integer conDay = continuousSignIn(uid);
                Integer rank = checkSign(id, uid);
                map.put("message", "签到成功");
                map.put("code", 4001);
                map.put("conDay", conDay);
                //第几个签到。
                map.put("checkSign", rank);
            }
        } catch (Exception e) {
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
        } catch (Exception e) {
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
     * @param uid
     * @return
     */
    private Integer checkSign(String key, String uid) {
        //查询所有进行遍历
        List<String> stringList = redisTemplate.opsForList().range(key, 0, -1);
        if (stringList == null) {
            return 0;
        }
        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).equals(uid)) {
//                查找到就返回第几个否则返回-1
                return i + 1;
            }
        }
        return 0;
    }

}
