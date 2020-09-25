package com.concurrent.synchronize;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *
 * synchronized练习
 *
 * 1、synchronized锁的是对象（实际锁改变的就是对象头）
 * 2、锁属性的改变不影响锁的使用，锁对象改变会影响
 */
@Slf4j(topic = "learn")
public class SynchronizedTest04 {

    private Demo mDemo = new Demo();


    @SneakyThrows
    private void sync() {
        synchronized (mDemo) {
            int i = 0;
            while (true) {
                Thread.sleep(1000);
                System.out.println("线程" + Thread.currentThread().getName() + "执行次数" + (++i));
            }
        }
    }

    @Test
    public void test() throws InterruptedException {

        SynchronizedTest04 synchronizedTest04 = new SynchronizedTest04();

        new Thread(synchronizedTest04 :: sync, "Thread01").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread wThread02 = new Thread(synchronizedTest04 :: sync, "Thread02");

        // 1、锁属性的改变
        synchronizedTest04.mDemo.setName("Lee");

        // 2、锁对象改变
        synchronizedTest04.mDemo = new Demo();

        wThread02.start();
        wThread02.join();
    }


    class Demo{

        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
