package com.kankan.op.util;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import com.xunlei.util.EmptyChecker;
import com.xunlei.util.Log;
import com.xunlei.util.StringTools;

public final class StarRoutingFactory {

    private static final Logger log = Log.getLogger();

    public interface RoutingPolicy {

        public InetSocketAddress getAddress(long userId);

        /**
         * 获取可连接的服务器的个数
         */
        public int getAddressNum();
    }

    public static class DirectRoutingPolicy implements RoutingPolicy {

        private InetSocketAddress address;

        @Override
        public InetSocketAddress getAddress(long userId) {
            return address;
        }

        public DirectRoutingPolicy(InetSocketAddress address) {
            this.address = address;
        }

        @Override
        public int getAddressNum() {
            return 1;
        }

        @Override
        public String toString() {
            return String.format("DirectRoutingPolicy [address=%s]", address);
        }
    }

    public static class PollingRoutingPolicy implements RoutingPolicy {

        private InetSocketAddress[] addresses;
        private AtomicLong counter;

        @Override
        public InetSocketAddress getAddress(long userId) {
            try {
                return addresses[(int) (counter.incrementAndGet() % addresses.length)];
            } catch (Exception e) {
                if (counter.get() < 0) {
                    counter = new AtomicLong();
                }
                return addresses[(int) (counter.incrementAndGet() % addresses.length)];
            }

        }

        public PollingRoutingPolicy(InetSocketAddress[] addresses) {
            this.addresses = addresses;
            this.counter = new AtomicLong();
        }

        @Override
        public int getAddressNum() {
            return addresses.length;
        }

        @Override
        public String toString() {
            return String.format("PollingRoutingPolicy [addresses=%s]", Arrays.toString(addresses));
        }
    }

    public static class HashRoutingPolicy implements RoutingPolicy {

        /** 模的分母 */
        private int segmentsNum;
        /** 分段区间设定，如按userId%256分，那么segmentsNum=256，分两段，下标0-127为第一段，下标128-256为第二段 */
        private InetSocketAddress[] segments;
        private int addressNum;

        @Override
        public InetSocketAddress getAddress(long userId) {
            try {
                return segments[(int) (Math.abs(userId) % segmentsNum)];
            } catch (Exception e) {
                log.error("HashRoutingPolicy.getAddress({}) encount error", userId, e);
                throw new RuntimeException(e);
            }
        }

        public HashRoutingPolicy(InetSocketAddress[] addresses, List<Integer> segmentEndIndex) {
            addressNum = addresses.length;
            if (EmptyChecker.isNotEmpty(segmentEndIndex)) {
                Collections.sort(segmentEndIndex);
                segmentsNum = segmentEndIndex.get(segmentEndIndex.size() - 1) + 1;
                segments = new InetSocketAddress[segmentsNum];
                int index = 0;
                for (int i = 0; i < segmentEndIndex.size(); i++) {
                    int top = segmentEndIndex.get(i);
                    for (; index <= top; index++) {
                        segments[index] = addresses[i];
                    }
                }
            } else {
                segmentsNum = addresses.length;
                segments = addresses;
            }
        }

        @Override
        public int getAddressNum() {
            return addressNum;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int begin = 0;
            int end = 0;
            if (EmptyChecker.isNotEmpty(segments)) {
                InetSocketAddress lastAddr = null;
                for (int i = 0; i < segmentsNum; i++) {
                    InetSocketAddress addr = segments[i];
                    if (i == segmentsNum - 1) { // 最后一个就不需要处理额外情况了
                        sb.append(lastAddr).append("[").append(begin).append(",").append(end).append("]");
                    } else if (!addr.equals(lastAddr)) {
                        if (lastAddr != null) {
                            sb.append(lastAddr).append("[").append(begin).append(",").append(end - 1).append("], ");
                        }
                        lastAddr = addr;
                        begin = end;
                    }
                    end++;
                }
            }
            return "HashRoutingPolicy [segmentsNum=" + segmentsNum + ", segments=" + sb.toString() + "]";
        }
    }

    public static RoutingPolicy parseRoutingPolicy(String str) {
        RoutingPolicy rp = null;
        try {
            // routing_ci = "polling|hash;ip:port,ip:port;default_port";
            String[] arr = StringTools.splitAndTrimAsArray(str, ";");
            String policyName = arr[0].toLowerCase();
            String addresses = arr[1];
            InetSocketAddress[] finalAddArr = {};

            String[] addArr = StringTools.splitAndTrimAsArray(addresses, ",");
            List<Integer> segmentEndIndex = new ArrayList<Integer>(addArr.length);
            List<InetSocketAddress> addList = new ArrayList<InetSocketAddress>(addArr.length);
            for (String add : addArr) {
                String[] t = StringTools.splitAndTrimAsArray(add, ":");
                if (t.length == 1) {
                    throw new RuntimeException("missing port: " + add + " for input string: " + str);
                }
                addList.add(new InetSocketAddress(t[0], Integer.valueOf(t[1])));
                if (t.length > 2) {
                    segmentEndIndex.add(Integer.valueOf(t[2]));
                }
            }

            finalAddArr = addList.toArray(finalAddArr);

            if (finalAddArr.length == 1) {
                rp = new DirectRoutingPolicy(finalAddArr[0]);
            } else if ("polling".equals(policyName)) {
                rp = new PollingRoutingPolicy(finalAddArr);
            } else if ("hash".equals(policyName)) {
                rp = new HashRoutingPolicy(finalAddArr, segmentEndIndex);
            } else {
                rp = new PollingRoutingPolicy(finalAddArr); // 默认 直接 polling
            }
        } catch (Exception e) {
            log.error("cannot parse:{}", str, e);
            return null;
        } finally {
            // if (rp != null) {
            //
            // }
        }
        return rp;
    }

    public static void main(String[] args) {
        RoutingPolicy rp = parseRoutingPolicy("direct;127.0.0.1,127.0.0.2;80");
        RoutingPolicy rp1 = parseRoutingPolicy("hash;127.0.0.1:89");
        RoutingPolicy rp2 = parseRoutingPolicy("direct;proxy.ci:80");
        RoutingPolicy rp3 = parseRoutingPolicy("hash;proxy.userbase:19801:127,proxy.userbase:29801:255,proxy.userbase:39801:5");
        System.out.println(rp);
        System.out.println(rp1);
        System.out.println(rp2);
        System.out.println(rp3);
    }
}
