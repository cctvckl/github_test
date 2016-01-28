package com.kankan.op.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import com.xunlei.proxy.HttpClientUtil;
import com.xunlei.spring.Config;
import com.xunlei.util.CollectionUtil;
import com.xunlei.util.Log;
import com.xunlei.util.StringTools;
import com.xunlei.util.codec.URICodec;

/**
 * <pre>
 * 
 * 手机业务帮助类
 * 
 * 1.调用支付网关发送手机短信
 * 2.验证手机号码对应的运营商
 * 
 * @author huqixin
 * @since 2015-10-12
 */
@Service
public final class SmsHelper {

    @Config(resetable = true)
    private static String paySmsVersion = "v1.0";
    @Config(resetable = true)
    private static String paySmsBizNoNew = "000001";
    @Config(resetable = true)
    private static String paySmsBizKeyNew = "qawsedrftgyhplok";
    @Config(resetable = true)
    private static String paySmsServerUrlRoot = "http://sms.pay.kankan.com/";

    private static final Logger LOG = Log.getLogger();

    /** 3 中国电信号码段 **/
    private static Set<String> CHINA_TELECOM_PREFIX_SET = CollectionUtil.buildSet("133", "153", "180", "181", "189");
    /** 2 中国联通号码段 **/
    private static Set<String> CHINA_UNICOM_PREFIX_SET = CollectionUtil.buildSet("130", "131", "132", "155", "156", "185", "186", "145");
    /** 1 中国移动号码段 **/
    private static Set<String> CHINA_MOBILE_PREFIX_SET = CollectionUtil.buildSet("139", "138", "137", "136", "135", "134", "159", "158", "157", "152", "151", "150", "187", "188", "182", "183", "184",
            "147");

    /**
     * <pre>
     * 三大运营商：中国移动、中国联通和中国电信
     * 
     * 中国移动号码段为：139、138、137、136、135、134、159、158、157、152、151、150、187、188、182、183、184、147
     * 中国联通号码段为：130、131、132、155、156、185、186、145
     * 中国电信号码段为：133、153、180、181、189
     * 
     * 返回码：
     * 0-非三大运营商
     * 1-中国移动
     * 2-中国联通
     * 3-中国电信
     */
    public static byte checkMobileOperator(String mobileNo) {
        byte result = 0;
        if (StringTools.isNotEmpty(mobileNo) && mobileNo.length() > 3 && verifyMobileNo(mobileNo)) {
            String prefix = mobileNo.substring(0, 3);
            if (StringTools.isNotEmpty(prefix)) {
                // 1.中国移动
                if (CHINA_MOBILE_PREFIX_SET.contains(prefix)) {
                    result = 1;
                    // 2.中国联通
                } else if (CHINA_UNICOM_PREFIX_SET.contains(prefix)) {
                    result = 2;
                    // 3.中国电信
                } else if (CHINA_TELECOM_PREFIX_SET.contains(prefix)) {
                    result = 3;
                }
            }
        }
        return result;
    }

    /**
     * 用正则表达式验证手机号码是否合法
     */
    public static boolean verifyMobileNo(String mobileNo) {
        boolean result = false;
        if (StringTools.isNotEmpty(mobileNo)) {
            // 13xx 15xx 18xx 14xx
            Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobileNo);
            result = m.matches();
        }
        return result;
    }

    /**
     * 根据mobileNo发送手机短信，支持批量
     */
    public static int sendMobileMessage(List<String> mobileNoList, String messageContent) {
        int succeed = 0;
        for (String mobileNo : mobileNoList) {
            if (StringTools.isNotEmpty(mobileNo) && verifyMobileNo(mobileNo)) {
                String url = buildComplelledUrl(mobileNo, messageContent);// 构建请求参数
                String callback = HttpClientUtil.httpGet(url);// 请求支付网关接口
                if (StringTools.isNotEmpty(callback)) {// 解析返回参数
                    SAXReader reader = new SAXReader();
                    StringReader re = new StringReader(callback);
                    try {
                        Document doc = reader.read(re);
                        String sendresult = "", errcode = "";
                        if (doc != null) {
                            Element root = doc.getRootElement();
                            sendresult = root.elementTextTrim("sendresult");
                            errcode = root.elementTextTrim("errcode");
                            LOG.debug("send mobileNo : {}, sendresult : {}, errcode : {} detail :{}", mobileNo, sendresult, errcode, messageContent);
                        }
                        if (StringTools.isNotEmpty(sendresult) && sendresult.equals("00")) {
                            succeed++;
                        }
                    } catch (DocumentException e) {
                        throw new IllegalArgumentException("支付网关返回的callback文件解析出错！！！");
                    } finally {
                        re.close();
                    }
                }
            }
        }
        return succeed;
    }

    /**
     * 根据mobileNo发送手机短信
     */
    public static int sendMobileMessage(String mobileNo, String messageContent) {
        return sendMobileMessage(CollectionUtil.buildList(mobileNo), messageContent);
    }

    /**
     * 构造请求支付网关发送手机短信URL地址
     */
    private static String buildComplelledUrl(String mobileNo, String content) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("version", paySmsVersion);
        params.put("pageCharset", "GBK");// 一定使用GBK编码方式
        params.put("bizNo", paySmsBizNoNew);
        params.put("mobile", mobileNo);
        params.put("content", content);
        String signMsg = SignatureHelper.sign(params, paySmsBizKeyNew, "GBK");// 一定使用GBK编码方式处理
        return new StringBuilder(paySmsServerUrlRoot).append("sendsms?version=").append(URICodec.encode(paySmsVersion)).append("&pageCharset=").append(URICodec.encode("GBK")).append("&bizNo=")
                .append(URICodec.encode(paySmsBizNoNew)).append("&mobile=").append(mobileNo).append("&content=").append(URICodec.encode(content)).append("&signMsg=").append(signMsg).toString();
    }

}
