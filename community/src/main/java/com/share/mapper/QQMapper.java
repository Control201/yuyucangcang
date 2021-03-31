package com.share.mapper;

import com.share.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO:
 * @Author YuYu
 * @Date 2020-04-01 16:59
 * @Version 2.0
 */
@Mapper
@Repository
public interface QQMapper {
    /**
     * 根据openId查询uid
     *
     * @param openId
     * @return
     */
    Long findUidByOpenId(String openId);

    /**
     * 创建qq用户表
     *
     * @param uid
     * @param openId
     * @return
     */
    Integer addQQInfo(@Param("uid") Long uid, @Param("openId") String openId, @Param("date") Date date);

    /**
     * 创建用户表用于关联qq
     *
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 根据uid查询user部分信息用于登陆
     *
     * @param uid
     * @return
     */
    User findUserByUid(Long uid);

    /**
     * 根据uid查询OpenId
     *
     * @param uid
     * @return
     */
    String findOpenIdByUid(Long uid);

}
