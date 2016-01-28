package com.kankan.op.util;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xunlei.httptool.util.ParamInvalidError;
import com.xunlei.iptool.IPSegment;
import com.xunlei.iptool.IPTool;
import com.xunlei.netty.httpserver.component.XLContextAttachment;
import com.xunlei.netty.httpserver.component.XLHttpRequest;
import com.xunlei.netty.httpserver.component.XLHttpResponse;
import com.xunlei.spring.AfterConfig;
import com.xunlei.spring.Config;
import com.xunlei.util.Log;

/**
 * @author ZengDong
 * @since 2012-3-1 上午11:34:53
 */
@Service
public class IpUtil {

    protected final Logger log = Log.getLogger(this);

    @Autowired
    private RedirectHelper redirectHelper;
    @Config(resetable = true)
    private int provincefloor = -1;
    @Config(resetable = true)
    private int provinceceil = 35;
    @Config(resetable = true)
    private String provinceexclude = "32,33";
    private int[] provinceExcludeArray;

    @AfterConfig
    public void reCalcProvinceExclude() {
        String[] parts = provinceexclude.split(",");
        int len = parts.length;
        provinceExcludeArray = new int[len];

        for (int i = 0; i < len; i++) {
            try {
                provinceExcludeArray[i] = Integer.parseInt(parts[i]);
            } catch (Exception e) {
                provinceExcludeArray[i] = -1;
                log.error("invalidate provinceexclude string:" + provinceexclude);
            }
        }
    }

    /**
     * 检查用户请求的IP是否是国外的，如果是国外IP抛出rtn=1003
     */
    public void checkForeignIp(XLHttpRequest request, XLHttpResponse response) {
        checkForeignIp(response.getAttach());
    }

    /**
     * 是否要执行检查境外IP
     */
    @Config(resetable = true)
    private boolean check_foreign_ip = true;

    /**
     * 根据传入的IP地址找到对应的IP信息
     */
    public IPSegment getIPSegmentByIP(String ip) {
    	//Add for http://192.168.28.197/zentaopms/www/bug-view-490.html 数据库ip为空，导致入参ip为空，在内层会空指针异常 by caokunliang below
    	if (ip == null){
    		log.error("method parameter<String ip> is null");
    		return null;
    	}
    	//Add for http://192.168.28.197/zentaopms/www/bug-view-490.html 数据库ip为空，导致入参ip为空，在内层会空指针异常  by caokunliang above

        return IPTool.findSegment(ip);
    }

    /**
     * 检查用户请求的IP是否是国外的，如果是国外IP抛出rtn=1003
     */
    public void checkForeignIp(XLContextAttachment attach) {
        if (check_foreign_ip) {
            String ip = attach.getRequest().getParameter("_user_ip", ""); // 由于内容页通过php调用，取不到ip信息，需要php端通过参数传入
            if (ip.isEmpty()) {
                ip = attach.getRequest().getRemoteIP();
            }
            IPSegment ipSeg = IPTool.findSegment(ip);
            if (ipSeg != null) {
                int province = ipSeg.getProvince_id();
                if (province > provincefloor && province < provinceceil) {
                    for (int i : provinceExcludeArray) {
                        if (i == province) {
                            break;
                        }
                    }
                    return;
                }
            }
            throw new ParamInvalidError("境外IP禁止");
        }
    }

    /**
     * 检查用户请求的IP是否是国外的，输出debug信息
     */
    public String checkForeignIpInfo(XLContextAttachment attach) {
        StringBuilder sb = new StringBuilder();
        String ip = attach.getRequest().getParameter("_user_ip", ""); // 由于内容页通过php调用，取不到ip信息，需要php端通过参数传入
        if (ip.isEmpty()) {
            ip = attach.getRequest().getRemoteIP();
        }
        sb.append("你的IP是 ").append(ip).append(" (").append(IPTool.strToLong(ip)).append(")").append("\n");
        IPSegment ipSeg = IPTool.findSegment(ip);
        if (ipSeg != null) {
            sb.append(ipSeg).append("\n");
            int province = ipSeg.getProvince_id();
            if (province > provincefloor && province < provinceceil) {
                for (int i : provinceExcludeArray) {
                    if (i == province) {
                        break;
                    }
                }
            }
        } else {
            sb.append("未查询到该IP相关信息");
        }
        return sb.toString();
    }
}
