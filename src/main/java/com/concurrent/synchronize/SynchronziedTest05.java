package com.concurrent.synchronize;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * synchronized练习
 *
 * 1、死锁 至少两个锁对象，并且相互嵌套
 */
@Slf4j(topic = "learn")
public class SynchronziedTest05 {


    private static Object o1 = new Object();
    private static Object o2 = new Object();


    public static void main(String[] args) {


        Thread wThread01 = new Thread(() -> {
            sync01();
        }, "Thread01");

        Thread wThread02 = new Thread(() -> {
            sync02();
        }, "Thread02");

        wThread01.start();
        wThread02.start();
    }


    public static void sync01() {

        synchronized (o1) {
            log.debug("线程：" + Thread.currentThread().getName() + "sync01执行");
            try {
                Thread.sleep(3000);
                synchronized (o2) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("线程：" + Thread.currentThread().getName() + "sync01执行完");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sync02() {

        synchronized (o2) {
            log.debug("线程：" + Thread.currentThread().getName() + "sync02执行");
            try {
                Thread.sleep(3000);
                synchronized (o1) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("线程：" + Thread.currentThread().getName() + "sync02执行完");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
