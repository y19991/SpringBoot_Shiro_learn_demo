package com.yafnds.springbootshiro.util;

import java.util.Random;

/**
 * 包名称：com.yafnds.springbootshiro.util
 * 类名称：EncryptUtil
 * 类描述：加密工具类
 * 创建人：@author y19991
 * 创建时间：2021/4/29 10:41
 */

public class EncryptUtil {

    /** 散列次数 */
    public static final int HASH_ITERATIONS = 1024;

    /**
     * 生成随机盐
     * @param n 要生成的盐的长度
     * @return 长度为 n 的随机盐
     */
    public static String getSalt(int n) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // 获取一个随机的字符
            char randomChar = chars[new Random().nextInt(chars.length)];
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
