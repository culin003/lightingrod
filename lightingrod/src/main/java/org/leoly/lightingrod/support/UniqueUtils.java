/**
 * copywrite 2015-2020 金地物业
 * 不能修改和删除上面的版权声明
 * 此代码属于数据与信息中心部门编写，在未经允许的情况下不得传播复制
 * UniqueUtils.java
 * @Date 2018年1月10日 上午11:14:46
 * guguihe
 */
package org.leoly.lightingrod.support;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO:
 * 
 * @author guguihe
 * @Date 2018年1月10日 上午11:14:46
 */
public class UniqueUtils {

    private final static Object locker = new Object();

    /**
     * 本机IP地址
     */
    private static String SERVER_IP = null;
    static {
        try {
            SERVER_IP = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
            SERVER_IP = "127.0.0.1";
        }
    }

    /**
     * 年份影射
     */
    private static final char[] YEAR_MAP = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't' };

    /**
     * 基准年份
     */
    private static final int BASE_YEAR = 2018;

    /**
     * 冲突时的处理
     */
    private final static Map<String, Integer> conflictMap = new ConcurrentHashMap<>();

    /**
     * 清理线程
     */
    private static Thread clearThread = new Thread() {
        public void run() {
            while (true) {
                Calendar c = Calendar.getInstance();
                String tempKey = String.valueOf(c.getTimeInMillis());
                conflictMap.forEach((key, value) -> {
                    if (Long.valueOf(tempKey) - Long.valueOf(key) > 1000L) {
                        conflictMap.remove(key);
                        System.out.println("Removed " + key + " : " + value);
                    }
                });

                try {
                    Thread.sleep(300);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    };

    /**
     * TODO: 生成逻辑开始
     */
    public static void start() {
        if (!clearThread.isAlive()) {
            clearThread.start();
        }
    }

    /**
     * TODO:获取唯一编码
     * 
     * @return
     */
    public static String getUniqueCode() {
        Calendar c = Calendar.getInstance();
        Integer y = c.get(Calendar.YEAR);
        Integer m = c.get(Calendar.MONDAY);
        Integer d = c.get(Calendar.DAY_OF_MONTH);
        Integer h = c.get(Calendar.HOUR_OF_DAY);
        Integer mm = c.get(Calendar.MINUTE);
        Integer s = c.get(Calendar.SECOND);
        Integer ss = c.get(Calendar.MILLISECOND);

        StringBuilder sb = new StringBuilder();
        BigDecimal b = null;

        // IP地址编码
        String ipStr = SERVER_IP.replace(".", "0");
        b = new BigDecimal(ipStr);
        ipStr = b.toBigInteger().toString(16);
        sb.append(ipStr.substring(ipStr.length() - 3));

        // 超出范围时，直接使用年份的32位编码
        if (y >= BASE_YEAR + YEAR_MAP.length) {
            b = new BigDecimal(y.toString());
            sb.append(b.toBigInteger().toString(16));
        }
        else {
            sb.append(YEAR_MAP[y - BASE_YEAR]);
        }

        // 月 + 日
        b = new BigDecimal(m.toString() + d.toString());
        sb.append(b.toBigInteger().toString(16));

        // 时 + 分 + 秒 + 毫秒
        b = new BigDecimal(h.toString() + mm.toString() + s.toString() + ss.toString());
        sb.append(b.toBigInteger().toString(16));

        String key = String.valueOf(c.getTimeInMillis());
        synchronized (locker) {
            if (conflictMap.containsKey(key)) {
                Integer index = conflictMap.get(key);
                index += 1;
                conflictMap.put(key, index);
                sb.append(index);
            }
            else {
                conflictMap.put(key, 0);
                sb.append("0");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
//        UniqueUtils.start();
//        class MyThread extends Thread {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    System.out.println(getUniqueCode());
//                }
//            }
//        }
//
//        for (int i = 0; i < 2; i++) {
//            MyThread t = new MyThread();
//            t.start();
//        }
        
        String a = "003333";
        BigDecimal b = new BigDecimal(a);
        System.out.println(b.intValue());
        
    }
}
