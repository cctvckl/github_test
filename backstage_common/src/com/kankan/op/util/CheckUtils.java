package com.kankan.op.util;

import java.util.Arrays;

/**
 * 校验工具类
 * @author wangcheng
 * @Date   2015年12月14日
 *
 */
public class CheckUtils {
	/**
	 * 整型负数校验
	 * @param param
	 * @return
	 */
	public static boolean isNegative(int param) {
		return param < 0 ? true : false;
	}
	
	/**
	 * 长整型负数校验
	 * @param param
	 * @return
	 */
	public static boolean isNegative(long param) {
		return param < 0 ? true : false;
	}
	
	/**
	 * 正数检查
	 * @param param
	 * @return
	 */
	public static boolean isPositive(int param) {
		return param > 0 ? true : false;
	}
	
	/**
	 * 非正检查
	 * @param param
	 * @return
	 */
	public static boolean isNotPositive(int param) {
		return param <= 0 ? true : false;
	}
	
	/**
	 * 长整型正数检查
	 * @param param
	 * @return
	 */
	public static boolean isPositive(long param) {
		return param > 0 ? true : false;
	}
	
	/**
	 * 长整型非正检查
	 * @param param
	 * @return
	 */
	public static boolean isNotPositive(long param) {
		return param <= 0 ? true : false;
	}
	
	/**
	 * 0检查
	 * @param param
	 * @return
	 */
	public static boolean isZero(int param) {
		return param == 0 ? true : false;
	}
	/**
	 * 非0检查
	 * @param param
	 * @return
	 */
	public static boolean isNotZero(int param) {
		return param != 0 ? true : false;
	}
	
	/**
	 * 长整型0检查
	 * @param param
	 * @return
	 */
	public static boolean isZero(long param) {
		return param == 0 ? true : false;
	}
	/**
	 * 长整型非0检查
	 * @param param
	 * @return
	 */
	public static boolean isNotZero(long param) {
		return param != 0 ? true : false;
	}
	
	public static boolean isEmptyStr(String param) {
	    return isNull(param) || "".equals(param);
	}
	
	/**
	 * 字符串判空
	 * @param param
	 * @return
	 */
	public static boolean isNull(String param) {
		return null == param ? true : false;
	}
	
	/**
	 * 字符串非空判断
	 * @param param
	 * @return
	 */
	public static boolean isNotNull(String param) {
		return null != param ? true : false;
	}
	
	/**
	 * 校验某个对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return null == obj ? true : false;
	}
	
	/**
	 * 校验某个对象不为空
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return null != obj ? true : false;
	}
	/**
	 * 校验输入的字符串是否为null，如果为null，则转换为""
	 * @param param
	 * @return
	 */
	public static String checkNull(String param) {
		return param == null ? "" : param;
	}
	
	/**
	 * 校验是否是空数组
	 * @param param
	 * @return
	 */
	public static String[] checkNull(String[] param) {
		return param == null ? new String[]{}: param;
	}
	
	/**
	 * 校验参数是否是 0 或 1
	 * @param param
	 * @return
	 */
	public static boolean isBinary(int param) {
		return param >> 1 == 0 ? true : false;
	}
	
	/**
	 * 校验参数是否不是 0 或 1
	 * @param param
	 * @return
	 */
	public static boolean isNotBinary(int param) {
		return param >> 1 == 0 ? false : true;
	}
	
	/**
	 * 校验某个字符串长度是否大于
	 * @param str
	 * @param length 不包含上限
	 * @return
	 */
	public static boolean strMoreLen(String str, int length) {
		return str.length() > length ? true : false;
	}
	
	/**
	 * 校验某个字符串长度是否小于
	 * @param str
	 * @param length 不包含下限
	 * @return
	 */
	public static boolean strLessLen(String str, int length) {
		return str.length() < length ? true : false;
	}

	/**
	 * 判断param是否在values里
	 * @param param
	 * @param values
	 * @return
	 */
	public static boolean paramIn(Object param, Object... values) {
		return Arrays.asList(values).contains(param);
	}
	
	/**
	 * 判断param是否不在values里
	 * @param param
	 * @param values
	 * @return
	 */
	public static boolean paramNotIn(Object param, Object... values) {
		return !Arrays.asList(values).contains(param);
	}
	
	public static void main(String[] args) {
//		System.out.println(isBinary(2));
//		System.out.println(isBinary(-3));
//		System.out.println(isBinary(Integer.MAX_VALUE));
//		System.out.println(isBinary(Integer.MIN_VALUE));
//		System.out.println(isBinary(-0));
//		System.out.println(isBinary(-1));
//		System.out.println(isBinary(0));
//		System.out.println(isBinary(1));
		
	}
}
