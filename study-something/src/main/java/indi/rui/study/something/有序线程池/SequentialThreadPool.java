package indi.rui.study.something.有序线程池;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: yaowr
 * @create: 2021-10-19
 */
public class SequentialThreadPool<K> {
    private ThreadPoolExecutor executor;

    private ConcurrentMap<K, JobWeakReference<SequentialJob>> jobMap = new ConcurrentHashMap<>();

    private ReferenceQueue<SequentialJob> queue = new ReferenceQueue<>();

    public SequentialThreadPool(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public void executeSerial(K key, Runnable r) {
        if (jobMap.size() > 100) {
            clearEmptyJob();
        }
        JobWeakReference<SequentialJob> jobRef = jobMap.get(key);
        SequentialJob job = null;
        if (jobRef != null) {
            job = jobRef.get();
        }
        while (job == null) {
            jobMap.putIfAbsent(key, new JobWeakReference<>(new SequentialJob<>(key), queue, key));
            jobRef = jobMap.get(key);
            if (jobRef != null) {
                job = jobRef.get();
            }
            if (job != null) {
                break;
            }
            clearEmptyJob();
        }
        job.addJob(r);
    }

    private void clearEmptyJob() {
        Reference<? extends SequentialJob> ref;
        while ((ref = queue.poll()) != null) {
            JobWeakReference<? extends SequentialJob> jobRef = (JobWeakReference<? extends SequentialJob>) ref;
            jobMap.remove(jobRef.key);
            System.out.println("remove job: " + jobRef.key);
        }
    }

    public void monitor() {
        System.gc();
        System.out.println("-------- GC --------");
        clearEmptyJob();
        System.out.println("concurrent jobs: " + jobMap.size());
    }

    /**
     * 任务虚引用封装类
     */
    class JobWeakReference<T> extends WeakReference<T> {

        private K key;

        public JobWeakReference(T referent, ReferenceQueue<? super T> q, K key) {
            super(referent, q);
            this.key = key;
        }
    }

    /**
     * 有序任务封装类
     */
    class SequentialJob<K> implements Runnable {

        private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1000);

        private AtomicBoolean running = new AtomicBoolean(false);

        private K key;

        public SequentialJob(K key) {
            this.key = key;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable r = queue.poll(100, TimeUnit.MILLISECONDS);
                    if (r != null) {
                        r.run();
                    } else {
                        synchronized (this) {
                            if (queue.isEmpty() &&
                                    running.compareAndSet(true, false)) {
                                return;
                            }
                        }
                    }
                } catch (Throwable e) {
                    // TODO
                    e.printStackTrace();
                }
            }
        }

        public void addJob(Runnable r) {
            synchronized (this) {
                queue.add(r);
                if (running.compareAndSet(false, true)) {
                    executor.execute(this);
                }
            }
        }
    }
}
