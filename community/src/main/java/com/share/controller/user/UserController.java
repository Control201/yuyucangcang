package com.share.controller.user;


import com.share.constant.TextType;
import com.share.dto.*;
import com.share.entity.CollectBlog;
import com.share.entity.Message;
import com.share.entity.UserRelation;
import com.share.service.impl.BlogServiceImpl;
import com.share.service.impl.MessageServiceImpl;
import com.share.statusenum.UserStatusEnum;
import com.share.service.impl.UserServiceImpl;
import com.share.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO:用户登录后的界面
 * @Author Kang
 * @Date 2020-01-14 16:35
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private BlogServiceImpl blogServiceImpl;
    @Autowired
    private MessageServiceImpl messageServiceImpl;

    /**
     * 查询用户信息用于修改展示
     *
     * @param uid
     * @param model
     * @return
     */
    @GetMapping("/set/{uid}")
    public String findUserById(@PathVariable("uid") String uid, Model model) {
        Long id = Long.parseLong(uid);
        UserDto userDto = userServiceImpl.findUserById(id);
        model.addAttribute("UserInfo", userDto);
        return "user/set";
    }

    /**
     * 用户的所有帖子主页--以及收藏
     *
     * @param request
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/index");
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Long uid = loginUser.getUid();
//最近十条---公开的
        List<ArticleDto> personalArticleList = blogServiceImpl.findBlogByUid(uid, 0, 0);
//       公开的文章总数
        Integer personalArticlesTotal = blogServiceImpl.findTotalByUid(uid, 0);
//最近十条---私密的
        List<ArticleDto> privacyArticlesList = blogServiceImpl.findBlogByUid(uid, 1, 0);
//       私密的文章总数
        Integer privacyArticlesTotal = blogServiceImpl.findTotalByUid(uid, 1);
// 最近十条----收藏的
        CollectBlog collectBlog = new CollectBlog();
        collectBlog.setUid(uid);
        List<ArticleDto> collectBlogList = blogServiceImpl.findCollectBlogList(uid, 0);
//     收藏的文章总数
        Integer collectArticlesTotal = blogServiceImpl.findCollectBlog(collectBlog);
        modelAndView.addObject("personalArticlesTotal", personalArticlesTotal);
        modelAndView.addObject("personalArticleList", personalArticleList);
        modelAndView.addObject("privacyArticlesTotal", privacyArticlesTotal);
        modelAndView.addObject("privacyArticleList", privacyArticlesList);
        modelAndView.addObject("collectArticlesTotal", collectArticlesTotal);
        modelAndView.addObject("collectBlogList", collectBlogList);
        return modelAndView;
    }


    /**
     * ajax分页查看用户帖子
     *
     * @param type
     * @param currentPage
     * @param request
     * @return
     */
    @PostMapping("/index/page")
    @ResponseBody
    public Map<String, Object> indexPage(@RequestParam("type") String type,
                                         @RequestParam("currentPage") String currentPage,
                                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Long uid = loginUser.getUid();
        Integer curr = Integer.valueOf(currentPage);
        List<ArticleDto> articleDtoList = null;
        //根据不同类型进行分页查询
        if (type.equals(TextType.PERSONAL_ARTICLE)) {
            articleDtoList = blogServiceImpl.findBlogByUid(uid, 0, (curr - 1) * 10);
        } else if (TextType.PRIVACY_ARTICLE.equals(type)) {
            articleDtoList = blogServiceImpl.findBlogByUid(uid, 1, (curr - 1) * 10);
        } else if (TextType.COLLECT_BLOG.equals(type)) {
            articleDtoList = blogServiceImpl.findCollectBlogList(uid, (curr - 1) * 10);
        }
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("articleDtoList", articleDtoList);
        return map;
    }

    /**
     * 修改用户基本信息
     *
     * @param userInfoDto
     * @return
     */
    @PostMapping("/set/{uid}")
    @ResponseBody
    public ReslutTypeDto modifyInfo(@RequestBody UserInfoDto userInfoDto,
                                    @PathVariable("uid") String uid,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Integer integer = null;
        try {
            integer = userServiceImpl.modifyUserInfo(userInfoDto, uid);
        } catch (Exception e) {
            //特殊字段抛出异常
            return new ReslutTypeDto(UserStatusEnum.User_Info_Modify_Fail);
        }
        if (integer == null || !integer.equals(1)) {
            return new ReslutTypeDto(UserStatusEnum.User_Info_Modify_Fail);
        }
        //session中得数据得同步：比如昵称
        loginUser.setNickName(userInfoDto.getNickname());
        session.setAttribute("loginUser", loginUser);
        return new ReslutTypeDto(UserStatusEnum.User_Info_Modify_OK);
    }

    /**
     * 查看个人消息
     *
     * @param request
     * @return
     */
    @GetMapping(value = {"/message", "/message/{currentPage}"})
    public ModelAndView message(HttpServletRequest request
            , @PathVariable(value = "currentPage", required = false) String currentPage) {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
//        移除新消息提示
        session.removeAttribute("newMessageTotal");
        Long uid = loginUser.getUid();
        Integer pageNum = 1;
        if (currentPage != null) {
            pageNum = Integer.valueOf(currentPage);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/message");
        //查询最近回复 接受方
        List<MessageDto> messageList = messageServiceImpl.findMessageByRid(uid, (pageNum - 1) * 5);
        Integer total = messageServiceImpl.findTotalByRid(uid);
        modelAndView.addObject("messageList", messageList);
        modelAndView.addObject("currentPage", pageNum);
        modelAndView.addObject("messageTotal", total);
        return modelAndView;
    }

    /**
     * 设置为已读
     *
     * @param request
     * @param mid
     * @return
     */
    @PostMapping(value = {"/message/readMessage", "/message/readAllMessage"})
    @ResponseBody
    public ReslutTypeDto readMessage(HttpServletRequest request
            , @RequestParam(value = "mid", required = false) Integer mid) {
        Message message = new Message();
        Integer total = null;
        //根据mid判断是设置所有还是一个
        if (mid == null) {
            HttpSession session = request.getSession();
            UserDto loginUser = (UserDto) session.getAttribute("loginUser");
            Long uid = loginUser.getUid();
            //接收方
            message.setRid(uid);
            total = messageServiceImpl.setReadStatus(message);
        } else {
            message.setId(mid);
            total = messageServiceImpl.setReadStatus(message);
        }
        if (total == 0) {
            return new ReslutTypeDto(UserStatusEnum.Message_Read_Fail);
        }
        return new ReslutTypeDto(UserStatusEnum.Message_Read_OK);
    }

    /**
     * 主页发送消息
     *
     * @return
     */
    @PostMapping(value = {"/message/send"})
    @ResponseBody
    public ReslutTypeDto sendMessage(@RequestParam("uid") Long uid
            , @RequestParam("rid") Long rid
            , @RequestParam("content") String content) {
        Message message = new Message();
        message.setRid(rid);
        message.setUid(uid);
        message.setContent(content);
        //状态2 为私信
        message.setStatus(2);
        Integer integer = null;
        try {
            integer = messageServiceImpl.sendMessage(message);
            if (integer == 1) {
                return new ReslutTypeDto(UserStatusEnum.Message_Send_OK);
            }
        } catch (Exception e) {
            return new ReslutTypeDto(UserStatusEnum.Message_Send_Fail);
        }
        return new ReslutTypeDto(UserStatusEnum.Message_Send_Fail);
    }

    /**
     * 主页改变关系
     *
     * @param uid
     * @param rid
     * @return
     */
    @PostMapping(value = {"/home/modifyRelation"})
    @ResponseBody
    public ReslutTypeDto modifyRelation(@RequestParam("uid") Long uid
            , @RequestParam("rid") Long rid
            , @RequestParam("status") Integer status) {
        UserRelation userRelation = new UserRelation();
        userRelation.setUid(uid);
        userRelation.setRid(rid);
        userRelation.setStatus(status);
        Integer integer = null;
        try {
            //添加关注
            if (status == 1) {
                integer = userServiceImpl.addRelation(userRelation);
                if (integer == 1) {
                    return new ReslutTypeDto(UserStatusEnum.Add_Relation_OK);
                }
            } else {
                //取消关注
                integer = userServiceImpl.modifyRelation(userRelation);
                if (integer == 1) {
                    return new ReslutTypeDto(UserStatusEnum.Modify_Relation_OK);
                }
            }
        } catch (Exception e) {
            return new ReslutTypeDto(UserStatusEnum.Relation_Exception);
        }
        return new ReslutTypeDto(UserStatusEnum.Relation_Exception);
    }

    /**
     * 查看主页
     *
     * @param uid
     * @return
     */
    @GetMapping("/home/{uid}")
    public ModelAndView findHomeById(@PathVariable("uid") String uid
            , HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/home");
        Long id = Long.parseLong(uid);
        //判断是否与登录用户有关系
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser != null) {
            UserRelation userRelation = new UserRelation();
            userRelation.setUid(loginUser.getUid());
            userRelation.setRid(id);
            UserRelation relation = userServiceImpl.findRelation(userRelation);
            modelAndView.addObject("relation", relation);
        }
        UserDto userDto = userServiceImpl.findUserById(id);
        String time = DateUtils.date2String(new Date(), "yyyy-MM-dd");
        List<ArticleDto> articleList = blogServiceImpl.findBlogByUid(id, 0, 0);
        //查询最近回复 发送方
        List<MessageDto> messageList = messageServiceImpl.findMessageByUid(id);
        modelAndView.addObject("messageList", messageList);
        modelAndView.addObject("time", time);
        modelAndView.addObject("UserInfo", userDto);
        modelAndView.addObject("articleList", articleList);
        return modelAndView;
    }

    /**
     * 用户查看好友关系列表
     *
     * @return
     */
    @GetMapping("/relation")
    public ModelAndView userRelation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Long uid = loginUser.getUid();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/relation");
        //查询关注人列表
        List<UserDto> idolList = userServiceImpl.findRelationList(uid, 0, 0);
        Integer idolTotal = userServiceImpl.findRelationTotal(uid, 0);
        //查询粉丝列表
        List<UserDto> fansList = userServiceImpl.findRelationList(uid, 1, 0);
        Integer fansTotal = userServiceImpl.findRelationTotal(uid, 1);
        modelAndView.addObject("idolList", idolList);
        modelAndView.addObject("idolTotal",idolTotal);
        modelAndView.addObject("fansList", fansList);
        modelAndView.addObject("fansTotal",fansTotal);
        return modelAndView;
    }

    @PostMapping("/relation/page")
    @ResponseBody
    public Map<String, Object> relationPage(@RequestParam("type") String type,
                                         @RequestParam("currentPage") String currentPage,
                                         HttpServletRequest request){
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        Long uid = loginUser.getUid();
        int curr = Integer.parseInt(currentPage);
        List<UserDto> userInfoList = null;
        //根据不同类型进行分页查询
        if (TextType.IDOL_PAGE.equals(type)) {
            userInfoList = userServiceImpl.findRelationList(uid, 0, (curr - 1) * 10);
        } else if (TextType.FANS_PAGE.equals(type)) {
            userInfoList = userServiceImpl.findRelationList(uid, 1, (curr - 1) * 10);
        }
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("userInfoList", userInfoList);
        return map;
    }
}
