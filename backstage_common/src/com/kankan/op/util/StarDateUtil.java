package com.kankan.op.util;

import java.util.Calendar;
import java.util.Date;
import com.xunlei.util.DateStringUtil;
import com.xunlei.util.DateUtils;
import com.xunlei.util.StringTools;

/**
 * @author ZengDong
 * @since 2011-3-29 下午04:37:58
 */
public class StarDateUtil {

    private static final Date nullDate = new Date(0);
    public static final DateStringUtil dsuday = DateStringUtil.DEFAULT_DAY;
    public static final DateStringUtil dsu = DateStringUtil.DEFAULT;

    public static final Date parse(String str) {
        return StringTools.isEmpty(str) ? nullDate : str.length() == 10 ? dsuday.parse(str) : dsu.parse(str);
    }

    /**
     * 返回今天凌晨0点0分0秒时间戳
     */
    public static final long today0000() {
        return DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).getTime();
    }

    public static final Date todayYMD() {
        return DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
    }
}
