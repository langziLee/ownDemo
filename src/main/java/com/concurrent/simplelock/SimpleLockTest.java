package com.concurrent.simplelock;

import org.junit.Test;

public class SimpleLockTest {

    private int count = 0;

    private SimpleLock mSimpleLock = new SimpleLock();

    @Test
    public void test() throws InterruptedException {

        Thread wThread1 = new Thread(()-> sum());
        Thread wThread2 = new Thread(()-> sum());
        Thread wThread3 = new Thread(()-> sum());

        wThread1.start();
        wThread2.start();
        wThread3.start();

        wThread1.join();
        wThread2.join();
        wThread3.join();

        System.out.println("执行结束count的值：" + count);
    }

    private void sum () {

        mSimpleLock.lock();

        for (int i=0; i<10000; i++) {
            count = count + 1;
        }

        mSimpleLock.unLock();
    }
}
