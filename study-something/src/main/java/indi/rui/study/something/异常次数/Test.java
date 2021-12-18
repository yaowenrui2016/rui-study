package indi.rui.study.something.异常次数;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-12-16
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        int count = 0;
        for (; ; ) {
            try {
                ((Object) null).toString();
            } catch (RuntimeException e) {
                if (e.getStackTrace().length == 0) {
                    System.err.println(++count + ": " + (e.getStackTrace().length == 0));
                }
            }
        }
    }
}
