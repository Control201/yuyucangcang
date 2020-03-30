package com.share.service.impl;

import com.share.dto.MessageDto;
import com.share.dto.ReplyDto;
import com.share.dto.UserDto;
import com.share.entity.Message;
import com.share.mapper.BlogMapper;
import com.share.mapper.MessageMapper;
import com.share.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO:
 * @Author YuYu
 * @Date 2020-02-07 19:05
 * @Version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Integer addMessage(ReplyDto replyDto) {
        Message message = new Message();
        //评论接受者id
        Long rid = replyDto.getRid();
        Integer aid = replyDto.getAid();
        if (rid == null) {
            //评论文章--发送给博主
            message.setStatus(0);
            message.setRid(replyDto.getAuid());
        } else {
            //回复评论--发送给评论者
            message.setRid(rid);
            message.setStatus(1);
        }
        message.setUid(replyDto.getUid());
        message.setAid(aid);
        message.setContent(replyDto.getContent());
        message.setCreateTime(new Date());
        return messageMapper.addMessage(message);
    }



    @Override
    public List<MessageDto> findMessageByRid(Long uid,Integer start) {
        return messageMapper.findMessageByRid(uid,start);
    }

    @Override
    public List<MessageDto> findMessageByUid(Long uid) {
        return messageMapper.findMessageByUid(uid);
    }

    @Override
    public Integer findTotalByRid(Long uid) {
        return messageMapper.findTotalByRid(uid);
    }

    @Override
    public Integer setReadStatus(Message message) {
        return messageMapper.setReadStatus(message);
    }

    @Override
    public Integer sendMessage(Message message) {
        message.setCreateTime(new Date());
        //发送私信，aid默认1
        message.setAid(1);
        return messageMapper.addMessage(message);
    }

    @Override
    public Integer findNewMessageTotal(Long rid) {
        return messageMapper.findNewMessageTotal(rid);
    }
}
