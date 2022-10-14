package indi.rui.study.something.String切割;

/**
 * @author: yaowr
 * @create: 2022-09-07
 */
public class Main {
    public static void main(String[] args) {
//        String str = "abcd";
//        System.out.println(str.substring(0, str.length() - 1));
//        System.out.println(str.substring(str.length() - 1));

        String str2 = "abc";
        String split = "###";
        String[] splits = str2.split(split);
        for (int i = 0; i < splits.length; i++) {
            System.out.println(splits[i]);
        }
    }
}
