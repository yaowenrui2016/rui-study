package indi.rui.study;

import static indi.rui.study.ParkDemo.myPrint;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: yaowr
 * @create: 2019-11-08
 */
public class ConsumerProducerDemo {

    private static final int PRODUCER_SIZE = 4;
    private static final int CONSUMER_SIZE = 4;
    private static final int CORE_TICKET_POOL_SIZE = 31;
    private static final int MAX_TICKET_POOL_VAL = 1000;

    private static class Ticket {
        private static final int INITIAL_VAL = 1;
        private static int count = INITIAL_VAL;
        private String name;

        public Ticket() {
            this.name = "<Ticket:" + (count++) + ">";
        }
    }

    private void toBarrier(CyclicBarrier barrier) {
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        LinkedList<Ticket> list = new LinkedList<>();
        CyclicBarrier barrier = new CyclicBarrier(PRODUCER_SIZE + CONSUMER_SIZE);
        Lock lock = new ReentrantLock();
        Condition notEmpty = lock.newCondition();
        Condition notFull = lock.newCondition();
        // 生产者
        for (int i = 0; i < CONSUMER_SIZE; i++) {
            new Thread(() -> {
                toBarrier(barrier);
                for (;;) {
                    lock.lock();
                    try {
                        if (list.size() == CORE_TICKET_POOL_SIZE) {
                            notFull.awaitUninterruptibly();
                            continue;
                        }
                        if (Ticket.count > MAX_TICKET_POOL_VAL) {
                            myPrint("今日票据售卖完成！");
                            break;
                        }
                        Ticket ticket = new Ticket();
                        myPrint("产生票据: " + ticket.name);
                        list.addLast(ticket);
                        notEmpty.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }, "Prod-" + (i + 1)).start();
        }

        // 消费者
        for (int i = 0; i < PRODUCER_SIZE; i++) {
            new Thread(() -> {
                toBarrier(barrier);
                for (;;) {
                    lock.lock();
                    try {
                        if (list.size() == 0) {
                            notEmpty.awaitUninterruptibly();
                            continue;
                        }
                        Ticket ticket = list.removeFirst();
                        myPrint("消费票据: " + ticket.name);
                        notFull.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }, "Cons-" + (i + 1)).start();
        }
    }

    public static void main(String[] args) {
        new ConsumerProducerDemo().start();
    }
}
