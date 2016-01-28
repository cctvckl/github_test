package com.kankan.op.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.xunlei.util.StringTools;
import com.xunlei.util.codec.DigestUtils;
import com.xunlei.util.codec.URICodec;

/**
 * <pre>
 * 
 * 验证帮助类
 * 
 * 1.根据参数进行md5加密
 * 2.根据参数拼接请求URL地址
 * 
 * @author HuXiusong
 * @since 2013-8-7
 */
@Service
public class SignatureHelper {

    /**
     * 根据参数和密钥返回md5加密后验证码
     */
    public static String sign(Map<String, String> param, String privateKey, String charsetName) {
        String content = getSignatureContent(param, null);
        return sign(content, privateKey, charsetName);
    }

    /**
     * 根据参数和密钥返回md5加密后验证码
     */
    public static String sign(String content, String privateKey, String charsetName) {
        if (content == null) {
            return "";
        }
        return DigestUtils.md5Hex(content + privateKey, Charset.forName(charsetName));
    }

    /**
     * <pre>
     * 1.对除签名验证码外的参数进行排序
     * 2.根据参数构造请求参数地址返回
     */
    public static String getSignatureContent(Map<String, String> param, String encodeCharset) {
        Map<String, String> prop = new ConcurrentHashMap<String, String>();
        for (String key : param.keySet()) {
            if (StringTools.isNotEmpty(key) && !key.equals("bizKey")) {
                String value = param.get(key);
                if (StringTools.isNotEmpty(value)) {
                    prop.put(key, value);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<String>(prop.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = prop.get(key);
            if (StringTools.isNotEmpty(encodeCharset)) {
                value = URICodec.encode(value, Charset.forName(encodeCharset));
            }
            sb.append(i == 0 ? "" : "&");
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

}
