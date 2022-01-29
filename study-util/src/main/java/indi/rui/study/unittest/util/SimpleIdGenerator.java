package indi.rui.study.unittest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2022-01-28
 */
public class SimpleIdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SimpleIdGenerator.class);

    private static final ConcurrentMap<Long, CounterWeakReference> TIMESTAMP_MAP = new ConcurrentHashMap<>();

    private static final ReferenceQueue<AtomicInteger> QUEUE = new ReferenceQueue<>();

    public static String generateID() {
        if (TIMESTAMP_MAP.size() > 100) {
            clean();
        }
        long timestamp = System.currentTimeMillis();
        AtomicInteger counter;
        while (true) {
            CounterWeakReference counterRef = TIMESTAMP_MAP.computeIfAbsent(timestamp,
                    (k) -> new CounterWeakReference(new AtomicInteger(10000), QUEUE, k));
            counter = counterRef.get();
            if (counter != null) {
                break;
            }
            clean();
        }
        return timestamp + "" + counter.getAndIncrement();
    }

    private static void clean() {
        Reference<? extends AtomicInteger> ref;
        while ((ref = QUEUE.poll()) != null) {
            CounterWeakReference counterRef = (CounterWeakReference) ref;
            TIMESTAMP_MAP.remove(counterRef.key);
            logger.debug("remove the key: {}", counterRef.key);
        }
    }

    static class CounterWeakReference extends WeakReference<AtomicInteger> {

        private long key;

        CounterWeakReference(AtomicInteger referent, ReferenceQueue<? super AtomicInteger> q, long key) {
            super(referent, q);
            this.key = key;
        }
    }
}
