package indi.rui.study.something.ThreadLocal演示;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-10-21
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        List<ThreadLocal<Integer>> tls = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int val = i;
            ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> val);
            tls.add(threadLocal);
        }


        for (ThreadLocal<Integer> threadLocal : tls) {
            log.info("{}", threadLocal.get());
        }

    }
}
