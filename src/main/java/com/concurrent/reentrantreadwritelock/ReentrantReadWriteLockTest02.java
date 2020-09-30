package com.concurrent.reentrantreadwritelock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  ReentrantReadWrite
 *  (验证只有读读线程在一起才会共享， 如果是读写读线程， 两个读是不会共享的 )
 */
@Slf4j(topic = "learn")
public class ReentrantReadWriteLockTest02 {
    //读写锁
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    public static void main(String[] args) throws InterruptedException {

        /**
         * t1  最先拿到写（W）锁 然后睡眠了5s
         * 之后才会叫醒别人
         */
        new Thread(() -> {
            w.lock();
            try {
                log.debug("t1 +");
                TimeUnit.SECONDS.sleep(5);
                log.debug("5s 之后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                w.unlock();
            }
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);


        /**
         * t1在睡眠的过程中 t2不能拿到 读写互斥
         * t2 一直阻塞
         */
        new Thread(() -> {
            try {
                r.lock();
                log.debug("t2----+-------");
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {

                e.printStackTrace();
            } finally {
                log.debug("t2-----jian-------");
                r.unlock();
            }
        }, "t2").start();

        TimeUnit.SECONDS.sleep(1);


        /**
         * t1在睡眠的过程中 t3不能拿到 读写互斥
         * t3 一直阻塞
         *
         * 当t1释放锁之后 t3和t2 能同时拿到锁
         * 读读并发
         */
        new Thread(() -> {
            try {
                r.lock();
                log.debug("t3----+-------");
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t3----释放-------");

                r.unlock();
            }
        }, "t3").start();


        /**
         * 拿写锁
         * t1睡眠的时候 t4也页阻塞
         * 顺序应该 t2 t3  t4
         */
        new Thread(() -> {
            try {
                w.lock();
                log.debug("t4--------+---");
                TimeUnit.SECONDS.sleep(10);
                log.debug("t4--------醒来---");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t4--------jian---");
                w.unlock();
            }
        }, "t4").start();


        /**
         *
         * t5 是读锁
         * 他会不会和t2 t3 一起执行
         */
        new Thread(() -> {
            try {
                r.lock();
                log.debug("t5--------+---");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t5--------jian---");
                r.unlock();
            }
        }, "t5").start();
    }
}
