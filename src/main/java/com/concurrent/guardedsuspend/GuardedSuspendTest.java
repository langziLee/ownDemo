package com.concurrent.guardedsuspend;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 保护暂停模式
 * join、feture的简版
 * 某个结果需要在多线程之间传递，则可以让这些线程关联到一个对象 GuardedObject
 * 但是如果这个结果需要不断的从一个线程到另一个线程那么可以使用消息队列（见生产者/消费者）
 */
@Slf4j(topic = "learn")
public class GuardedSuspendTest {

    private GuardedObject mGuardedObject = new GuardedObject();

    @Test
    public void test01() {

        Thread wThread01 = new Thread(() -> {

            log.debug("开始执行Thread");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mGuardedObject.setResponse("5000");
        });
        wThread01.start();

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        mGuardedObject.getResponse();
        log.debug("Main开始执行");
    }
}
