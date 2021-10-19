package indi.rui.study.something.细粒度锁;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 弱引用哈希锁
 *
 * @author: yaowr
 * @create: 2021-10-18
 */
public class WeakHashLock<K> {
    private ConcurrentHashMap<K, LockWeakReference<K, ReentrantLock>> lockMap = new ConcurrentHashMap<>();
    private ReferenceQueue<ReentrantLock> queue = new ReferenceQueue<>();


    public ReentrantLock getLock(K key) {
        if (lockMap.size() > 1000) {
            clearEmptyLock();
        }
        LockWeakReference<K, ReentrantLock> lockRef = lockMap.get(key);
        ReentrantLock lock = null;
        if (lockRef != null) {
            lock = lockRef.get();
        }
        while (lock == null) {
            lockMap.putIfAbsent(key, new LockWeakReference<>(new ReentrantLock(true), queue, key));
            lockRef = lockMap.get(key);
            if (lockRef != null) {
                lock = lockRef.get();
            }
            if (lock != null) {
                return lock;
            }
            clearEmptyLock();
        }
        return lock;
    }

    private void clearEmptyLock() {
        Reference<? extends ReentrantLock> ref;
        while ((ref = queue.poll()) != null) {
            LockWeakReference<K, ReentrantLock> lwRef = (LockWeakReference<K, ReentrantLock>) ref;
            lockMap.remove(lwRef.key);
        }
    }

    private static class LockWeakReference<K, T> extends WeakReference<T> {

        private K key;

        public LockWeakReference(T referent, ReferenceQueue<? super T> q, K key) {
            super(referent, q);
            this.key = key;
        }
    }
}
