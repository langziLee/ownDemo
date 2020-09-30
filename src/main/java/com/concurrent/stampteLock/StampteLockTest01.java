package com.concurrent.stampteLock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * StampteLock （读读 、 读写案例）
 */
@Slf4j(topic = "learn")
public class StampteLockTest01 {

    static long stampW = 0;
    static StampedLock stampteLock = new StampedLock();

    @SneakyThrows
    public static void main(String[] args) {

        // 验证戳被改变
        new Thread(() -> {
            read();
        }, "Thread1").start();

        TimeUnit.MICROSECONDS.sleep(100);
        new Thread(() -> {
            write();
        }, "Thread2").start();

        // 为了验证读读
        TimeUnit.SECONDS.sleep(3);
        new Thread(() -> {
            read();
        },"Thread3").start();
    }

    @SneakyThrows
    private static void read() {

        // 尝试一次乐观度
        long stamp = stampteLock.tryOptimisticRead();
        log.debug("StampedLock 线程 {}  tryOptimisticRead拿到的戳{}", Thread.currentThread().getName(), stamp);
        TimeUnit.SECONDS.sleep(1);
        // 验戳
        if (stampteLock.validate(stamp)) {
            log.debug("StampedLock 检验有效性, 线程 {} 拿到的戳{}", Thread.currentThread().getName(), stamp);
            return;
        }

        log.debug("检验失败, 被写线程改变了" + stampW);

        try {
            stamp = stampteLock.readLock();
            log.debug("StampedLock 线程 {}  readLock拿到的戳{}", Thread.currentThread().getName(), stamp);
            TimeUnit.SECONDS.sleep(1);
        } finally {
            log.debug("StampedLock unlockRead释放锁");
            stampteLock.unlockRead(stamp);
        }
    }


    @SneakyThrows
    private static void write() {

        try {
            stampW = stampteLock.writeLock();
            log.debug("StampedLock 线程 {}  writeLock拿到的戳", Thread.currentThread().getName(), stampW);
            TimeUnit.SECONDS.sleep(2);
        } finally {
            log.debug("StampedLock unlockWrite释放锁");
            stampteLock.unlockWrite(stampW);
        }
    }
}
