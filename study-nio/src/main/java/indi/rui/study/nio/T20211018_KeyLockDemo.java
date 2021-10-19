package indi.rui.study.nio;

import java.util.concurrent.Semaphore;

/**
 * @author: yaowr
 * @create: 2021-10-18
 */
public class T20211018_KeyLockDemo {

    public static void main(String[] args) {
        Semaphore current  = new Semaphore(1, true);
        current.acquireUninterruptibly();
    }
}
