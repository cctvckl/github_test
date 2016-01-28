package com.kankan.op.util;

import com.xunlei.netty.httpserver.component.XLHttpResponse;
import com.xunlei.netty.httpserver.component.XLHttpResponse.ContentType;
import com.xunlei.util.StringTools;

/**
 * 用于返回带JS的HTML返回内容的工具
 * 
 * @since 2013-6-18
 * @author hujiachao
 */
public class JSRespUtil {

    private static String escape(String str) {
        return StringTools.escapeString(str.replace("<br/>", "\n").replace("<br />", "\n"));
    }

    /**
     * 在页面上展示一个弹框，用户点确定后页面自动关闭
     */
    public static String alertClose(XLHttpResponse response, String msg, String hidden) {
        String html = "<script type=\"text/javascript\">" //
                + "function closeme()"
                + "{"
                + "    var browserName=navigator.appName;"
                + "    if (browserName==\"Netscape\") {"
                + "        window.open('','_parent','');"
                + "        window.close();"
                + "    } else if (browserName==\"Microsoft Internet Explorer\") {"
                + "        window.opener = \"whocares\";"
                + "        window.close();"
                + "    }"
                + "    window.history.go(-1);"
                + "}"
                + "alert(\""
                + escape(msg)
                + "\");"
                + "closeme();"
                + "var hidden=\""
                + escape(hidden)
                + "\";"
                + "</script>";
        response.setInnerContentType(ContentType.html);
        return html;
    }

    /**
     * 在页面上展示一个弹框，用户点确定后页面回退到上一个
     */
    public static String alertBack(XLHttpResponse response, String msg) {
        String html = "<script type=\"text/javascript\">" //
                + "alert(\""
                + escape(msg)
                + "\");"
                + "history.go(-1);"
                + "</script>";
        response.setInnerContentType(ContentType.html);
        return html;
    }

    /**
     * 在页面上展示一个弹框，用户点确定后页面跳转到指定位置
     */
    public static String alertHref(XLHttpResponse response, String msg, String href) {
        String html = "<script type=\"text/javascript\">" //
                + "alert(\""
                + escape(msg)
                + "\");"
                + "location.href=\""
                + href
                + "\";"
                + "</script>";
        response.setInnerContentType(ContentType.html);
        return html;
    }
}
