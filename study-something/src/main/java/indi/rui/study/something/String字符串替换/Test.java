package indi.rui.study.something.String字符串替换;

/**
 * @author: yaowr
 * @create: 2022-03-07
 */
public class Test {
    public static void main(String[] args) {
        String s = "\nabc\nadef\naghi\najkl";
        System.out.println("s:" + s);
        String t = s.replace("\na", "-");
        System.out.println("t:" + t);
    }
}
