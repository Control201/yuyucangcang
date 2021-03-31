package com.share.controller.user;

import com.share.constant.Exp;
import com.share.dto.BlogDto;
import com.share.dto.ReslutTypeDto;
import com.share.dto.UserDto;
import com.share.entity.CollectBlog;
import com.share.service.impl.BlogServiceImpl;
import com.share.service.impl.UserServiceImpl;
import com.share.statusenum.BlogStatusEnum;
import com.share.utils.DateUtils;
import com.share.utils.ExpUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO:博客相关--需要用户权限
 * @Author Kang
 * @Date 2020-01-30 13:47
 * @Version 1.0
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogServiceImpl blogServiceimpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private ExpUtils expUtils;

    @GetMapping("/add")
    public String addBlog() {
        return "blog/add";
    }

    /**
     * 删除帖子所有相关
     * @param aid
     * @return
     */
    @PostMapping("/del")
    @ResponseBody
    public ReslutTypeDto delBlog(@RequestParam("aid")Integer aid){
        Integer integer = blogServiceimpl.delBlog(aid);
        if (integer == 1) {
            return new ReslutTypeDto(BlogStatusEnum.del_Blog_OK);
        }
        return new ReslutTypeDto(BlogStatusEnum.del_Blog_Exception);
    }
    /**
     * 文章收藏
     * @param status
     * @param aid
     * @param uid
     * @return
     */
    @PostMapping("/collect")
    @ResponseBody
    public ReslutTypeDto collectBlog(@RequestParam("mark") String status,
                                     @RequestParam("aid") String aid,
                                     @RequestParam("uid") String uid){
        CollectBlog collectBlog = new CollectBlog();
        collectBlog.setCreateTime(new Date());
        collectBlog.setAid(Integer.valueOf(aid));
        collectBlog.setStatus(Integer.valueOf(status));
        collectBlog.setUid(Long.valueOf(uid));
        //查询该用户收藏 -- 防止出现数据异常
        Integer collectBlogTotal = blogServiceimpl.findCollectBlog(collectBlog);
        if (collectBlog.getStatus() == 0 && collectBlogTotal > 0) {
            Integer delCollectBlog = blogServiceimpl.delCollectBlog(collectBlog);
            if (delCollectBlog != 0) {
                //删除记录成功，取消收藏
                return new ReslutTypeDto(BlogStatusEnum.collect_Blog_Fail);
            } else {
                //否则操作异常
                return new ReslutTypeDto(BlogStatusEnum.collect_Blog_Exception);
            }
        } else if (collectBlog.getStatus() == 1 && collectBlogTotal == 0) {
            Integer addCollectBlog = blogServiceimpl.addCollectBlog(collectBlog);
            if (addCollectBlog == 1) {
                //删除记录成功，取消收藏
                return new ReslutTypeDto(BlogStatusEnum.collect_Blog_OK);
            } else {
                //否则操作异常
                return new ReslutTypeDto(BlogStatusEnum.collect_Blog_Exception);
            }
        }
        //否则操作异常
        return new ReslutTypeDto(BlogStatusEnum.collect_Blog_Exception);
    }
    /**
     * 发布文章
     *
     * @param blogDto
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public ReslutTypeDto addBlog(@RequestBody BlogDto blogDto,HttpServletRequest request) {
        Long uid= Long.valueOf(blogDto.getUid());
        Integer integer = null;
        try {
            integer = blogServiceimpl.addBlog(blogDto);
        } catch (Exception e) {
            return new ReslutTypeDto(BlogStatusEnum.Blog_Publish_Fail);
        }
        if (integer == 1) {
//            增加经验
            Integer exp = expUtils.addExp(uid, Exp.BLOG_EXP);
            expUtils.dataSync(request,exp);
            return new ReslutTypeDto(BlogStatusEnum.Blog_Publish_OK);
        }
        return new ReslutTypeDto(BlogStatusEnum.Blog_Publish_Fail);
    }
    /**
     * 上传图片方法
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/upload", "/upload/set"})
    @ResponseBody
    public Map<String, Object> uploadFile(HttpServletRequest request, @Param("file") MultipartFile file) throws IOException {
        String res = DateUtils.date2String(new Date(), "yyyyMMddHHmmssSS");
        //服务器上使用
         String rootPath ="/var/file/upload/img/";
        //本地使用
//        String rootPath = "D:/project/communityFile/";
        //获取原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称用时间代替。后面为文件格式
        String newFileName = res + originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件绝对路径
        File newFile = new File(rootPath + newFileName);
        //判断目标文件所在的目录是否存在
        if (!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        //将内存中的数据写入磁盘
        file.transferTo(newFile);
        //完整的url -- 为虚拟路径
        String fileUrl = "/img/" + newFileName;

        Map<String, Object> map = new HashMap<String, Object>(16);
        Map<String, Object> data = new HashMap<String, Object>(16);

        //判断是否为修改头像
        Integer code = this.setAvatar(request, newFileName);
        if (code == 1) {
            //上传成功
            //0表示成功，1失败
            map.put("code", 0);
            //提示消息
            map.put("msg", "上传成功");
        } else {
            //上传失败
            //0表示成功，1失败
            map.put("code", 1);
            //提示消息
            map.put("msg", "上传失败");
        }
        map.put("data", data);
        //图片url
        data.put("src", fileUrl);
        //图片名称，这个会显示在输入框里   可不写
        data.put("title", newFileName);
        return map;
    }
    /**
     * 修改用户头像
     * @param request
     * @param newFileName
     */
    private Integer setAvatar(HttpServletRequest request, String newFileName) {
        String path = "set";
        String requestUrl = request.getRequestURI();
        String way = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
//        判断是哪个接口uri访问的
        if (!path.equals(way)) {
            //默认通过
            return 1;
        }
        HttpSession session = request.getSession();
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        loginUser.setAvatar("/img/" + newFileName);
        //1 为修改成功  其他为失败
        Integer code = userServiceImpl.modifyUserInfo(loginUser);
        //如果成功的话  新的头像路径数据回写
        if (code == 1 ){
            session.setAttribute("loginUser",loginUser);
        }
        return code;
    }
}
