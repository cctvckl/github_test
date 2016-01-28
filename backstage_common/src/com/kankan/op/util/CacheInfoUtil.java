package com.kankan.op.util;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 缓存信息工具类
 * 1 查看缓存中信息
 * 2 对比缓存刷新信息
 * @author wangcheng
 *
 */
public class CacheInfoUtil {
	public List<?> beforeData = Lists.newArrayList();
	public List<?> afterData = Lists.newArrayList();
	
	/**刷新前缓存数据条数*/
	public long beforeCount = 0;
	/**刷新后缓存数据条数*/
	public long afterCount = 0;
	
	
}
