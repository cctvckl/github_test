package com.ckl.websocketdemo;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.lang.management.MemoryUsage;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2018/6/26 0026
 * creat_time: 12:38
 **/
public class SimpleTests {
    public static void main(String[] args) {
        test3();

    }

    public static void test2() {
        //无法使用 service:jmx:rmi:///jndi/rmi://192.168.44.128:9998/jmxrmi 连接到 192.168.44.128:9998
        //Cannot connect to 192.168.44.128:9998 using service:jmx:rmi:///jndi/rmi://192.168.44.128:9998/jmxrmi
        String jmxURL = "service:jmx:rmi:///jndi/rmi://192.168.44.128:9997/jmxrmi";
        JMXServiceURL serviceURL = null;
        try {
            serviceURL = new JMXServiceURL(jmxURL);
            Map map = new HashMap();
//            String[] credentials = new String[]{"monitorRole", "QED"};
//            map.put("jmx.remote.credentials", credentials);
//            JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
            MBeanServerConnection mbsc = connector.getMBeanServerConnection();

            //堆使用率
            ObjectName heapObjName = new ObjectName("java.lang:type=Memory");
            MemoryUsage heapMemoryUsage = MemoryUsage.from((CompositeDataSupport) mbsc.getAttribute(heapObjName, "HeapMemoryUsage"));
            System.out.println(heapMemoryUsage);
            long maxMemory = heapMemoryUsage.getMax();//堆最大
            long commitMemory = heapMemoryUsage.getCommitted();//堆当前分配
            long usedMemory = heapMemoryUsage.getUsed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test1() {
        try {
            String jndiPath = "jmxrmi";
            String serverhost = "192.168.44.128";
            String serverport = "9997";
            // url=service:jmx:rmi:///jndi/rmi://192.168.8.7:8088/jmxrmi
            String jmxurl = "service:jmx:rmi:///jndi/rmi://" + serverhost + ":"
                    + serverport + "/" + jndiPath;
            System.out.println("jmxurl:" + jmxurl);
            JMXServiceURL url = new JMXServiceURL(jmxurl);
            Map<String, Object> enviMap = new HashMap<String, Object>();

            JMXConnector connector = JMXConnectorFactory.connect(url, enviMap);

            MBeanServerConnection mbsc = connector.getMBeanServerConnection();
            System.out.println("successful connected ");
            connector.close();
            System.out.println("close connect");
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static void test3() {
        try {

            String jmxURL = "service:jmx:rmi:///jndi/rmi://192.168.44.128:9997/jmxrmi";

            JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);

            Map map = new HashMap();
            String[] credentials = new String[]{"monitorRole", "tomcat"};
            map.put("jmx.remote.credentials", credentials);
            JMXConnector connector = JMXConnectorFactory.connect(serviceURL,
                    map);
            MBeanServerConnection mbsc = connector.getMBeanServerConnection();

            // 端口最好是动态取得
            ObjectName threadObjName = new ObjectName(
                    "Catalina:type=ThreadPool,name=\"http-nio-8080\"");
            MBeanInfo mbInfo = mbsc.getMBeanInfo(threadObjName);

            String attrName = "currentThreadCount";// tomcat的线程数对应的属性值
            MBeanAttributeInfo[] mbAttributes = mbInfo.getAttributes();
            System.out.println("currentThreadCount:"
                    + mbsc.getAttribute(threadObjName, attrName));

            // heap
            for (int j = 0; j < mbsc.getDomains().length; j++) {
                System.out.println("###########" + mbsc.getDomains()[j]);
            }
            Set MBeanset = mbsc.queryMBeans(null, null);
            System.out.println("MBeanset.size() : " + MBeanset.size());
            Iterator MBeansetIterator = MBeanset.iterator();
            while (MBeansetIterator.hasNext()) {
                ObjectInstance objectInstance = (ObjectInstance) MBeansetIterator
                        .next();
                ObjectName objectName = objectInstance.getObjectName();
                String canonicalName = objectName.getCanonicalName();
                System.out.println("canonicalName : " + canonicalName);
                if (canonicalName
                        .equals("Catalina:host=localhost,type=Cluster")) {
                    // Get details of cluster MBeans
                    System.out.println("Cluster MBeans Details:");
                    System.out
                            .println("=========================================");
                    // getMBeansDetails(canonicalName);
                    String canonicalKeyPropList = objectName
                            .getCanonicalKeyPropertyListString();
                }
            }
            // ------------------------- system ----------------------
            ObjectName runtimeObjName = new ObjectName("java.lang:type=Runtime");
            System.out.println("厂商:"
                    + (String) mbsc.getAttribute(runtimeObjName, "VmVendor"));
            System.out.println("程序:"
                    + (String) mbsc.getAttribute(runtimeObjName, "VmName"));
            System.out.println("版本:"
                    + (String) mbsc.getAttribute(runtimeObjName, "VmVersion"));
            Date starttime = new Date((Long) mbsc.getAttribute(runtimeObjName,
                    "StartTime"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("启动时间:" + df.format(starttime));

            Long timespan = (Long) mbsc.getAttribute(runtimeObjName, "Uptime");
            System.out.println("连续工作时间:" + formatTimeSpan(timespan));
            // ------------------------ JVM -------------------------
            // 堆使用率
            ObjectName heapObjName = new ObjectName("java.lang:type=Memory");
            MemoryUsage heapMemoryUsage = MemoryUsage
                    .from((CompositeDataSupport) mbsc.getAttribute(heapObjName,
                            "HeapMemoryUsage"));
            long maxMemory = heapMemoryUsage.getMax();// 堆最大
            long commitMemory = heapMemoryUsage.getCommitted();// 堆当前分配
            long usedMemory = heapMemoryUsage.getUsed();
            System.out.println("heap:" + (double) usedMemory * 100
                    / commitMemory + "%");// 堆使用率

            MemoryUsage nonheapMemoryUsage = MemoryUsage
                    .from((CompositeDataSupport) mbsc.getAttribute(heapObjName,
                            "NonHeapMemoryUsage"));
            long noncommitMemory = nonheapMemoryUsage.getCommitted();
            long nonusedMemory = heapMemoryUsage.getUsed();
            System.out.println("nonheap:" + (double) nonusedMemory * 100
                    / noncommitMemory + "%");

            ObjectName permObjName = new ObjectName(
                    "java.lang:type=MemoryPool,name=Metaspace");
            MemoryUsage permGenUsage = MemoryUsage
                    .from((CompositeDataSupport) mbsc.getAttribute(permObjName,
                            "Usage"));
            long committed = permGenUsage.getCommitted();// 持久堆大小
            long used = heapMemoryUsage.getUsed();//
            System.out.println("perm gen:" + (double) used * 100 / committed
                    + "%");// 持久堆使用率

            // -------------------- Session ---------------
            ObjectName managerObjName = new ObjectName(
                    "Catalina:type=Manager,*");
            Set<ObjectName> s = mbsc.queryNames(managerObjName, null);
            for (ObjectName obj : s) {
                System.out.println("应用名:" + obj.getKeyProperty("path"));
                ObjectName objname = new ObjectName(obj.getCanonicalName());
                System.out.println("最大会话数:"
                        + mbsc.getAttribute(objname, "maxActiveSessions"));
                System.out.println("会话数:"
                        + mbsc.getAttribute(objname, "activeSessions"));
                System.out.println("活动会话数:"
                        + mbsc.getAttribute(objname, "sessionCounter"));
            }

            // ----------------- Thread Pool ----------------
            ObjectName threadpoolObjName = new ObjectName(
                    "Catalina:type=ThreadPool,*");
            Set<ObjectName> s2 = mbsc.queryNames(threadpoolObjName, null);
            for (ObjectName obj : s2) {
                System.out.println("端口名:" + obj.getKeyProperty("name"));
                ObjectName objname = new ObjectName(obj.getCanonicalName());
                System.out.println("最大线程数:"
                        + mbsc.getAttribute(objname, "maxThreads"));
                System.out.println("当前线程数:"
                        + mbsc.getAttribute(objname, "currentThreadCount"));
                System.out.println("繁忙线程数:"
                        + mbsc.getAttribute(objname, "currentThreadsBusy"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formatTimeSpan(long span) {
        long minseconds = span % 1000;

        span = span / 1000;
        long seconds = span % 60;

        span = span / 60;
        long mins = span % 60;

        span = span / 60;
        long hours = span % 24;

        span = span / 24;
        long days = span;
        return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d.%5$03d",
                days, hours, mins, seconds, minseconds).toString();
    }

}
