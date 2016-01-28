package com.kankan.op.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.xunlei.spring.AfterConfig;
import com.xunlei.util.ValueUtil;

@Service
public class UserLogProperties {

    /**
     * 提取等级对应的抽奖次数上限
     */
    public static int getUserMaxCanDrawTimes(int vipLevel) {
        int maxcandrawtimes = 0;
        if (vipLevel >= 0 && vipLevel < 7) {// VIP等级暂时只有1-6级
            maxcandrawtimes = ValueUtil.getInteger(userMaxCanDrawMap.get(vipLevel).toString(), 0);
        }
        return maxcandrawtimes;
    }

    /**
     * 提取各个等级对应的抽奖次数上限列表
     */
    public static List<Integer> getUserMaxCanDrawTimesList() {
        List<Integer> list = new ArrayList<Integer>(10);
        for (Map.Entry<Integer, Integer> entry : userMaxCanDrawMap.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        return list;
    }

    /**
     * 提取各个type对应获取的成长值
     */
    public static int getMappedUserGrowthByType(int type) {
        int vipGroupValue = 0;
        if (type >= 0) {
            vipGroupValue = userMappedUserGrowthMap.get(type);
        }
        return vipGroupValue;
    }

    /**
     * 根据类型获取对应的积分值
     */
    public static int getMappedUserPointByType(int type) {
        int addPoint = 0;
        if (type >= 0) {
            addPoint = taskMappedUserPointMap.get(type);
        }
        return addPoint;
    }

    /**
     * 根据类型获取对应的积分值上限
     */
    public static int getUserMaxCanGetPointByType(int type) {
        int limitAddPoint = 0;
        if (type >= 0) {
            limitAddPoint = taskMappedUserMaxCanGetPointMap.get(type);
        }
        return limitAddPoint;
    }

    /** 幸运大抽奖等级用户对应的抽奖次数 **/
    private static String PREFIX_DRAW = "draw";
    /** 任务对应获取的积分值 **/
    private static String PREFIX_TASK = "task";
    /** 任务对应的成长值 **/
    private static String PREFIX_GROUP = "growth";
    /** 任务对应的积分获取上限值 **/
    private static String PREFIX_TASK_LIMIT = "uplimit";

    private static HashMap<Integer, Integer> taskMappedUserPointMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> taskMappedUserMaxCanGetPointMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> userMappedUserGrowthMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> userMaxCanDrawMap = new HashMap<Integer, Integer>();

    @AfterConfig
    protected void initCache() throws IOException {
        try {
            Properties properties = new Properties();
            HashMap<Integer, Integer> taskMappedUserPointTempMap = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> taskMappedUserMaxCanGetPointTempMap = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> userMappedUserGrowthTempMap = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> userMaxCanDrawTempMap = new HashMap<Integer, Integer>();
            properties.load(UserLogProperties.class.getClassLoader().getResourceAsStream("userLog.properties"));
            Set<Object> keys = properties.keySet();
            Iterator<Object> it = keys.iterator();
            while (it.hasNext()) {
                Object object = it.next();
                String key = object.toString();
                if (key.startsWith(PREFIX_GROUP)) {
                    userMappedUserGrowthTempMap.put(Integer.parseInt(key.substring(key.lastIndexOf("_") + 1)), Integer.parseInt(properties.get(key).toString()));
                } else if (key.startsWith(PREFIX_TASK)) {
                    taskMappedUserPointTempMap.put(Integer.parseInt(key.substring(key.lastIndexOf("_") + 1)), Integer.parseInt(properties.get(key).toString()));
                } else if (key.startsWith(PREFIX_TASK_LIMIT)) {
                    taskMappedUserMaxCanGetPointTempMap.put(Integer.parseInt(key.substring(key.lastIndexOf("_") + 1)), Integer.parseInt(properties.get(key).toString()));
                } else if (key.startsWith(PREFIX_DRAW)) {
                    userMaxCanDrawTempMap.put(Integer.parseInt(key.substring(key.lastIndexOf("_") + 1)), Integer.parseInt(properties.get(key).toString()));
                }
            }
            userMaxCanDrawMap = userMaxCanDrawTempMap;
            taskMappedUserPointMap = taskMappedUserPointTempMap;
            userMappedUserGrowthMap = userMappedUserGrowthTempMap;
            taskMappedUserMaxCanGetPointMap = taskMappedUserMaxCanGetPointTempMap;
        } catch (IOException e) {
            throw new IOException("启动加载userLog.properties文件失败，请检查文件配置信息");
        }
    }

}
