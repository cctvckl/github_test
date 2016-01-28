package com.kankan.op.util;

import java.lang.management.ManagementFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.kankan.star.bean.User;
import com.xunlei.logback.LogFormatFactory;
import com.xunlei.spring.AfterBootstrap;
import com.xunlei.spring.BeanUtil;
import com.xunlei.spring.Config;
import com.xunlei.springutil.MailTemplate;
import com.xunlei.util.EmptyChecker;
import com.xunlei.util.Log;
import com.xunlei.util.ValidateUtil;
import com.xunlei.util.concurrent.ConcurrentUtil;

/**
 * 邮件通知机制
 * 
 * @since 2013-8-5
 * @author hujiachao
 */
@Service
public class Notifier {

    private static Notifier INSTANCE;

    private Notifier() {
        INSTANCE = this;
    }

    /** 需要发邮件提醒的log */
    private static Logger alarmLog = Log.getLoggerWithPrefix("alarm");
    private static final Logger log = Log.getLogger();

    /** 发送EDM邮件预备统计的数据 */
    private static Logger statLog = Log.getLoggerWithPrefix("stat");
    public static LogFormatFactory logFormat = LogFormatFactory.getInstance("|");

    @Config(resetable = true)
    private String mail_emplate_newuser = "usrmail/newuser.vm"; // 邮件模板
    @Config(resetable = true)
    private String mail_emplate_xfmail = "usrmail/xfmail.vm"; // 邮件模板
    @Config(resetable = true)
    private String mail_emplate_upgrade = "usrmail/upgrade.vm"; // 邮件模板
    private MailTemplate mailTemplateStar;
    @Autowired
    private SeparatedTableUtil separatedTableUtil;

    @AfterBootstrap
    public void init() {
        mailTemplateStar = BeanUtil.getTypedBeanSilently("mailTemplateStar");
    }

    /**
     * 发送报警邮件
     */
    public static void alarm(String title, String content) {
        MDC.put("mailTitle", "[警告]" + title);
        alarmLog.error(title + " |" + ManagementFactory.getRuntimeMXBean().getName() + "\n" + content + "\n");
    }

    /** 需要发邮件通知的log */
    private static Logger noticeLog = Log.getLoggerWithPrefix("notice");

    /**
     * 发送一般通知邮件
     */
    public static void notice(String title, String content) {
        MDC.put("mailTitle", "[通知]" + title);
        noticeLog.error(title + " |" + ManagementFactory.getRuntimeMXBean().getName() + "\n" + content + "\n");
    }

    /** 需要发邮件报告的log */
    private static Logger reportLog = Log.getLoggerWithPrefix("report");

    /**
     * 发送报告邮件，如定时扫描的结果等
     */
    public static void report(String title, String content) {
        MDC.put("mailTitle", "[报告]" + title);
        if (content.isEmpty() || content.length() < 10) {
            reportLog.error(title + " |" + ManagementFactory.getRuntimeMXBean().getName() + "\n" + content + "\n");
        } else {
            reportLog.error(content + "|" + ManagementFactory.getRuntimeMXBean().getName() + "\n");
        }
    }

    /** 需要发邮件报告的log，报告的重要性相对较低 */
    private static Logger reportGeneralLog = Log.getLoggerWithPrefix("reportGeneral");

    /**
     * 发送报告邮件，如定时扫描的结果等
     */
    public static void reportGeneral(String title, String content) {
        MDC.put("mailTitle", "[报告]" + title);
        if (content.isEmpty() || content.length() < 10) {
            reportGeneralLog.error(title + " |" + ManagementFactory.getRuntimeMXBean().getName() + "\n" + content + "\n");
        } else {
            reportGeneralLog.error(content + "|" + ManagementFactory.getRuntimeMXBean().getName() + "\n");
        }
    }

    /**
     * 发送会员报名探班团邮件（zhangqi@kankan.com）
     * 
     * @param to 收件人
     * @param subject 主题
     * @param content 正文
     */
    public static void sendVipSignUpEmail(String[] to, String subject, String content) {
        try {
            INSTANCE.mailTemplateStar.sendMimeMail(to, subject, content);
        } catch (Exception e) {
            log.info("send vip sign up email fail!", e);
        }
    }

}
