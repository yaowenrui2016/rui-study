package indi.rui.study;

import java.lang.ref.*;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 强引用、软引用、弱引用和虚引用的演示
 * -XX:+PrintGC -Xms20m -Xmx20m
 * @author: yaowr
 * @create: 2019-10-21
 */
public class ReferenceDome {

    private static void referenceTest(BiFunction<byte[], ReferenceQueue, Reference> function) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ReferenceQueue queue = new ReferenceQueue();
        new Thread(() -> {
            System.out.println("启动新线程来获取队列中的目标引用...");
            countDownLatch.countDown();
            while (true) {
                Reference collectedRef;
                try {
                    collectedRef = queue.remove();
                    System.out.println("队列中的目标引用[" + collectedRef + " -> " + collectedRef.get() + "]");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
        try {
            countDownLatch.await();
            // 分配10M的对象
            System.out.println("------------创建10M的对象-------------");
            byte[] allocate = new byte[1024 * 1024 * 10];
            Reference<byte[]> reference = function.apply(allocate, queue);
            System.out.println("目标引用[" + reference + " -> " + reference.get() + "]");
            // 去除强引用
            allocate = null;
            // 手动gc
            System.out.println("------------手动GC-------------");
            System.gc();
            // 沉睡半秒等待gc执行
            Thread.sleep(500);
            System.out.println("目标引用[" + reference + " -> " + reference.get() + "]");
            System.out.println("------------再次创建10M对象-------------");
            byte[] newAllocate = new byte[1024 * 1024 * 10  ];
            System.out.println("目标引用[" + reference + " -> " + reference.get() + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 软引用测试
     */
    private static void softReferenceTest() {
        referenceTest(((target, queue) -> new SoftReference<>(target, queue)));
    }

    /**
     * 弱引用测试
     */
    private static void weakReferenceTest() {
        referenceTest(((target, queue) -> new WeakReference<>(target, queue)));
    }

    /**
     * 虚引用测试
     */
    private static void phantomReferenceTest() {
        referenceTest(((target, queue) -> new PhantomReference(target, queue)));
    }

    public static void main(String[] args) {
        // 1.软引用测试
         softReferenceTest();
        // 2.弱引用测试
//         weakReferenceTest();
        // 3.虚引用测试
//        phantomReferenceTest();
    }

    @Getter
    @AllArgsConstructor
    public static class Target {
        private String id;

        @Override
        public String toString() {
            return "Target[" + id + "]";
        }
    }
}
