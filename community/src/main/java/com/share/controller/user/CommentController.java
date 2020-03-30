package com.share.controller.user;

import com.share.dto.*;
import com.share.entity.Comment;
import com.share.entity.LoveRecord;
import com.share.entity.Page;
import com.share.service.impl.BlogServiceImpl;
import com.share.service.impl.CommentServiceImpl;
import com.share.service.impl.MessageServiceImpl;
import com.share.statusenum.BlogStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO:评论相关
 * @Author YuYu
 * @Date 2020-02-04 19:46
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    private MessageServiceImpl messageServiceImpl;

    @Autowired
    private BlogServiceImpl blogServiceimpl;

    /**
     * 评论文章
     *
     * @param replyDto
     * @return
     */
    @PostMapping("/review")
    @ResponseBody
    public ReslutTypeDto review(@RequestBody ReplyDto replyDto) {
        Long rid = replyDto.getRid();
        Long uid = replyDto.getUid();
        Long auid = replyDto.getAuid();
        Comment comment = new Comment();
        comment.setUid(uid);
        comment.setContent(replyDto.getContent().trim());
        comment.setAid(replyDto.getAid());

        Integer integer = null;
        try {
            integer = commentServiceImpl.addComment(comment);
            //更新文章排行权重。
            blogServiceimpl.updateBlogTime(replyDto.getAid(), new Date());
        } catch (Exception e) {
            return new ReslutTypeDto(BlogStatusEnum.Review_Blog_Fail);
        }
        //写入数据库成功，然后发送消息提醒
        if (integer == 1) {
            //如果是同一人 则 不发消息
            if (uid.equals(auid) && rid == null || uid.equals(rid)) {
                return new ReslutTypeDto(BlogStatusEnum.Review_Blog_OK);
            } else {
                //发送消息
                messageServiceImpl.addMessage(replyDto);
            }
            return new ReslutTypeDto(BlogStatusEnum.Review_Blog_OK);
        }
        return new ReslutTypeDto(BlogStatusEnum.Review_Blog_Fail);
    }

    /**
     * 评论点赞
     *
     * @return
     */
    @PostMapping("/comment/likeMark")
    @ResponseBody
    public ReslutTypeDto likeMark(@RequestParam("mark") String status,
                                  @RequestParam("id") String acid,
                                  @RequestParam("uid") String uid) {
        LoveRecord loveRecord = new LoveRecord();
        loveRecord.setCreateTime(new Date());
        loveRecord.setAcid(Integer.valueOf(acid));
        loveRecord.setStatus(Integer.valueOf(status));
        loveRecord.setUid(Long.valueOf(uid));
        //查询该用户该评论点赞数数 -- 防止出现数据异常
        Integer recordTotal = commentServiceImpl.findLoveRecord(loveRecord);
        if (loveRecord.getStatus() == 0 && recordTotal > 0) {
            Integer delLoveRecord = commentServiceImpl.delLoveRecord(loveRecord);
            if (delLoveRecord != 0) {
                //删除记录成功，取消点赞
                return new ReslutTypeDto(BlogStatusEnum.like_Comment_Fail);
            } else {
                //否则操作异常
                return new ReslutTypeDto(BlogStatusEnum.like_Comment_Exception);
            }
        } else if (loveRecord.getStatus() == 1 && recordTotal == 0) {
            Integer addLoveRecord = commentServiceImpl.addLoveRecord(loveRecord);
            if (addLoveRecord == 1) {
                //删除记录成功，取消点赞
                return new ReslutTypeDto(BlogStatusEnum.like_Comment_OK);
            } else {
                //否则操作异常
                return new ReslutTypeDto(BlogStatusEnum.like_Comment_Exception);
            }
        }
        //否则操作异常
        return new ReslutTypeDto(BlogStatusEnum.like_Comment_Exception);
    }

    /**
     * 查询评论---分页
     *
     * @param aid
     * @param currentPage
     * @return
     */
    @PostMapping("/comment/{aid}/{currentPage}")
    @ResponseBody
    public Map<String, Object> findComment(@PathVariable("aid") String aid
            , @PathVariable(value = "currentPage") String currentPage
            , HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(aid);
        Integer pageNum = Integer.parseInt(currentPage);
        //如果请求值被恶意刷成负数则进行处理
        if (pageNum < 1) {
            pageNum = 1;
        }
        //初始页面查询
        Page page = new Page();
        page.setAid(id);
        page.setStart((pageNum - 1) * 10);
        page.setRows(10);
        page.setUid(loginUser.getUid());
        List<CommentDto> commentList = commentServiceImpl.findCommentByUid(page);
        Integer total = commentServiceImpl.findTotal(page);
        map.put("total", total);
        map.put("currentPage", pageNum);
        map.put("commentList", commentList);
        return map;
    }


    /**
     * 删除评论--删除点赞
     * @param acid
     * @return
     */
    @PostMapping("/comment/del")
    @ResponseBody
    public ReslutTypeDto delComment(@RequestParam("acid") Integer acid) {
        Integer integer = commentServiceImpl.delComment(acid);
        if (integer == 1) {
            //成功则返回ok否则直接异常处理
            return new ReslutTypeDto(BlogStatusEnum.del_Comment_OK);
        }
        return new ReslutTypeDto(BlogStatusEnum.del_Comment_Exception);
    }
}
