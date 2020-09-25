package com.concurrent.synchronize;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * synchronized练习  (面试题：保证线程的顺序)
 */
@Slf4j(topic = "learn")
public class SynchronizedInterview01 {


    public static void main(String[] args) throws InterruptedException {

        AtomicBoolean first = new AtomicBoolean(false);

        Thread wThread01 = new Thread(() -> {

            while (true) {
                if (first.get()) {
                    log.debug(Thread.currentThread().getName() + "执行完");
                    break;
                }
            }
        }, "Thread01");

        Thread wThread02 = new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "执行完");
            first.set(true);
        }, "Thread02");


        wThread01.start();
        TimeUnit.SECONDS.sleep(1);
        wThread02.start();
    }
}
