package com.concurrent.synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 *
 * synchronized练习  (面试题：保证线程的顺序)
 * 使用lock
 */
@Slf4j(topic = "learn")
public class SynchronizedInterview03 {

    public static void main(String[] args) throws InterruptedException {

        AtomicBoolean first = new AtomicBoolean(false);

        Thread wThread01 = new Thread(() -> {

            while (!first.get()) {
                LockSupport.park();
            }
            log.debug(Thread.currentThread().getName() + "执行完");
        }, "Thread01");

        Thread wThread02 = new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "执行完");
            first.set(true);
            LockSupport.unpark(wThread01);
        }, "Thread02");


        wThread01.start();
        TimeUnit.SECONDS.sleep(1);
        wThread02.start();
    }
}
