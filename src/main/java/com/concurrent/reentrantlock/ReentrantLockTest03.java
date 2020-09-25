package com.concurrent.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock
 * 主要是验证signal方法会转移node（线程）到主队列
 * 先进先出  4231
 */
@Slf4j(topic = "learn")
public class ReentrantLockTest03 {


    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        // 条件队列
        Condition wait = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("t1 去await");
                wait.await();
                log.debug("t1 醒来");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        // t1 最先得到锁 然后去wait队列当中阻塞  释放了锁

        // t4 启动 获取到锁 睡眠5s 没有释放锁 5s之后 把t1 唤醒
        new Thread(() -> {
            try {
                lock.lock();
                log.debug("t4--------获取锁---");
                TimeUnit.SECONDS.sleep(5);
                wait.signal();
                log.debug("交给t1");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t4").start();

        TimeUnit.SECONDS.sleep(1);

        // 拿锁失败  主队列当中阻塞
        new Thread(() -> {
            try {
                lock.lock();
                log.debug("t2----拿到锁-------");
            } catch (Exception e) {

                e.printStackTrace();
            } finally {
                log.debug("t2----释放-------");
                lock.unlock();
            }
        }, "t2").start();

        // 顺序启动  顺序入队
        TimeUnit.SECONDS.sleep(1);

        // 拿锁失败  主队列当中阻塞
        new Thread(() -> {
            try {
                lock.lock();
                log.debug("t3----拿到锁-------");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t3----释放-------");
                lock.unlock();
            }
        }, "t3").start();
    }
}
