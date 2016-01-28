package com.kankan.op.util;

/**
 * <pre>
 * http://stackoverflow.com/questions/2938482/encode-decode-a-long-to-a-string-using-a-fixed-set-of-letters-in-java
 * 
 * @author 曾东
 * @since 2012-6-27 上午11:04:11
 */
public class NumStrConverter {

    private static final String digit = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";// 62
    private static final int[] digit_indexof_array = _getIndexOfArray(digit);

    public static long _decode(String s, String symbols, int[] indexOfArray) {
        final int B = symbols.length();
        long num = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            num *= B;
            int idx = indexOfArray[ch];
            // if (idx <= 0) {// 可以加上 错误判断
            // throw new IllegalArgumentException("decode string:[" + s + "] ch:" + ch + " is not in symbols' range:[" + symbols + "]");
            // }
            num += idx;

        }
        return num;
    }

    public static int[] _getIndexOfArray(String symbols) {
        int max = 0;
        for (int i = 0; i < symbols.length(); i++) {
            max = Math.max(max, symbols.charAt(i));
        }
        int[] arr = new int[max + 1];
        for (int i = 0; i < symbols.length(); i++) {
            arr[symbols.charAt(i)] = i;
        }
        return arr;
    }

    public static long decode(String s) {
        return _decode(s, digit, digit_indexof_array);
    }

    public static long decode(String s, String symbols) {
        final int B = symbols.length();
        long num = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            num *= B;
            int idx = symbols.indexOf(ch); // indexOf 性能并不好，最好提前通过 index_of_array来映射
            // if (idx <= 0) {
            // throw new IllegalArgumentException("decode string:[" + s + "] ch:" + ch + " is not in symbols' range:[" + symbols + "]");
            // }
            num += idx;
        }
        return num;
    }

    public static String encode(long num) {
        return encode(num, digit);
    }

    /**
     * 注意　不能encode 非正整数
     */
    public static String encode(long num, String symbols) {
        if (num == 0) {
            return "0";
        }
        final int B = symbols.length();
        StringBuilder sb = new StringBuilder();
        while (num != 0) {
            sb.append(symbols.charAt((int) (num % B)));
            num /= B;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        // System.out.println(decode(encode(1234567892392342312L)));
        // System.out.println(encode(1206261408423461520L));

        System.out.println(decode("1R6gmxjc0Yc"));

        // System.out.println(encode(Long.valueOf("1206261408165996720")));
        // System.out.println(decode("AzL8n0Y58m7"));
        //
        // System.out.println(decode("0"));
        // System.out.println(Long.toHexString(-23423));
        // // 不能encode 非正整数
        // System.out.println(encode(0));
        // System.out.println(encode(Long.MIN_VALUE));

    }
}
