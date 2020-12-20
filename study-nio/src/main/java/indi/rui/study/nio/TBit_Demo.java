package indi.rui.study.nio;

/**
 * @author: yaowr
 * @create: 2020-12-18
 */
public class TBit_Demo {
    /**
     * 移位操作：
     * 1.左移操作：无论正数和负数右边统一补0，左边第一位符号位遇到1为负数，遇到0为正数；
     * 2.右移操作：正数左边补0，负数左边补1；
     * 3.无符号右移操作：无论正数和负数左边统一补0；
     * @param args
     */
    public static void main(String[] args) {
        int offset = 28;
        int a = 0xffffffff;
        int m = Integer.MAX_VALUE;
        short s = Short.MAX_VALUE;

        String a_str = Integer.toBinaryString(a);
        System.out.println("a        = " + a_str + "(" + a_str.length() + "位) = " + a);

        int a1 = a << offset;
        String a1_str = Integer.toBinaryString(a1);
        System.out.println("a <<  " + offset + " = " + a1_str + "(" + a1_str.length() + "位) = " + a1);

        int a2 = a >> offset;
        String a2_str = Integer.toBinaryString(a2);
        System.out.println("a >>  " + offset + " = " + a2_str + "(" + a2_str.length() + "位) = " + a2);

        int a3 = a >>> offset;
        String a3_str = Integer.toBinaryString(a3);
        System.out.println("a >>> " + offset + " = " + a3_str + "(" + a3_str.length() + "位) = " + a3);

        int b = -a;
        String b_str = Integer.toBinaryString(b);
        System.out.println("b        = " + b_str + "(" + b_str.length() + "位) = " + b);

        int b1 = b << offset;
        String b1_str = Integer.toBinaryString(b1);
        System.out.println("b <<  " + offset + " = " + b1_str + "(" + b1_str.length() + "位) = " + b1);

        int b2 = b >> offset;
        String b2_str = Integer.toBinaryString(b2);
        System.out.println("b >>  " + offset + " = " + b2_str + "(" + b2_str.length() + "位) = " + b2);

        int b3 = b >>> offset;
        String b3_str = Integer.toBinaryString(b3);
        System.out.println("b >>> " + offset + " = " + b3_str + "(" + b3_str.length() + "位) = " + b3);
    }
}
