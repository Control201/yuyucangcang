package com.share.controller.user;

import com.share.dto.ReslutTypeDto;
import com.share.service.impl.AdminServiceImpl;
import com.share.statusenum.AdminStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description TODO:管理员权限
 * @Author YuYu
 * @Date 2020-03-04 14:28
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminServiceimpl;

    /**
     * 文章收藏
     *
     * @param top
     * @param aid
     * @return
     */
    @PostMapping("/setTop")
    @ResponseBody
    public ReslutTypeDto topBlog(@RequestParam("top") String top,
                                 @RequestParam("aid") String aid) {
        Integer type = Integer.valueOf(top);
        Integer id = Integer.valueOf(aid);
        try {
            Integer integer = adminServiceimpl.setTopBlog(type,id);
            if (integer == 1) {
//                设置成功
                return new ReslutTypeDto(AdminStatusEnum.Blog_Top_Modify_OK);
            } else {
                return new ReslutTypeDto(AdminStatusEnum.Blog_Modify_Exception);
            }
        } catch (Exception e) {
            return new ReslutTypeDto(AdminStatusEnum.Blog_Modify_Exception);
        }
    }

    /**
     * 文章收藏
     *
     * @param boutique
     * @param aid
     * @return
     */
    @PostMapping("/setBoutique")
    @ResponseBody
    public ReslutTypeDto boutiqueBlog(@RequestParam("boutique") String boutique,
                                      @RequestParam("aid") String aid) {
        Integer type = Integer.valueOf(boutique);
        Integer id = Integer.valueOf(aid);

        try {
            Integer integer = adminServiceimpl.setBoutiqueBlog(type, id);
            if (integer == 1) {
//                设置成功
                return new ReslutTypeDto(AdminStatusEnum.Blog_Boutique_Modify_OK);
            } else {
                return new ReslutTypeDto(AdminStatusEnum.Blog_Modify_Exception);
            }
        } catch (Exception e) {
            return new ReslutTypeDto(AdminStatusEnum.Blog_Modify_Exception);
        }
    }
}
