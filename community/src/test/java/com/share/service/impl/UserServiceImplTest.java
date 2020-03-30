package com.share.service.impl;

import com.share.dto.ArticleDetailDto;
import com.share.dto.ArticleDto;
import com.share.dto.CommentDto;
import com.share.dto.UserDto;
import com.share.entity.Comment;
import com.share.entity.LoveRecord;
import com.share.entity.Page;
import com.share.mapper.BlogMapper;
import com.share.mapper.UserMapper;
import com.share.service.UserService;
import com.share.utils.DateUtils;
import com.share.utils.RedisUtil;
import com.share.utils.VerCodeGenerateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO: User类持久层测试
 * @Author Kang
 * @Date 2020-01-14 15:02
 * @Version 1.0
 */

//配置spring环境
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogServiceImpl blogServiceImpl;

    @Autowired
    CommentServiceImpl commentServiceImpl;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        String key = "test";
        String value = "测试";
//        插入缓存
        redisTemplate.opsForValue().set(key,value,300, TimeUnit.SECONDS);
//        取缓存
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    public void addUser() {
        String verCode = VerCodeGenerateUtil.getVerCode();
        System.out.println(verCode);
    }
    @Test
    public void update() {
        LoveRecord loveRecord = new LoveRecord();
        loveRecord.setAcid(10003);
        loveRecord.setUid(1580265547356934882L);
        loveRecord.setCreateTime(new Date());
        loveRecord.setStatus(1);
//        Integer integer = commentServiceImpl.findLoveRecord(loveRecord);
//        Integer integer = commentServiceImpl.addLoveRecord(loveRecord);
        Integer integer1 = commentServiceImpl.delLoveRecord(loveRecord);
        System.out.println(integer1);


    }

    @Test
    public void findAllBlog(){
        List<UserDto> idolList = userService.findRelationList(1581908378401380633L, 0, 0);
        Integer idolTotal = userService.findRelationTotal(1581908378401380633L, 0);
        //查询粉丝列表
        List<UserDto> fansList = userService.findRelationList(1581908378401380633L, 1, 0);
        Integer fansTotal = userService.findRelationTotal(1581908378401380633L, 1);
        System.out.println(idolList);
        System.out.println(idolTotal);
        System.out.println(fansTotal);
        System.out.println(fansList);
    }
}