package com.kankan.op.util;

import java.text.SimpleDateFormat;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.slf4j.Logger;
import com.xunlei.util.Log;

/**
 * @author ZengDong
 * @since 2011-3-7 下午08:40:31
 */
public class JsonObjectUtilForThrift {

    public static final ObjectMapper ENHANCE_MAPPER;
    private static final Logger log = Log.getLogger();
    static {
        ENHANCE_MAPPER = new ObjectMapper();
        SerializationConfig sc = ENHANCE_MAPPER.getSerializationConfig();
        sc.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 这里 在fromObject时，为了不改动thrift的java文件情况下，能只打印必须的字段，而进行特殊设置
        // isGetter不要，getter也不要，只要proteced或public的字段
        ENHANCE_MAPPER.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withIsGetterVisibility(Visibility.NONE).withGetterVisibility(Visibility.NONE)
                .withFieldVisibility(Visibility.PROTECTED_AND_PUBLIC));

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
        return JsonObjectUtilForThrift.fromObject(data);
    }

    /**
     * 为云短信特地开的接口，云短信上传文件数据到成刚那边需要userid，为了兼容旧接口，云短信调用addFileForClient接口时同时返回超级userid
     * 
     * @param rtn
     * @param data
     * @param userId
     * @return
     */
    public static String getRtnAndDataJsonObject(int rtn, Object data, long userId) {
        return "{\"rtn\":" + rtn + ",\"data\":" + JsonObjectUtilForThrift.fromObject(data) + ",\"userId\":" + userId + "}";
    }

    public static String getRtnAndDataJsonObject(int rtn, Object data) {
        return "{\"rtn\":" + rtn + ",\"data\":" + JsonObjectUtilForThrift.fromObject(data) + "}";
    }
}
