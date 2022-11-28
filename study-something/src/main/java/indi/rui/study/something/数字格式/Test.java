package indi.rui.study.something.数字格式;

import java.text.DecimalFormat;

/**
 * @author: yaowr
 * @create: 2022-11-16
 */
public class Test {


    private static final DecimalFormat FORMAT = new DecimalFormat("00");

    public static void main(String[] args) {
        System.out.println(formatTime(6,18));
    }


    private static String formatTime(int beginMin, int endMin) {
        StringBuilder builder = new StringBuilder();
        builder.append(FORMAT.format(beginMin / 60)).append(":").append(FORMAT.format(beginMin % 60))
                .append("-")
                .append(FORMAT.format(endMin / 60)).append(":").append(FORMAT.format(endMin % 60));
        return builder.toString();
    }
}
