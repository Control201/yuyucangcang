package com.share.mapper;

import com.share.dto.MessageDto;
import com.share.dto.UserDto;
import com.share.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO:消息
 * @Author YuYu
 * @Date 2020-02-07 18:41
 * @Version 1.0
 */
@Mapper
@Repository
public interface MessageMapper {

    /**
     * 添加消息记录
     * @param message
     * @return
     */
    Integer addMessage(Message message);


    /**
     * 查询记录消息 --接受方
     * @param uid
     * @param start
     * @return
     */
    List<MessageDto> findMessageByRid(@Param("uid") Long uid, @Param("start") Integer start);

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
     * 新消息记录数
     * @param rid
     * @return
     */
    Integer findNewMessageTotal(Long rid);
}
