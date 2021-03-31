package com.share.utils;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO:正则表达式测试图片文件名
 * @Author Kang
 * @Date 2020-02-01 19:27
 * @Version 1.0
 */
public class RegExUtils {

    /**
     * 筛选文件名<img>中的文件名
     *思想先匹配img 标签在匹配img的src值
     * @return
     */
    public static List<String> regExFileName(String content) {
        List<String> tempFiles = new ArrayList<>();
        String img = "";
        Pattern pImage;
        Matcher mImage;
             //图片链接地址
        String regExImg = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
//        标签属性中src
        String regExSrc = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
        pImage = Pattern.compile(regExImg, Pattern.CASE_INSENSITIVE);
        mImage = pImage.matcher(content);
        while (mImage.find()) {
            // 得到<img />数据
            img = mImage.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile(regExSrc).matcher(img);
            //[/img/20200202112207927.JPG, /img/20200202112212248.JPG]
            while (m.find()) {
                //截取最后的文件名
                String substring = m.group(1).substring(m.group(1).lastIndexOf('/')+1);
                tempFiles.add(substring);
            }
        }
        return tempFiles;
    }

}
