package com.share.mapper;

import com.share.dto.CommentDto;
import com.share.entity.Comment;
import com.share.entity.LoveRecord;
import com.share.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description TODO:评论相关
 * @Author YuYu
 * @Date 2020-02-04 15:31
 * @Version 1.0
 */
@Mapper
@Repository
public interface CommentMapper {
    /**
     * 通过aid查询评论
     * @param page
     * @return
     */
    List<CommentDto> findCommentByAid(Page page);

    /**
     * 通过点赞人Uid查询它所看到的评论列表   主要用与展示用户的评论点赞状态
     * @param page
     * @return
     */
    List<CommentDto> findCommentByUid(Page page);

    /**
     *  通过类别查询总数--aid
     * @param page
     * @return
     */
    Integer findTotal(Page page);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    Integer addComment(Comment comment);

    /**
     * 查询评论点赞记录数 --uid 和acid为依据 --返回条数
     * @param loveRecord
     * @return
     */
    Integer findLoveRecord(LoveRecord loveRecord);

    /**
     * 添加点赞记录
     * @param loveRecord
     * @return
     */
    Integer addLoveRecord(LoveRecord loveRecord);

    /**
     * 删除点赞记录 --uid 和acid为依据
     * @param loveRecord
     * @return
     */
    Integer delLoveRecord(LoveRecord loveRecord);
    /**
     * 删除评论
     * @param acid
     * @return
     */
    Integer delComment(Integer acid);
}
