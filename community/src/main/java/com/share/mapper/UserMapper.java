package com.share.mapper;

import com.share.dto.UserDto;
import com.share.entity.User;
import com.share.entity.UserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO:用户持久层
 * @Author Kang
 * @Date 2020-01-14 12:38
 * @Version 1.0
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 用户注册
     * @param user
     * @return
     */
    Integer addUser(User user);
    /**
     * 用户信息查询
     * @param uid
     * @return
     */
    User findUserById(Long uid);
    /**
     * 根据用户名 查询用户密码
     * @param username -- 用户邮箱登录的
     * @return
     */
    User findUserByName(String username);
    /**
     * 更新用户密码
     * @param user
     * @return
     */
    Integer modifyUserPassword(User user);
    /**
     * 更新用户基本信息
     * @param user
     * @return
     */
    Integer modifyUserInfo(User user);
    /**
     * 更新登录时间
     * @param user
     * @return
     */
    Integer updateLastTime(User user);
    /**
     * 添加关系表
     * @param userRelation
     * @return
     */
    Integer addRelation(UserRelation userRelation);
    /**
     * 修改关系表 --目前是删除关注记录
     * @param userRelation
     * @return
     */
    Integer modifyRelation(UserRelation userRelation);
    /**
     * 根据uid和rid查询是否有关系记录
     * @param userRelation
     * @return
     */
    UserRelation findRelation(UserRelation userRelation);
    /**
     * 查询粉丝 或偶像 status区分
     * @param uid
     * @param status
     * @return
     */
    List<UserDto> findRelationList(@Param("uid") Long uid, @Param("status") Integer status, @Param("start") Integer start);
    /**
     * 查询粉丝 或偶像 总数status区分
     * @param uid
     * @param status
     * @return
     */
    Integer findRelationTotal(@Param("uid") Long uid, @Param("status") Integer status);

    /**
     * 通过id增加经验
     * @param uid
     * @param exp
     * @return
     */
    Integer addExpByUid(@Param("uid") Long uid,@Param("exp") Integer exp);
}
