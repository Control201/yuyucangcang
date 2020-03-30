package com.share.utils;

import java.security.SecureRandom;
import java.util.Random;


/**
 * @Description TODO:邮箱验证码 ，采用SecureRandom比Random更安全。
 * @Author YuYu
 * @Date 2020-01-19 17:03
 * @Version 1.0
 */

public class VerCodeGenerateUtil {
    private static final String SYMBOLS = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();
    /**
     * 生成4位随机验证码
     * @return 返回4位验证码
     */
    public static String getVerCode() {
        char[] nonceChars = new char[4];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

}

