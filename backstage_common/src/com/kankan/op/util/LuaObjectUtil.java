package com.kankan.op.util;

import java.util.LinkedHashMap;
import java.util.Map;
import com.xunlei.httptool.util.RtnConstants;
import com.xunlei.util.MapUtil;

/**
 * XMP播放器使用lua返回包,以下是lua通用格式
 * 
 * <pre>
 * 
 * function GetSubTable()
 *     local t = {
 *         iValNum = 0,
 *         sValString = "主角", 
 *         bValBool = true,
 *     }
 *     return t
 * end
 * 
 * 以上是基本格式，其中 布尔型可为 true ,false。字符串编码为 utf-8。
 * 变量名第一个字母表示值类型比较好
 * function GetSubTable()
 *     local t = {
 *         rtn = 0,
 *         data = {
 *            key1 = 1,
 *            key2 = "1",
 *            key3 = true,
 *            key4 = false,
 *            key5 = { ...
 *            },
 *         }
 *     }
 *     return t
 * end
 * 与JSON的区别:
 * 1:key跟value之间用等号=隔开,
 * 2:数组花括号{}
 * 
 * by XMP开发:谭浩
 * </pre>
 * 
 * @author YuanHaoliang
 * @since 2013-7-20
 */
public class LuaObjectUtil {

    public static final String DEFAULT_FUNCTION_NAME = "GetSubTable";

    /**
     * 按正确次序传入 key,value,生成map
     * 
     * @param keyvalue
     * @return Map对象
     */
    public static Map<String, Object> buildMap(Object... keyvalue) {
        return MapUtil.buildMap(new LinkedHashMap<String, Object>(keyvalue.length / 2), keyvalue);
    }

    /**
     * 获得登录态验证通过的Lua
     */
    public static String getOnlyOkLua() {
        return getOnlyRtnLua(RtnConstants.OK);
    }

    /**
     * 获得指定的rtn的Lua
     */
    public static String getOnlyRtnLua(int rtn) {
        return getOnlyRtnLua(rtn, DEFAULT_FUNCTION_NAME);
    }

    /**
     * 获得指定的rtn,functionName的LUA
     */
    public static String getOnlyRtnLua(int rtn, String functionName) {
        return "function " + functionName + "() local t = { rtn = " + rtn + " } return t end";
    }

    /**
     * 获得指定的rtn和指定的data的Lua
     */
    public static String getRtnAndDataLuaObject(int rtn, Object data) {
        return getRtnAndDataLuaObject(rtn, data, DEFAULT_FUNCTION_NAME);
    }

    /**
     * 获得指定的rtn和指定的data和指定functionName的Lua
     */
    public static String getRtnAndDataLuaObject(int rtn, Object data, String functionName) {
        Map<String, Object> object = new LinkedHashMap<String, Object>(2);
        object.put(RtnConstants.rtn, rtn);
        object.put(RtnConstants.data, data);
        String obj = LuaEncoder.encode(object);
        return "function " + functionName + "() local t = " + obj + " return t end";
    }

}
