package com.concurrent.synchronize;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;

/**
 *
 * synchronized练习
 *
 * 1、活锁 (可能一直无法停止)
 */
@Slf4j(topic = "learn")
public class SynchronziedTest06 {


    private static Object o1 = new Object();
    private static Object o2 = new Object();
    private static int count = 0;

    public static void main(String[] args) {


        Thread wThread01 = new Thread(() -> {
            while (count <= 200) {
                log.debug("线程：" + Thread.currentThread().getName() + "执行完" + count);
                try {
                    TimeUnit.NANOSECONDS.sleep(2000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread01");

        Thread wThread02 = new Thread(() -> {
            while (count >= 0) {
                log.debug("线程：" + Thread.currentThread().getName() + "执行完" + count);
                try {
                    TimeUnit.NANOSECONDS.sleep(2000);
                    count--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread02");

        wThread01.start();
        wThread02.start();
    }
}
