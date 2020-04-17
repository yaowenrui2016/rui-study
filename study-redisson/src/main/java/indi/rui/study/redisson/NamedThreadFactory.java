package indi.rui.study.redisson;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2020-02-14
 */
public class NamedThreadFactory implements ThreadFactory {
    private AtomicInteger count = new AtomicInteger(1);
    private String namePrefix;

    public NamedThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, namePrefix + "-" + count.getAndIncrement());
    }

}
