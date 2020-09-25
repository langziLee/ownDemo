package com.concurrent.synchronize;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * synchronized练习  (面试题：保证线程的顺序)
 *
 * 三个线程并发顺序打印 a b c  (打印四次)
 * ReentrantLock版
 *
 */
@Slf4j(topic = "learn")
public class SynchronizedInterview05 {


    public static void main(String[] args) throws InterruptedException {

        ReentrantLock mReentrantLock = new ReentrantLock();
        Condition mCondition = mReentrantLock.newCondition();
        Sync sync = new Sync();

        Thread wThread01 = new Thread(() -> {
            sync.print("a", 1, 2, mReentrantLock, mCondition);
        }, "Thread01");

        Thread wThread02 = new Thread(() -> {
            sync.print("b", 2, 3, mReentrantLock, mCondition);
        }, "Thread02");

        Thread wThread03 = new Thread(() -> {
            sync.print("c", 3, 1, mReentrantLock, mCondition);
        }, "Thread03");

        wThread03.start();
        TimeUnit.SECONDS.sleep(1);
        wThread01.start();
        TimeUnit.SECONDS.sleep(1);
        wThread02.start();
    }


    static class Sync {

        private AtomicInteger fag = new AtomicInteger(1);

        private AtomicInteger count = new AtomicInteger(0);

        @SneakyThrows
        public void print(String str, int waitTag, int nextTag, ReentrantLock reentrantLock, Condition condition) {

            while (true){

                try {
                    reentrantLock.lock();

                    while (fag.get() != waitTag) {
                        condition.await();
                    }
                    log.debug(Thread.currentThread().getName() + "打印:" + str);
                    fag.getAndSet(nextTag);

                    if (waitTag == 1) {
                        count.getAndAdd(1);
                        if (count.get() == 4) {
                            break;
                        }
                    }
                    condition.signalAll();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }
    }
}
