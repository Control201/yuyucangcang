package com.share.service.impl;

import com.share.dto.UserDto;
import com.share.dto.UserInfoDto;
import com.share.entity.UserRelation;
import com.share.statusenum.AccountStatusEnum;
import com.share.mapper.UserMapper;
import com.share.entity.User;
import com.share.service.UserService;
import com.share.utils.AddressByIpUtils;
import com.share.utils.DateUtils;
import com.share.utils.SaltUtils;
import com.share.utils.UidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO:UserService接口实现层
 * @Author YuYu
 * @Date 2020-01-14 15:00
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @Override
    public Integer addUser(String username, String password, HttpServletRequest request) {
        //ip
        String ip = request.getRemoteAddr();
        String address = AddressByIpUtils.getAddressByIP(ip);
        User user = new User();
        Long uid = UidUtils.getUid();
        //substring()下标含头不含尾,为防止uid没19位从13开始截取
        String endsWith = uid.toString().substring(13);
        user.setUid(uid);
        user.setIp(ip);
        user.setLocation(address);
        user.setNickname("Share_"+endsWith);
        user.setEmail(username);
        user.setPassword(SaltUtils.getPwdPlus(password));
        user.setCreateTime(new Date());
        return userMapper.addUser(user);
    }
    /**
     * 查询数据封装到dto类 防止数据暴露
     * @param uid
     * @return
     */
    @Override
    public UserDto findUserById(Long uid) {
        User user = userMapper.findUserById(uid);
        UserDto userDto = new UserDto(
                user.getUid(),
                user.getNickname(),
                user.getAvatar(),
                user.getSex(),
                user.getCreateTime(),
                AccountStatusEnum.NORMAL,
                user.getLastTime(),
                user.getLocation(),
                user.getBirthday(),
                user.getMobile(),
                user.getEmail(),
                user.getSignature(),
                user.getAuthority(),
                user.getExp()
                );
        return userDto;
    }
    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public Integer modifyUserPassword(String username,String password){
        User user = new User();
        user.setEmail(username);
        user.setPassword(SaltUtils.getPwdPlus(password));
        //用户更新信息时间
        user.setUpdateTime(new Date());
        return userMapper.modifyUserPassword(user);
    }
    /**
     * @param username -- 用户邮箱登录的
     * @return
     */
    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }
    /**
     * @param userInfoDto  --更新用户信息
     * @param uid
     * @return
     */
    @Override
    public Integer modifyUserInfo(UserInfoDto userInfoDto,String uid) {
        User user = new User();
        user.setUid(Long.parseLong(uid));
        user.setNickname(userInfoDto.getNickname());
        user.setLocation(userInfoDto.getLocation());
        user.setSex(Integer.valueOf(userInfoDto.getSex()));
        user.setMobile(userInfoDto.getMobile());
        //用户更新信息时间
        user.setUpdateTime(new Date());
        try {
            user.setBirthday(DateUtils.string2Date(userInfoDto.getBirthday(),"yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
           return 0;
        }
        user.setSignature(userInfoDto.getSignature());
        return userMapper.modifyUserInfo(user);
    }

    /**
     * 主要用于修改头像
     * @param userDto
     * @return
     */
    @Override
    public Integer modifyUserInfo(UserDto userDto){
        User user = new User();
        user.setUid(userDto.getUid());
        user.setAvatar(userDto.getAvatar());
        return userMapper.modifyUserInfo(user);
    }
    /**
     * 更新最后登录时间
     * @param user
     * @return
     */
    @Override
    public Integer updateLastTime(User user) {
        user.setLastTime(new Date());
        return userMapper.updateLastTime(user);
    }

    @Override
    public Integer addRelation(UserRelation userRelation) {
        userRelation.setCreateTime(new Date());
        return userMapper.addRelation(userRelation);
    }
    @Override
    public Integer modifyRelation(UserRelation userRelation) {
        userRelation.setModifyTime(new Date());
        return userMapper.modifyRelation(userRelation);
    }

    @Override
    public UserRelation findRelation(UserRelation userRelation) {
        return userMapper.findRelation(userRelation);
    }

    @Override
    public List<UserDto> findRelationList(Long uid, Integer status,Integer start) {
        return userMapper.findRelationList(uid,status,start);
    }

    @Override
    public Integer findRelationTotal(Long uid, Integer status) {
        return userMapper.findRelationTotal(uid,status);
    }

    @Override
    public Integer addExpByUid(Long uid, Integer exp) {
        return userMapper.addExpByUid(uid,exp);
    }
}
