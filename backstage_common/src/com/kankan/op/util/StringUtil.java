package com.kankan.op.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 生成随机字符串
 * 
 * @author zhangdekun
 */
public class StringUtil {

    private static final Random random = new Random(System.currentTimeMillis());
    public static final String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Random random = new Random();
    public static String genRandomStr(int len) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**把字符数组里的字符串按字典顺序排序*/
    public static String[] stringSort(String[] s) {
        List<String> list = new ArrayList<String>(s.length);
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
        Collections.sort(list);
        return list.toArray(s);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        String s1="fsfasfasf";
        String s2="y234124";
        String s3="adsadasx456n";
        list.add(s1);
        list.add(s2);
        list.add(s3);
        Collections.sort(list);
        System.out.println(list.get(0)+list.get(1)+list.get(2));
    }
}
