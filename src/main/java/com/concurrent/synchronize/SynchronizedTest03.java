package com.concurrent.synchronize;

import org.junit.Test;

/**
 *
 * synchronized练习  (锁消除)
 *
 */
public class SynchronizedTest03 {

    private volatile int i = 0;

    /**
     * JNI 对以下代码进行了优化， 锁消除
     * 因为o是方法内部的变量
     */
    @Test
    public void test() {
        Object o = new Object();
        synchronized (o) {
            i++;
        }
    }
}
