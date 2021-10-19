package indi.rui.study.nio;

/**
 * @author: yaowr
 * @create: 2021-07-27
 */
public class T20210727_SubStringDemo {
    public static void main(String[] args) {
        String s = "hello";
        String a = "he";
        String s1 = s.substring(a.length());
        System.out.println("s = " + s);
        System.out.println("a = " + a);
        System.out.println("s1 = " + s1);
    }
}
