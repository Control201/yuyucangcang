package com.share.service;

import com.share.entity.User;

import java.util.Date;

/**
 * @Description TODO:
 * @Author YuYu
 * @Date 2020-04-01 16:53
 * @Version 2.0
 */
public interface QQService {

    /**
     * 根据openId查询uid
     * @param OpenId
     * @return
     */
    Long findUidByOpenId(String OpenId);

    /**
     * 创建qq用户表并绑定user
     * @param uid
     * @param OpenId
     * @return
     */
    Integer addQQInfo(Long uid, String OpenId);

    /**
     * 创建用户表用于关联qq
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 根据uid查询user部分信息用于登陆
     * @param uid
     * @return
     */
    User findUserByUid(Long uid);

    /**
     * 根据uid查询OpenId
     * @param uid
     * @return
     */
    String findOpenIdByUid(Long uid);
}
