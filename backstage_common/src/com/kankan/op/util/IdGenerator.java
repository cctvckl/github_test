package com.kankan.op.util;

import java.util.Random;
import com.xunlei.util.TimebasedIdGenerator;

public class IdGenerator {

    /**
     * <pre>
     * 支付订单(19)           yyyyMMddHHmmss + milliTime(3位) + userId后两位
     * 购买商品订单(19)       yyyyMMddHHmmss + milliTime(3位) + userId后两位
     * 兑换券 (20)            "T" + yyyyMMddHHmmss + milliTime(3位) + userId后两位
     * 用户子帐号(19)         "1"/"2" + yyMMddHHmmss + 精确秒数后4位  + userId后两位
     * </pre>
     */
    public static final TimebasedIdGenerator[] timebasedIdGenerator_A = new TimebasedIdGenerator[100];// 按userId % 100 分成100个生成器,提高并发性 -> 10w并发
    public static final TimebasedIdGenerator[] timebasedIdGenerator_B = new TimebasedIdGenerator[100];// 100w并发
    public static final long VIP_ACCOUNT_BASE = TimebasedIdGenerator.DECIMAL_SHIFT_BASE[18];
    public static final long BERYL_ACCOUNT_BASE = 2 * TimebasedIdGenerator.DECIMAL_SHIFT_BASE[18];
    public static final char[] CHARACTER = {
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
        'z'
    };
    public static final Random rand = new Random();
    static {
        for (int i = 0; i < 100; i++) {
            timebasedIdGenerator_A[i] = new TimebasedIdGenerator(3, true);
            timebasedIdGenerator_B[i] = new TimebasedIdGenerator(4, false);
        }
    }

    private static final long accountVipPrefix = (long) Math.pow(10, 18);
    private static final long accountRechargePrefix = 2 * accountVipPrefix;

    private static long nextIdA(long userId) {
        int idx = (int) (userId % 100);
        return timebasedIdGenerator_A[idx].nextLongId() * 100 + idx;
    }

    private static long nextIdB(long userId) {
        int idx = (int) (userId % 100);
        return timebasedIdGenerator_B[idx].nextLongId() * 100 + idx;
    }

    /**
     * 给支付的订单id
     */
    public static long getPayOrderId(long userId) {
        return nextIdA(userId);
    }

    /**
     * 购买商品的id
     */
    public static long getOrderId(long userId) {
        return nextIdA(userId);
    }

    /**
     * 明星发表的动态id
     */
    public static long getDynamicId(long userId) {
        return nextIdA(userId);
    }

    /**
     * 消息通知的消息编号
     * 
     * @param userId
     * @return long
     */
    public static long getMessageId(long userId) {
        return nextIdA(userId);
    }
    
    /**
     * 单纯获取不重复的ID
     */
    public static long getId() {
        return timebasedIdGenerator_A[0].nextLongId();
    }

    /**
     * 生成vip会员的子账号
     */
    public static long getAccountVip(long userId) {
        return accountVipPrefix + nextIdB(userId);
    }

    /**
     * 生成非vip会员的子账号
     */
    public static long getAccountRecharge(long userId) {
        return accountRechargePrefix + nextIdB(userId);
    }

    /**
     * 是否vip会员账号
     * 
     * @return
     */
    public static boolean isVipAccount(long userAccount) {
        return userAccount < BERYL_ACCOUNT_BASE;
    }

    /**
     * 是否充值看看会员帐户
     * 
     * @param userAccount
     * @return
     */
    public static boolean isBerylAccount(long userAccount) {
        return !isVipAccount(userAccount);
    }

    /**
     * 生成券编号
     * 
     * @return
     */
    public static String getTicketNo(long userId) {
        return getTicketNo("T", userId);
    }

    /**
     * 生成游离券编号
     * 
     * @return
     */
    public static String getExtricatedTicketNo(long userId) {
        return getTicketNo("E", userId);
    }

    private static String getTicketNo(String prefix, long userId) {
        int len = 4;
        char[] arr = new char[len];
        for (int i = 0; i < len; i++) {
            arr[i] = CHARACTER[rand.nextInt(26)];
        }
        return prefix + nextIdA(userId) + new String(arr);
    }
    
    /**
     * 生成评论ID
     * @param userId
     * @return
     */
    public static long getCommentId(long userId) {
    	return nextIdA(userId);
    }
    
    
    
}
