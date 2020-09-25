package com.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock （打断案例）
 * synchronized 不可被打断
 */
@Slf4j(topic = "learn")
public class ReentrantLockTest01 {


    private static ReentrantLock mReentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        /**
         * wThread01 优先获取锁
         */
        Thread wThread01 = new Thread(() -> {
            try {
                mReentrantLock.lock();
                log.debug(Thread.currentThread().getName() + "开始执行");
                TimeUnit.SECONDS.sleep(5);
                log.debug(Thread.currentThread().getName() + "执行完");
            } catch (InterruptedException e) {
                log.debug(Thread.currentThread().getName() + "被打断了");
                e.printStackTrace();
            } finally {
                log.debug(Thread.currentThread().getName() + "释放锁");
                mReentrantLock.unlock();
            }
        }, "Thread01");

        wThread01.start();
        TimeUnit.SECONDS.sleep(1);

        Thread wThread02 = new Thread(() -> {
            try {
                mReentrantLock.lock(); // 获取到了锁, 之后释放锁(不可以被打断)
                // mReentrantLock.lockInterruptibly(); // 获取不到锁， 会直接被打断， 没有锁所以释放锁会报错(可以被打断)
                log.debug(Thread.currentThread().getName() + "开始执行");
                log.debug(Thread.currentThread().getName() + "执行完");
            } catch (Exception e) {
                log.debug(Thread.currentThread().getName() + "被打断了没有获取锁");
                e.printStackTrace();
            } finally {
                log.debug(Thread.currentThread().getName() + "释放锁");
                mReentrantLock.unlock();
            }
        }, "Thread02");

        wThread02.start();

        //由于wThread02 可以被打断 故而1s之后打断wThread02 不在等待wThread01释放锁了
        try {
            log.debug("主线程------1s后打断wThread02");
            TimeUnit.SECONDS.sleep(1);
            wThread02.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
