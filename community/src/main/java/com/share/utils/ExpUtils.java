package com.share.utils;

import com.share.constant.Exp;
import com.share.dto.UserDto;
import com.share.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description TODO:增加经验工具类
 * @Author YuYu
 * @Date 2020-03-31 10:53
 * @Version 2.0
 */
@Component
public class ExpUtils {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserServiceImpl userServiceImpl;


    /**
     * 增加经验
     * @param id
     * @param addExps
     * @return
     */
    public Integer addExp(Long id ,Integer addExps) {
        String yyyymmdd = DateUtils.date2String(new Date(), "yyyyMMdd");
//        设置当天经验上限的key
        String key = "ExpCeil:" + yyyymmdd;
        String uid = String.valueOf(id);
        //        距离上限差值
        Integer ExpGap = 0;
//        当前经验
        Integer exp = null;
//        查看该用户当天获取经验值（除签到外）
        Object hashExists = redisUtil.hget(key, uid);
//        存在
        if (hashExists != null) {
            exp = (Integer) hashExists;
            ExpGap = Exp.EXP_CEIL - exp;
            if (addExps < ExpGap) {
//                正常增加
               redisUtil.hashIncrBy(key, uid, addExps);
                return addExps;
            } else if (ExpGap > 0 && ExpGap <= addExps) {
//              上限差值增加
               redisUtil.hashIncrBy(key, uid, ExpGap);
                return ExpGap;
            } else {
                return 0;
            }
        } else {
//        不存在,则创建并设置有效期会覆盖
            redisUtil.hset(key, uid, addExps, DateUtils.countDown());
            return addExps;
        }
    }

    /**
     * 同步经验--数据和页面进行同步
     * @param request
     * @param addExp
     */
    public void  dataSync(HttpServletRequest request,Integer addExp){
//        达到上限或异常则不同步
        if (addExp == null||addExp ==0){
            return;
        }
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Long uid = loginUser.getUid();
        try {
            userServiceImpl.addExpByUid(Long.valueOf(uid),addExp);
            //session中得数据得同步：比如增加经验值
            loginUser.setExp(loginUser.getExp()+addExp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("loginUser", loginUser);
    }


}
