package indi.rui.study.something.数组;

import java.util.Arrays;

/**
 * @author: yaowr
 * @create: 2022-10-20
 */
public class Test {
    public static void main(String[] args) {
        Integer[] arr = new Integer[100];
        arr[1] = 1;
        arr[10] = 1;
        arr[99] = 1;
        System.out.println("arr = " + Arrays.toString(arr));
    }
}
