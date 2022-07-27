package com.landray.notify.update.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID生成器
 *
 * @author 叶中奇
 */
public class IDGenerator {
    /**
     * ID长度
     */
    public static final int LEN = 36;

    private static long jvmId = 0;
    private static final AtomicLong SEQ = new AtomicLong();
    private static final Random RANDOM = new SecureRandom();
    private static final char SPLIT = 'w';
    private static final int SPLIT_LENGTH = String.valueOf(SPLIT).length();
    private static final String PATTERN = "[0-9a-z]+";
    private static final String KEY_TIMESTAMP = "IDGenerator.timestamp";

    public static void setJvmId(long jvmId) {
        IDGenerator.jvmId = jvmId;
    }

    /**
     * 生成主键，36位，[0-9a-w]：时间+w+jvmId+w+流水号+w+随机数填充+w+tenantId
     */
    public static String generateID() {
        long now = System.currentTimeMillis();
        return generateID(now, 0);
    }

    /**
     * 根据指定时间生成主键
     */
    public static String generateID(long time) {
        return generateID(time, 0);
    }

    /**
     * 根据指定时间，租户生成主键
     */
    public static String generateID(long time, int tenant) {
        StringBuilder id = new StringBuilder(LEN);
        id.append(Long.toUnsignedString(time, 32)).append(SPLIT)
                .append(Long.toUnsignedString(jvmId, 32)).append(SPLIT);
        String tenantId = Integer.toUnsignedString(tenant, 32);
        // 除去租户ID长度
        int length = LEN - tenantId.length() - 1;
        // 序列号填充，若填充完超过指定长度，则取后半部分
        String seqNum = Long.toUnsignedString(SEQ.incrementAndGet(), 32);
        if (id.length() + seqNum.length() > length) {
            seqNum = seqNum.substring(id.length() + seqNum.length() - length,
                    seqNum.length());
        }
        id.append(seqNum).append(SPLIT);
        // 随机数填充，不超过leng长度
        while (id.length() < length) {
            id.append(Integer.toUnsignedString(RANDOM.nextInt(), 32));
        }
        id.delete(length, id.length());
        return id.append(SPLIT).append(tenantId).toString();
    }

    /**
     * 根据ID获取该ID创建的时间
     */
    public static long getIDCreateTime(String id) {
        String timeInfo = id.substring(0, id.indexOf(SPLIT));
        return Long.parseLong(timeInfo, 32);
    }

    /**
     * 根据ID获取该ID对应的租户ID
     */
    public static int getTenantId(String id) {
        String tenantInfo = id.substring(id.lastIndexOf(SPLIT) + SPLIT_LENGTH);
        return Integer.parseInt(tenantInfo, 32);
    }


    public static void main(String[] args) throws Exception {
        System.out.println(generateID());
    }
}
