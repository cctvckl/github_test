package com.kankan.op.util;

import java.util.LinkedHashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xunlei.httptool.util.RtnConstants;

/**
 * 使用fastjson序列化对象的工具类
 * 
 * @author ywy
 */
public class FastJsonFormatUtil {

    /**
     * 把值为null的字符串转换为空字符""
     */
    public static final SerializerFeature[] stringAsEmpty = {
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullStringAsEmpty
    };

    /**
     * 序列化成JSON格式
     * 
     * @param rtn 返回码
     * @param data 返回数据
     * @return String
     */
    public static String toJsonString(int rtn, Object data) {
        Map<String, Object> object = new LinkedHashMap<String, Object>(2);
        object.put(RtnConstants.rtn, rtn);
        object.put(RtnConstants.data, data);
        return JSON.toJSONString(object, stringAsEmpty);
    }
    
}
