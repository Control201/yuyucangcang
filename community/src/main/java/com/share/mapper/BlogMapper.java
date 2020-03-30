package com.share.mapper;

import com.share.dto.ArticleDetailDto;
import com.share.dto.ArticleDto;
import com.share.entity.Blog;
import com.share.entity.CollectBlog;
import com.share.entity.LoveRecord;
import com.share.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO:博客相关
 * @Author YuYu
 * @Date 2020-02-01 18:41
 * @Version 1.0
 */
@Mapper
@Repository
public interface BlogMapper {
    /**
     *添加一条博客
     * @return
     */
    Integer addBlog(Blog blog);
    /**
     * 查询用户自己发表的总数
     * @param uid
     * @return
     */
    Integer findTotalByUid(@Param("uid") Long uid,@Param("open_procedure")Integer open_procedure);
    /**
     * 初始化页面文章
     * @return
     */
    List<ArticleDto> homePageBlog(Page page);
    /**
     * 分类查询博客列表
     * @param page
     * @return
     */
    List<ArticleDto> findBlogList(Page page);
    /**
     *  通过类别查询总数
     * @param page
     * @return
     */
    Integer findTotal(Page page);
    /**
     * 查询被关注人的博客列表
     * @param page
     * @return
     */
    List<ArticleDto> findRelationBlogList(Page page);
    //查询被关注人的博客总数
    Integer findRelationBlogTotal(Page page);
    /**
     * 最近七天热点动态
     * @return
     */
    List<ArticleDto> hotSpot();
    /**
     * 查询博客细节通过aid
     * @param aid
     * @return
     */
    ArticleDetailDto findBlogByAid(Integer aid);
    /**
     * 添加浏览量
     *  @param aid
     * @return
     */
    Integer addBrowseNum(Integer aid);
    /**
     * 通过uid和类别查询文章分页
     * @param uid
     * @return
     */
    List<ArticleDto> findBlogByUid(@Param("uid") Long uid,@Param("open_procedure") Integer open_procedure,@Param("start") Integer start);
    /**
     * 删除文章
     * @param aid
     * @return
     */
    Integer delBlog(Integer aid);
    /**
     * 添加 收藏博客记录
     * @param collectBlog
     * @return
     */
    Integer addCollectBlog(CollectBlog collectBlog);

    /**
     * 删除 收藏博客记录
     * @param collectBlog
     * @return
     */
    Integer delCollectBlog(CollectBlog collectBlog);

    /**
     * 查询收藏记录数 --uid和aid为依据 --返回条数
     * @param collectBlog
     * @return
     */
    Integer findCollectBlog(CollectBlog collectBlog);

    /**
     * 查询收藏文章及其分页
     * @param uid
     * @param start
     * @return
     */
    List<ArticleDto> findCollectBlogList(@Param("uid") Long uid,@Param("start") Integer start);

    /**
     * 更新时间权重
     * @param aid
     * @param updateTime
     * @return
     */
    Integer updateBlogTime(@Param("aid")Integer aid,@Param("updateTime") Date updateTime);
}
