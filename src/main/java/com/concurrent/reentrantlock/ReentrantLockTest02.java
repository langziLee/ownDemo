package com.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * ReentrantLock （验证interrupt）
 */
@Slf4j(topic = "learn")
public class ReentrantLockTest02 {

    private static Object o = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread wThread01 = new Thread(() -> {
            synchronized (o) {
                log.debug(Thread.currentThread().getName() + "开始执行");
                while (true) {
                    int i = 0;
                }
            }
        });
        wThread01.start();

        TimeUnit.SECONDS.sleep(1);

        log.debug("wThread01线程，状态" + wThread01.isInterrupted()); // 线程最初interrupt 是 false
        wThread01.interrupt();   // 中断 interrupt 是 true
        log.debug("wThread01线程，interrupt之后状态" + wThread01.isInterrupted()); //  是 true
//        wThread01.interrupt();
        log.debug("wThread01线程，interrupted状态" + wThread01.interrupted()); // 是 false (判断interrupt标志位，如果是true，则返回false)
        log.debug("wThread01线程，interrupted之后状态" + wThread01.isInterrupted());//  是 true

        log.debug(Thread.currentThread().getName() + "开始执行");

        TimeUnit.SECONDS.sleep(1);
    }
}
