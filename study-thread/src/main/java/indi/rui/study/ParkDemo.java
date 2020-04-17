package indi.rui.study;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: yaowr
 * @create: 2019-11-05
 */
public class ParkDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            myPrint("开始");
            for (int i = 0;;i++) {
                if ((i % 100000000) == 0) {
                    myPrint("---" + i);
                }
                if (i == 400000000) {
                    myPrint("停车");
                    LockSupport.park();
                    myPrint("继续开车");
                    if (Thread.interrupted()) {
                        myPrint("被打断");
                        break;
                    }
                }
            }
            myPrint("结束");
        }, "t1");


        t1.start();

        try {
            myPrint("睡8秒...");
            Thread.sleep(8000);
            myPrint("醒了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        myPrint("打断t1");
//        t1.interrupt();
        myPrint("唤醒t1的司机");
        LockSupport.unpark(t1);

        myPrint("结束");
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    public static void myPrint(Object obj) {
        System.out.printf("%s [%s] %s%n", sdf.format(new Date()), Thread.currentThread().getName(), obj.toString());
    }
}
