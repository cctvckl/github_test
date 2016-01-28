package com.kankan.op.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.slf4j.Logger;
import com.xunlei.httptool.util.JsonObjectUtil;
import com.xunlei.httptool.util.RtnConstants;
import com.xunlei.netty.httpserver.component.XLHttpRequest;
import com.xunlei.util.Log;

/**
 * @author ZengDong
 * @since 2011-3-7 下午08:40:31
 */
public class JsonObjectUtilExt {

    public static final ObjectMapper ENHANCE_MAPPER;
    private static final Logger log = Log.getLogger();
    static {
        ENHANCE_MAPPER = new ObjectMapper();
        SerializationConfig sc = ENHANCE_MAPPER.getSerializationConfig();
        sc.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        ENHANCE_MAPPER.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withGetterVisibility(Visibility.NON_PRIVATE));
    }

    public static String fromObject(Object obj) {
        try {
            return ENHANCE_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("", e);
        }
        return "{}";
    }

    public static String getDataJsonObject(Object data) {
        return JsonObjectUtilExt.fromObject(data);
    }

    public static String getRtnAndDataJsonArray(int rtn, List<Map<String, Object>> list) {
        Map<String, Object> object = new HashMap<String, Object>(2);
        object.put(RtnConstants.rtn, rtn);
        object.put(RtnConstants.data, list);
        return JsonObjectUtilExt.fromObject(object);
    }

    public static String getRtnAndDataJsonObject(int rtn, Map<String, Object> data) {
        Map<String, Object> object = new HashMap<String, Object>(2);
        object.put(RtnConstants.rtn, rtn);
        object.put(RtnConstants.data, data);
        return JsonObjectUtilExt.fromObject(object);
    }

    public static String getRtnAndDataJsonObject(int rtn, Object data) {
        Map<String, Object> object = new HashMap<String, Object>(2);
        object.put(RtnConstants.rtn, rtn);
        object.put(RtnConstants.data, data);
        return JsonObjectUtilExt.fromObject(object);
    }

    public static String getRtnAndDataJsonObject(XLHttpRequest request, int rtn, Map<String, Object> data) {
        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK, data);
    }
}
