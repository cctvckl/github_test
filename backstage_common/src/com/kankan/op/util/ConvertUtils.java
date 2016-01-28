package com.kankan.op.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * 粉丝圈项目常用转换工具集合类
 * @author wangcheng
 *
 */
public class ConvertUtils {
	/**
	 * 将特定字符串按特定格式转换为字符串数组
	 * @param sourceStr
	 * @param pattern
	 * @return
	 */
	public static String[] str2Array(String sourceStr,String pattern) {
		
//		System.out.println(sourceStr);
		if (null == sourceStr) {
			return new String[]{};
		}
		Iterable<String> resultList = Splitter.on(pattern)
                .trimResults()
                .omitEmptyStrings()
                .split(sourceStr);
		List<String> tempList = Lists.newArrayList(resultList);
        String [] rtnArray = new String[tempList.size()];
        tempList.toArray(rtnArray);
        tempList = null;
		return rtnArray;
	}
	/**
	 * 对象的深度拷贝，请调用者保证对象的一致性和合法性
	 * @param srcObj
	 * @return
	 */
	public static Object depthClone(Object srcObj) {
		Object cloneObj = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			
			oo.writeObject(srcObj);
			
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(in);
			cloneObj = oi.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cloneObj;
	}
	
	public static void collectionsDepthClone(List srcList,List destList) {
		for (Object obj : srcList) {
			destList.add(depthClone(obj));
		}
	}
	
	/**
	 * 图片缩略处理模式
	 * http://imgsrc.star.vip.kankan.com/fans/2015/12/10/1/thumb/1449752111183.jpg
	 * http://imgsrc.star.vip.kankan.com/fans/2015/12/10/1/1449752111183.jpg
	 * 原图地址和缩略图地址的差别就是文件名前面多了一级thumb目录
	 * @param imageUrls
	 * @return
	 */
	public static String[] convert2ThumbUrls(String[] imageUrls) {
		int forLen = imageUrls.length;
		
		String[] rtnUrls = new String[forLen];
		for (int i = 0; i < forLen; i++) {
			String parentUrl = imageUrls[i];
			String thumbUrl = parentUrl.substring(0,parentUrl.lastIndexOf("/")) + "/thumb" + parentUrl.substring(parentUrl.lastIndexOf("/"));
//			System.out.println(thumbUrl);
			rtnUrls[i] = thumbUrl;
		}
		
		return rtnUrls;
	}
	
//	public static void main(String[] args) {
//		String[] rtnUrls = {"http://imgsrc.star.vip.kankan.com/fans/2015/12/10/1/1449752111183.jpg"};
//		convert2ThumbUrls(rtnUrls);
//	}
	
}
