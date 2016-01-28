package com.kankan.op.util;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xunlei.httptool.util.RtnError;
import com.xunlei.netty.httpserver.component.XLContextAttachment;
import com.xunlei.netty.httpserver.component.XLHttpRequest;
import com.xunlei.netty.httpserver.component.XLHttpResponse;
import com.xunlei.netty.httpserver.exception.ClosedChannelError;
import com.xunlei.netty.httpserver.exception.ProcessFinishedError;
import com.xunlei.netty.httpserver.handler.TextResponseHandlerManager;
import com.xunlei.spring.Config;
import com.xunlei.util.Log;
import com.xunlei.util.StringTools;
import com.xunlei.util.codec.URICodec;

/**
 * @author ZengDong
 * @since 2011-4-28 下午08:21:49
 */
@Service
public class RedirectHelper {

    protected static final Logger log = Log.getLogger();

    private static RedirectHelper INSTANCE;
    @Config(resetable = true)
    private String proxyUrl = "http://dynamic.xlpan.com/postProxy.html";

    @Autowired
    protected TextResponseHandlerManager localHttpServerResponseHandlerManager;

    private RedirectHelper() {
        INSTANCE = this;
    }

    public static RedirectHelper getInstance() {
        return INSTANCE;
    }

    /**
     * 抛出异常
     */
    public void throwException(XLContextAttachment attach, RtnError e) {
        redirect(attach.getRequest(), attach.getResponse(), e.getJson());
    }

    /**
     * 抛出异常
     */
    public void throwException(XLHttpRequest request, XLHttpResponse response, RtnError e) {
        redirect(request, response, e.getJson());
    }

    /**
     * 对POST请求的结果进行重定向，如果传入的参数带有onlyjson=1则不执行跨域。如果进行了跨域操作，rtnData字段的字符串将被encodeURIComponent
     */
    public void redirect(XLContextAttachment attach, String rtnData) {
        redirect(attach.getRequest(), attach.getResponse(), rtnData);
    }

    /**
     * 对POST请求的结果进行重定向，如果传入的参数带有onlyjson=1则不执行跨域。如果进行了跨域操作，rtnData字段的字符串将被encodeURIComponent
     */
    public void redirect(XLHttpRequest request, XLHttpResponse response, String rtnData) {
        if (request.getMethod() == HttpMethod.GET || request.getParameterBoolean("onlyjson", false) || !request.getParameterBoolean("redirect", true)) {
            _writeResponseToFront(response.getAttach(), rtnData);
        } else {
            String referHost = request.getParameter("proxyUrl");
            // 2012-4-23
            // linnan(林楠) 17:08:37
            // 曾东 家超 post请求我们原来的方式需要有点改动 为了兼容采集那边 原来post请求你们识别一个requestType proxyUrl 现在还得加个documentDomain
            // documentDomain 这个你们不需要理会 只需要在我们响应里边和rtaData一起并行返回就行
            String domain = request.getParameter("documentDomain", "");
            if (StringTools.isEmpty(referHost)) {
                referHost = proxyUrl;
            }
            String requestType = request.getParameter("requestType", "");
            String redirectUrl = referHost + "?requestType=" + requestType + "&documentDomain=" + domain + "&rtnData=" + URICodec.encode(rtnData);
            response.redirect(redirectUrl);
            _writeResponseToFront(response.getAttach(), redirectUrl);
        }
        throw ProcessFinishedError.INSTANCE;
    }

    public void redirectUrl(XLHttpRequest request, XLHttpResponse response, String redirectUrl) {
        response.redirect(redirectUrl);
        _writeResponseToFront(response.getAttach(), redirectUrl);
        throw ProcessFinishedError.INSTANCE;
    }

    /**
     * 回写响应到前端
     * 
     * @param response
     * @param rtnData
     */
    public void writeResponseToFront(XLHttpRequest request, XLHttpResponse response, Object rtnData) {
        _writeResponseToFront(response.getAttach(), rtnData);
    }
    
    /**
     * 回写响应到前端
     */
    private void _writeResponseToFront(XLContextAttachment attach, Object cmdReturnObj) {
        try {
            localHttpServerResponseHandlerManager.writeResponse(attach, cmdReturnObj);
        } catch (ClosedChannelError e) {
            log.error("channelClosed    :{}", attach.getChannelHandlerContext().getChannel());
        }
    }
}
