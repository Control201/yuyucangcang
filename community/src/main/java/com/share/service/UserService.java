package com.share.service;

import com.share.dto.UserDto;
import com.share.dto.UserInfoDto;
import com.share.entity.User;
import com.share.entity.UserRelation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description TODO:User类接口
 * @Author YuYu
 * @Date 2020-01-14 14:59
 * @Version 1.0
 */
public interface UserService {
    /**
     * 用户注册
     * @param username
     * @param password
     * @param request
     * @return
     */
    Integer addUser(String username, String password, HttpServletRequest request);

    /**
     * 用户信息查询
     * @param uid
     * @return
     */
    UserDto findUserById(Long uid);

    /**
     * 忘记密码----修改密码
     * @param username
     * @param password
     * @return
     */
    Integer modifyUserPassword(String username,String password);

    /**
     * 根据用户名 查询用户密码
     * @param username -- 用户邮箱登录的
     * @return
     */
    User findUserByName(String username);

    /**
     * 更新用户基本信息
     * @param userInfoDto
     * @return
     */
    Integer modifyUserInfo(UserInfoDto userInfoDto,String id);

    /**
     * 修改用户头像
     * @param userDto
     * @return
     */
    Integer modifyUserInfo(UserDto userDto);

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
     * 修改关系表 --- 目前直接删除
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
     * 查询粉丝 或偶像 列表 status区分
     * @param uid
     * @param status
     * @return
     */
    List<UserDto> findRelationList(Long uid,Integer status,Integer start);


    /**
     * 查询粉丝 或偶像 总数status区分
     * @param uid
     * @param status
     * @return
     */
    Integer findRelationTotal(Long uid,Integer status);

    /**
     * 通过id增加经验
     * @param uid
     * @param exp
     * @return
     */
    Integer addExpByUid(Long uid,Integer exp);

}
