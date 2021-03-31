package com.share.service.impl;

import com.share.entity.User;
import com.share.mapper.QQMapper;
import com.share.service.QQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO:
 * @Author YuYu
 * @Date 2020-04-01 16:53
 * @Version 2.0
 */
@Service
public class QQServiceImpl  implements QQService {
    @Autowired
    private QQMapper qqMapper;

    @Override
    public Long findUidByOpenId(String openId) {
        return qqMapper.findUidByOpenId(openId);
    }

    @Override
    public Integer addQQInfo(Long uid, String openId) {
        Date date = new Date();
        return qqMapper.addQQInfo(uid,openId,date);
    }

    @Override
    public Integer addUser(User user) {
        return qqMapper.addUser(user);
    }

    @Override
    public User findUserByUid(Long uid) {
        return qqMapper.findUserByUid(uid);
    }

    @Override
    public String findOpenIdByUid(Long uid) {
        return qqMapper.findOpenIdByUid(uid);
    }
}
