package com.share.service.impl;

import com.share.dto.ArticleDetailDto;
import com.share.dto.ArticleDto;
import com.share.dto.BlogDto;
import com.share.entity.Blog;
import com.share.entity.CollectBlog;
import com.share.entity.Page;
import com.share.mapper.BlogMapper;
import com.share.service.BlogService;
import com.share.utils.RegExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @Description TODO:博客实现类
 * @Author YuYu
 * @Date 2020-01-31 14:53
 * @Version 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Integer addBlog(BlogDto blogDto) {
        Blog blog = new Blog();
        blog.setUid(Long.parseLong(blogDto.getUid()));
        blog.setTitle(blogDto.getTitle());
        blog.setContent(blogDto.getContent());
        blog.setTempFile(RegExUtils.regExFileName(blog.getContent()).toString());
        blog.setReviewStatus(Integer.valueOf(blogDto.getReviewStatus()));
        blog.setOpenProcedure(Integer.valueOf(blogDto.getOpenProcedure()));
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        return blogMapper.addBlog(blog);
    }

    @Override
    public List<ArticleDto> homePageBlog(Page page) {
        return blogMapper.homePageBlog(page);
    }

    @Override
    public List<ArticleDto> findBlogList(Page page) {
        return blogMapper.findBlogList(page);
    }

    @Override
    public Integer findTotal(Page page) {
        return blogMapper.findTotal(page);
    }

    @Override
    public List<ArticleDto> findRelationBlogList(Page page) {
        return blogMapper.findRelationBlogList(page);
    }

    @Override
    public Integer findRelationBlogTotal(Page page) {
        return blogMapper.findRelationBlogTotal(page);
    }

    @Override
    public List<ArticleDto> hotSpot() {
        return blogMapper.hotSpot();
    }

    @Override
    public List<ArticleDto> findBlogByUid(Long uid,Integer open_procedure,Integer start) {
        return blogMapper.findBlogByUid(uid,open_procedure,start);
    }

    @Override
    public ArticleDetailDto findBlogByAid(Integer aid) {
        return blogMapper.findBlogByAid(aid);
    }

    @Override
    public Integer addBrowseNum(Integer aid) {
        return blogMapper.addBrowseNum(aid);
    }

    @Override
    public Integer findTotalByUid(Long uid, Integer open_procedure) {
        return blogMapper.findTotalByUid(uid,open_procedure);
    }

    @Override
    public Integer delBlog(Integer aid) {
        return blogMapper.delBlog(aid);
    }
    @Override
    public Integer addCollectBlog(CollectBlog collectBlog) {
        return blogMapper.addCollectBlog(collectBlog);
    }
    @Override
    public Integer delCollectBlog(CollectBlog collectBlog) {
        return blogMapper.delCollectBlog(collectBlog);
    }
    @Override
    public Integer findCollectBlog(CollectBlog collectBlog) {
        return blogMapper.findCollectBlog(collectBlog);
    }

    @Override
    public List<ArticleDto> findCollectBlogList(Long uid, Integer start) {
        return blogMapper.findCollectBlogList(uid,start);
    }

    @Override
    public Integer updateBlogTime(Integer aid, Date updateTime) {
        return blogMapper.updateBlogTime(aid,updateTime);
    }
}
