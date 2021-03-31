package com.share.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @Description TODO:uid生成器
 * @Author Kang
 * @Date 2020-01-14 17:19
 * @Version 1.0
 */
public class UidUtils {
    private static final Random RANDOM = new SecureRandom();

    /**
     * 获取uid算法：时间戳（13位） 加一百万 内随机数（大约6位）
     * 该方法最大并发 一毫秒内重复几率百万分之一
     * <p>
     * 还可采取redis获取唯一uid
     *
     * @return
     */
    public static Long getUid() {
        long timeMillis = System.currentTimeMillis();
        String num1 = String.valueOf(timeMillis);
        long random = (long) (RANDOM.nextInt(1000000));
        String num2 = String.valueOf(random);
        Long uid = Long.parseLong(num1 + num2);
        return uid;
    }

    public static void main(String[] args) {
        String s = "/blog/noReply/1";
        String keyword = s.substring(s.lastIndexOf("g")+2,s.lastIndexOf("/"));
        System.out.println(keyword);
    }
}
