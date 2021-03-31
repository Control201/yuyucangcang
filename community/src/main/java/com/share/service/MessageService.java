package com.share.service;

import com.share.dto.MessageDto;
import com.share.dto.ReplyDto;
import com.share.dto.UserDto;
import com.share.entity.Message;

import java.util.List;

/**
 * @Description TODO:消息相关
 * @Author Kang
 * @Date 2020-02-07 19:05
 * @Version 1.0
 */
public interface MessageService {
    /**
     * 添加消息记录
     * @param replyDto
     * @return
     */
    Integer addMessage(ReplyDto replyDto);
    /**
     * 查询记录消息 --接受方
     * @param uid
     * @param start
     * @return
     */
    List<MessageDto> findMessageByRid(Long uid , Integer start);
    /**
     * 查询记录消息 --发送方
     * @param uid
     * @return
     */
    List<MessageDto> findMessageByUid(Long uid);
    /**
     * 接受方 查看消息总数
     * @param uid
     * @return
     */
    Integer findTotalByRid(Long uid);
    /**
     * 设置为已读
     * @param message
     * @return
     */
    Integer setReadStatus(Message message);
    /**
     * 私信添加   消息记录
     * @param message
     * @return
     */
    Integer sendMessage(Message message);
    /**
     * 新消息记录数
     * @param rid
     * @return
     */
    Integer findNewMessageTotal(Long rid);
}
