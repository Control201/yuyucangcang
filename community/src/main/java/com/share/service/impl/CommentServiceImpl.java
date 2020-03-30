package com.share.service.impl;

import com.share.dto.CommentDto;
import com.share.entity.Comment;
import com.share.entity.LoveRecord;
import com.share.entity.Page;
import com.share.mapper.CommentMapper;
import com.share.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO:
 * @Author YuYu
 * @Date 2020-02-04 16:22
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    public CommentMapper commentMapper;

    @Override
    public List<CommentDto> findCommentByAid(Page page) {
        return commentMapper.findCommentByAid(page);
    }

    @Override
    public List<CommentDto> findCommentByUid(Page page) {
        return commentMapper.findCommentByUid(page);
    }

    @Override
    public Integer findTotal(Page page) {
        return commentMapper.findTotal(page);
    }

    @Override
    public Integer addComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.addComment(comment);
    }

    @Override
    public Integer findLoveRecord(LoveRecord loveRecord) {
        return commentMapper.findLoveRecord(loveRecord);
    }

    @Override
    public Integer addLoveRecord(LoveRecord loveRecord) {
        return commentMapper.addLoveRecord(loveRecord);
    }

    @Override
    public Integer delLoveRecord(LoveRecord loveRecord) {
        return commentMapper.delLoveRecord(loveRecord);
    }

    @Override
    public Integer delComment(Integer acid) {
        return commentMapper.delComment(acid);
    }
}
