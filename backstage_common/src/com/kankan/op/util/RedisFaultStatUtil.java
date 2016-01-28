package com.kankan.op.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import com.xunlei.spring.AfterBootstrap;
import com.xunlei.spring.Config;
import com.xunlei.util.Log;
import com.xunlei.util.SystemMonitor;
import com.xunlei.util.concurrent.BaseSchedulable;

/**
 * 服务器请求redis情况监控
 * 
 * @since 2014-7-22
 * @author hujiachao
 */
@Service
public class RedisFaultStatUtil {

    public static class RedisFaultStat {

        private String name;
        /** 请求数 */
        private AtomicLong submit = new AtomicLong();
        /** 请求失败数 */
        private AtomicLong fail = new AtomicLong();
        /** 累计失败数，不清零的 */
        private AtomicLong failSum = new AtomicLong();

        public RedisFaultStat(String name) {
            this.name = name;
        }

        public void recordSubmit() {
            submit.incrementAndGet();
        }

        public void recordFail() {
            fail.incrementAndGet();
            failSum.incrementAndGet();
        }

        public int getFailRate() {
            long f = fail.get();
            long s = submit.get();
            return (int) (s > 0 ? (f * 100 / s) : 0);
        }

        public String info() {
            return String.format(fmt, name, submit.get(), fail.get(), failSum.get(), getFailRate());
        }

        private void reset() {
            submit.set(0);
            fail.set(0);
        }
    }

    private static Map<String, RedisFaultStat> stat = new ConcurrentHashMap<>();

    public static RedisFaultStat add(String name) {
        RedisFaultStat fs = new RedisFaultStat(name);
        stat.put(name, fs);
        return fs;
    }

    @Config(resetable = true)
    private float redisFaultStatWarningRate = 1; // 在10分钟统计范围内，如果失败率超过一定概率，就要报邮件
    @Config(resetable = true)
    private int redisFaultStatWarningNum = 500; // 在10分钟统计范围内，如果失败个数超过一定数目，就需要报邮件
    @Config(resetable = true)
    private int redisFaultStatCheckSec = 10 * 60; // 10分钟统计一次

    private static String fmt = "%-50s %-19s %-19s %-19s %-10s%%\n";

    public static String stat() {
        StringBuilder sb = new StringBuilder();
        for (RedisFaultStat fs : stat.values()) {
            sb.append(fs.info());
        }
        sb.insert(0, String.format(fmt, "NAME", "SUBMIT", "FAIL", "FAILSUM", "FAILRATE"));
        return sb.toString();
    }

    @AfterBootstrap
    private void initSche() {
        faultStatSchedulaber.scheduleWithFixedDelaySec(redisFaultStatCheckSec);
    }

    private static final Logger log_monitor = Log.getLogger(SystemMonitor.class);
    private BaseSchedulable faultStatSchedulaber = new BaseSchedulable() {

        @Override
        public void process() throws Throwable {
            boolean needAlarm = false;
            StringBuilder sb = new StringBuilder();
            int failRate = 0;
            for (RedisFaultStat fs : stat.values()) {
                if (fs.getFailRate() > redisFaultStatWarningRate || fs.fail.get() > redisFaultStatWarningNum) { // 满足条件，就需要发邮件报警
                    needAlarm = true;
                    sb.append(fs.info());
                    if (fs.getFailRate() > failRate) {
                        failRate = fs.getFailRate();
                    }
                }
                fs.reset();
            }
            if (needAlarm) {
                sb.insert(0, String.format(fmt, "NAME", "SUBMIT", "FAIL", "FAILSUM", "FAILRATE"));
                MDC.put("mailTitle", "redis请求失败告警");
                String content = "最大失败率" + failRate + "%\n" + sb.toString();
                log_monitor.error(content);
            }
        }
    };
}
