package com.zq.cloud.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * todo 结合中间件 保证datacenterId和machineId唯一性 暂时使用ip  https://www.cnblogs.com/shanzhai/p/10500274.html
 * <p>
 * Twitter的雪花算法SnowFlake，使用Java语言实现
 */
@Slf4j
public class SnowFlakeUtils {
    private static final SnowFlake INSTANCE = initIpBasedSnowFlake();

    private static SimpleDateFormat yyMMddSimpleDateFormat = new SimpleDateFormat("yyMMdd");
    private static SimpleDateFormat yyMMddHHddssSimpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");

    public static SnowFlake initIpBasedSnowFlake() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String localIpStr = ip.substring(ip.lastIndexOf('.') + 1);
            long localIp = Long.parseLong(localIpStr.replaceAll("\\.", ""));
            long datacenterId = localIp / 32;
            long machineId = localIp % 32;
            return new SnowFlake(datacenterId, machineId);
        } catch (Exception e) {
            log.error("Error Initializing ID Generator : " + e.getMessage(), e);

            return new SnowFlake(Long.parseLong(String.valueOf(Math.random() * 32)), Long.parseLong(String.valueOf(Math.random() * 32)));
        }
    }

    /**
     * 通过雪花算法获取唯一ID
     *
     * @return 唯一ID
     */
    public static long getNextId() {
        return INSTANCE.nextId();
    }

    /**
     * 通过年月日+雪花算法获取唯一ID，一般可用于订单号等需要可以目测看出年月日的需求
     *
     * @return 长度26的唯一ID
     */
    public static String getNextId26() {
        String yyMMdd = yyMMddSimpleDateFormat.format(new Date());
        return yyMMdd + StringUtils.leftPad(INSTANCE.nextId() + "", 20, "0");
    }

    /**
     * 通过年月日时分秒+雪花算法获取唯一ID，一般可用于订单号等需要可以目测看出年月日时分秒的需求
     *
     * @return 长度32的唯一ID
     */
    public static String getNextId32() {
        String yyMMddHHmmss = yyMMddHHddssSimpleDateFormat.format(new Date());
        return yyMMddHHmmss + StringUtils.leftPad(INSTANCE.nextId() + "", 20, "0");
    }

    /**
     * https://github.com/beyondfengyu/SnowFlake/blob/master/SnowFlake.java
     */
    public static class SnowFlake {

        /**
         * 起始的时间戳: 2017-09-08
         */
        private final static long START_STMP = 1504800000000L;

        /**
         * 每一部分占用的位数
         */
        private final static long SEQUENCE_BIT = 12; //序列号占用的位数
        private final static long MACHINE_BIT = 5;   //机器标识占用的位数
        private final static long DATACENTER_BIT = 5;//数据中心占用的位数

        /**
         * 每一部分的最大值
         */
        private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
        private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
        private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

        /**
         * 每一部分向左的位移
         */
        private final static long MACHINE_LEFT = SEQUENCE_BIT;
        private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

        private long datacenterId;  //数据中心
        private long machineId;     //机器标识
        private long sequence = 0L; //序列号
        private long lastStmp = -1L;//上一次时间戳

        public SnowFlake(long datacenterId, long machineId) {
            if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
                throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
            }
            if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
            this.datacenterId = datacenterId;
            this.machineId = machineId;
        }

        /**
         * 产生下一个ID
         *
         * @return
         */
        public synchronized long nextId() {
            long currStmp = getNewstmp();
            if (currStmp < lastStmp) {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            if (currStmp == lastStmp) {
                //相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //同一毫秒的序列数已经达到最大
                if (sequence == 0L) {
                    currStmp = getNextMill();
                }
            } else {
                //不同毫秒内，序列号置为0
                sequence = 0L;
            }

            lastStmp = currStmp;

            return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                    | datacenterId << DATACENTER_LEFT       //数据中心部分
                    | machineId << MACHINE_LEFT             //机器标识部分
                    | sequence;                             //序列号部分
        }

        private long getNextMill() {
            long mill = getNewstmp();
            while (mill <= lastStmp) {
                mill = getNewstmp();
            }
            return mill;
        }

        private long getNewstmp() {
            return System.currentTimeMillis();
        }

    }
}