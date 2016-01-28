package com.kankan.op.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.slf4j.Logger;
import com.xunlei.httptool.util.ParamInvalidError;
import com.xunlei.util.DateStringUtil;
import com.xunlei.util.DateUtil;
import com.xunlei.util.DateUtilHelper;
import com.xunlei.util.Log;
import com.xunlei.util.StringTools;
import com.xunlei.util.UnitConverter;
import com.xunlei.util.UnitConverter.ByteUnit;

/**
 * 公用Util类
 * 
 * @since 2013-6-13
 * @author hujiachao
 */
public class Miscellaneous {

    public static final Logger log = Log.getLogger();
    public static final char[] charList = {
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z'
    };
    public static final char[] randomList = {
        'o',
        'C',
        'v',
        'q',
        'I',
        'p',
        'z',
        'n',
        'Q',
        'j',
        's',
        'h',
        'x',
        'e',
        'H',
        'c',
        'P',
        'O',
        'N',
        'F',
        'm',
        'a',
        'B',
        'W',
        'S',
        'u',
        'U',
        'r',
        'Y',
        'J',
        'K',
        'Z',
        'k',
        'i',
        'R',
        'A',
        't',
        'w',
        'T',
        'D',
        'E',
        'b',
        'g',
        'G',
        'd',
        'f',
        'X',
        'V',
        'y',
        'M',
        'L',
        'l'
    };

    /**
     * 生成指定长度的随机字符串
     */
    public static String randomString(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(charList[rand.nextInt(charList.length)]);
        }
        return sb.toString();
    }

    /**
     * <pre>
     * 计算userid的签名，防止userid被恶意伪造
     * 
     * 此签名用于给用户发送的邮件中的问卷调查接口，由于邮件中无法带上登录态，
     * 进入问卷调查页面时， 为了能识别是这个用户提交的调查内容，需要附带上userid，
     * 但纯数字的id容易被伪造，故加入此简易签名算法来加强安全性
     * </pre>
     */
    public static String calcSign(long userid) {
        StringBuilder sb = new StringBuilder();
        long n = userid;
        while (n > 0) {
            sb.append(randomList[(int) (n % randomList.length)]);
            n /= charList.length;
        }
        return sb.toString();
    }

    /**
     * 获取现在的时间
     */
    public static String now() {
        return DateStringUtil.DEFAULT.now();
    }

    /**
     * 判断给定的时间是否已经距离现在超过了timeOutSeconds秒，注意给定时间一定要比当前时间小
     * 
     * @return 特殊情况：timeOutSeconds<=0返回false；date为空或者时间有误返回true
     */
    public static boolean isTimeOut(String date, long timeOutSeconds) {
        if (timeOutSeconds <= 0) {
            return false;
        }
        if (StringTools.isEmpty(date)) {
            return true;
        }
        DateFormat df = DateUtil.DEFAULT_DF_FACOTRY.get();
        df.setLenient(false);
        try {
            if (System.currentTimeMillis() - df.parse(date).getTime() > timeOutSeconds * 1000) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 获取现在时间之前若干秒的时间
     */
    public static String beforeNow(long second) {
        return DateStringUtil.DEFAULT.beforeNow(second);
    }

    /**
     * 获取现在时间之后若干秒的时间
     */
    public static String afterNow(long second) {
        return DateStringUtil.DEFAULT.afterNow(second);
    }

    /**
     * 获取当前指定时间月份指定数字之后的时间
     * 
     * @param date 格式 2010-01-01 10:10:10
     * @param num
     * @return
     * @throws ParseException
     */
    public static String getDateAfterNum(String date, int num) {
        return DateStringUtil.DEFAULT.addDays(date, num);
    }

    /**
     * 计算当前日期下个月最后一天
     * 
     * @param date
     * @return
     */
    public static String getDateAfterMonth(Date date) {
        return DateStringUtil.DEFAULT.oper(new Date(), DateUtilHelper.set(Calendar.DAY_OF_MONTH, 1), DateUtilHelper.add(Calendar.MONTH, 2), DateUtilHelper.add(Calendar.DAY_OF_MONTH, -1));
    }

    /**
     * 随机生成激活码
     * 
     * @return
     */
    public static String generateActiveCode() {
        String activeCode = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
        StringBuilder generateRandStr = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int randNum = rand.nextInt(activeCode.length());
            generateRandStr.append(activeCode.substring(randNum, randNum + 1));
        }
        return generateRandStr.toString();
    }

    private static final DateStringUtil DSU_yyyyMMdd = DateStringUtil.getInstance(DateUtil.DF_yyyyMMdd);

    /**
     * 获取指定数字前一天日期 yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static String getBeforeDate(Date date, int num) {
        return DSU_yyyyMMdd.addDays(date, -num);
    }

    /**
     * 所有有关文件大小的 请求,都统一从这里 转换
     */
    public static long getFileSize(long bytes) {
        return UnitConverter.convertByteCeil(bytes, ByteUnit.b, ByteUnit.kb);
    }

    public static String trimRefId(String refId) {
        int maxLen = 20;// 2010-12-10 最长20,超过的情况下是页面定义问题
        if (refId.length() > maxLen) {
            log.error("refId len too long:{}", refId);
            return refId.substring(0, maxLen);
        }
        return refId;
    }

    /**
     * 根据券编号查询分表
     */
    public static int getTableIndexByTid(String ticketId) {
        try {
            return Integer.parseInt(ticketId.substring(18, 20));
        } catch (Exception e) {
            throw ParamInvalidError.INSTANCE;
        }
    }

    /**
     * 根据卡密号查询分表
     */
    public static int getTableIndexByTid4CardCode(String ticketId) {
        try {
            return Integer.parseInt(ticketId.substring(0, 2));
        } catch (Exception e) {
            throw ParamInvalidError.INSTANCE;
        }
    }

    public static String replaceChar(String src, char dest, int begin, int end) {
        if (StringTools.isEmpty(src)) {
            return "";
        }
        char[] ch = src.toCharArray();
        for (int j = begin; j < end; j++) {
            ch[j] = '*';
        }
        return src = new String(ch);
    }
}
