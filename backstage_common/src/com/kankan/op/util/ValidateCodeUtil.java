package com.kankan.op.util;

import java.util.Random;

/**
 * 手机验证码生成器.
 * 
 * @author zhangdekun
 */
public class ValidateCodeUtil {

    private static final Random random = new Random(System.currentTimeMillis());

    /**
     * 生成六位手机验证码.
     * 
     * @return 六位十进制的字符串，不保证唯一.
     */
    public static String validateCode() {
        StringBuilder sb = null;
        sb = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
