package com.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * AtomicInteger的使用
 */

@Slf4j(topic = "learn")
public class AtomicIntegerTest {

    // 余额
    private static AtomicInteger balance = new AtomicInteger(20000);

    public static void main(String[] args) throws InterruptedException {

        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i <100 ; i++) {
            ts.add(new Thread(() -> pull(200)));
        }
        //启动
        for (Thread t : ts) {
            t.start();
        }
        //等待他们全部执行完
        for (Thread t : ts) {
            t.join();
        }

        log.debug("余额：" + balance);
    }


    private static void pull(Integer amount) {

        // 方法一
        balance.getAndAdd(0 - amount);


        // 方法二
//        while (true) {
//            int source = balance.get();
//            int next = source - amount;
//            if (balance.compareAndSet(source, next)) {
//                break;
//            }
//        }


    }

}
