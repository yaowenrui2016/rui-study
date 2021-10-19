package indi.rui.study.something.弱引用;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 弱引用，发生GC时一定会回收
 *
 * @author: yaowr
 * @create: 2021-10-18
 */
public class Test {
    private List<WeakReference<String>> refs = new ArrayList<>();
    private ReferenceQueue<String> queue = new ReferenceQueue<>();

    private void init() {
        for (int i = 0; i < 10; i++) {
            String value = "KEY_" + i;
            refs.add(new CustomWeakReference<>(value, queue, i));
        }
    }

    private void check() {
        System.out.println("check:");
        for (WeakReference<String> wref : refs) {
            System.out.println(wref.get());
        }
    }

    private void result() {
        System.out.println("result:");
        Reference<? extends String> ref;
        while ((ref = queue.poll()) != null) {
            CustomWeakReference<String> cwref = (CustomWeakReference<String>) ref;
            System.out.println(cwref.key);
        }
    }

    static class CustomWeakReference<T> extends WeakReference<T> {
        private int key;

        public CustomWeakReference(T referent, ReferenceQueue<? super T> q, int key) {
            super(referent, q);
            this.key = key;
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.init();
        while (true) {
            try {
                test.check();
                test.result();
                System.gc();
                System.out.println("-------- GC ------");
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
