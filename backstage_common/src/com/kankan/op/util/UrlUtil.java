package com.kankan.op.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.xunlei.util.MapUtil;

/**
 * URL相关的工具类
 * 
 * @author YuanHaoliang
 * @since 2014-2-27
 */
public class UrlUtil {

    private UrlUtil() {
    }

    /**
     * 检测URL是不是xunlei.com/kankan.com/sandai.net域名的链接
     */
    public static boolean checkXLDomain(String url) {
        try {
            String testUrl = url;
            if (!url.trim().toLowerCase().startsWith("http://")) {
                testUrl = "http://" + url;
            }
            URL u = new URL(testUrl);
            String host = u.getHost().toLowerCase();
            if (host.endsWith("xunlei.com") || host.endsWith("kankan.com") || host.endsWith("sandai.net") || host.endsWith("sjzhushou.com")) { // sjzhushou.com是手雷的页面
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 在一个已有的URL上添加参数.
     */
    public static String addParametersToUrl(String url, Object... keyvalue) {
        Map<String, Object> map = MapUtil.buildMap(new LinkedHashMap<String, Object>(keyvalue.length / 2), keyvalue);

        String urlHref = url;
        String anchor = "";

        // 如果有锚点,先取出,后补.
        int anchorPos = url.indexOf("#");
        if (anchorPos > 0) {
            urlHref = url.substring(0, anchorPos);
            anchor = url.substring(anchorPos);
        }

        boolean first = true;// 用于第一次判断链接上是否已有问号

        for (Entry<String, Object> e : map.entrySet()) {
            if (first && urlHref.indexOf("?") < 0) {
                // 如果是第一个参数,然后链接中没有含有问号,那要补问号
                try {
                    urlHref += "?" + e.getKey() + "=" + URLEncoder.encode(e.getValue().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                }
            } else {
                try {
                    urlHref += "&" + e.getKey() + "=" + URLEncoder.encode(e.getValue().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                }
            }
            first = false;
        }

        return urlHref += anchor;
    }
}
