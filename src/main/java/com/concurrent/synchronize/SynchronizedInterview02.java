package com.concurrent.synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * synchronized练习  (面试题：保证线程的顺序)
 * 使用synchronized
 */
@Slf4j(topic = "learn")
public class SynchronizedInterview02 {

    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {

        AtomicBoolean first = new AtomicBoolean(false);

        Thread wThread01 = new Thread(() -> {

            while (!first.get()) {
                synchronized (object) {
                    try {
                        log.debug(Thread.currentThread().getName() + "等待");
                        object.wait();
                        log.debug(Thread.currentThread().getName() + "试试");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            log.debug(Thread.currentThread().getName() + "执行完");

        }, "Thread01");

        Thread wThread02 = new Thread(() -> {
            synchronized (object) {
                log.debug(Thread.currentThread().getName() + "执行完");
                first.set(true);
                object.notifyAll();
            }
        }, "Thread02");


        wThread01.start();
        TimeUnit.SECONDS.sleep(1);
        wThread02.start();
    }
}
