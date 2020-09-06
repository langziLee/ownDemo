package com.concurrent.reentrantlock;


public class ReentrantLockTest {

    public static void main(String[] args) {

        // 同ReentrantLock
        // ReentrantLock默认是非公平锁 ,  new ReentrantLock(true) 这样则为公平锁
        // 非公平锁



        ReentrantLock01 lock = new ReentrantLock01();

        // 加锁
        lock.lock();

    }
}
