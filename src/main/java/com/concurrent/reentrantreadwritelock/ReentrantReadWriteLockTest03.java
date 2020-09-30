package com.concurrent.reentrantreadwritelock;


import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j(topic = "learn")
public class ReentrantReadWriteLockTest03 {

    //读写锁
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock r = rwl.readLock();
    static Lock w0 = rwl.writeLock();
    static Lock w1 = rwl.writeLock();

    public static void main(String[] args) throws InterruptedException {



//        // 网上说读写锁不支持锁的升级其实并不绝对，以下这种就可以
//        // 因为一上来的写锁，保证了该锁对象不会被其他线程共享。之后的读锁、写锁都是线程安全的
//        new Thread(() -> {
//            try {
//                w0.lock();
//                r.lock();
//                w1.lock();
//                log.debug("执行成功");
//            } finally {
//                w1.unlock();
//                r.unlock();
//                w0.unlock();
//            }
//        }).start();

        // 不支持直接读锁 再写锁的这种升级。 因为会出现死锁。 tryAcquire永远返回false
//        new Thread(() -> {
//            try {
//                r.lock();
//                w1.lock();
//                log.debug("执行成功");
//            } finally {
//                w1.unlock();
//                r.unlock();
//            }
//        }).start();


        // 验证共享锁
        new Thread(() -> {
            try {
                r.lock();
                while (true)
                    log.debug(Thread.currentThread().getName() + "执行成功");
            } finally {
                r.unlock();
            }
        }, "Thread01").start();
        new Thread(() -> {
            try {
                r.lock();
                while (true)
                    log.debug(Thread.currentThread().getName() + "执行成功");
            } finally {
                r.unlock();
            }
        }, "Thread02").start();
    }
}
