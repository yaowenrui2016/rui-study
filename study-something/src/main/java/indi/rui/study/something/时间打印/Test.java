package indi.rui.study.something.时间打印;

import java.text.DecimalFormat;

/**
 * @author: yaowr
 * @create: 2023-01-03
 */
public class Test {


    private static DecimalFormat format = new DecimalFormat("000");

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            System.currentTimeMillis();
        }
        System.out.println(spendTime(begin));
    }

    private static String spendTime(long begin) {
        long milli = System.currentTimeMillis() - begin;
        long sec = milli / 1000;
        milli = milli % 1000;
        return sec + "." + format.format(milli) + "s";
    }
}
