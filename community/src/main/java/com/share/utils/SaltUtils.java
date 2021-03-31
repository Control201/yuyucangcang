package com.share.utils;

import com.share.constant.Salt;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Description TODO:密码盐值加密
 * @Author Kang
 * @Date 2020-01-16 15:12
 * @Version 1.0
 */
public class SaltUtils {

    public static String getPwdPlus(String password) {
        //加密方式
        String hashAlgorithmName = Salt.HASH_ALGORITHM_NAME;
        //加密次数
        int hashInteractions = Salt.HASH_INTERACTIONS;
        //盐值
        String salt = Salt.ADD_SALT;
        //将得到的result放到数据库中就行了。
        String result = new SimpleHash(hashAlgorithmName, password,ByteSource.Util.bytes(salt), hashInteractions).toHex();
        return result;
    }

/*    public static void main(String[] args) {
        String s = getPwdPlus("12345678");//934cd11fd04135c255567551a5f82fef
        System.out.println(s);
    }*/



}
