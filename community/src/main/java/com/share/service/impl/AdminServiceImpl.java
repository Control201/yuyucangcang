package com.share.service.impl;

import com.share.mapper.AdminMapper;
import com.share.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO:管理员权限
 * @Author Kang
 * @Date 2020-03-04 14:41
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Integer setTopBlog(Integer top, Integer aid) {
        return adminMapper.setTopBlog(top,aid);
    }

    @Override
    public Integer setBoutiqueBlog(Integer articleType, Integer aid) {
        return adminMapper.setBoutiqueBlog(articleType,aid);
    }

}
