package com.concurrent.semapore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore 信号量的使用
 *
 */
@Slf4j(topic = "learn")
public class SemaphoreTest01 {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);

        for (int i=0; i<15; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.debug("线程{}, 开始执行", Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log.debug("线程{}, 释放", Thread.currentThread().getName());
                    semaphore.release();
                }
            }).start();
        }



    }
}
