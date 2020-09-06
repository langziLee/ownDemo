package com.concurrent.synchronize;

import com.jvmlearn.objectheader.O;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * synchronized练习  （ 批量重偏向   过期）
 */
@Slf4j(topic = "learn")
public class SynchronizedTest02 {


    private static List<O> list = new ArrayList<>();

    private static Thread mThread1;
    private static Thread mThread2;
    private static Thread mThread3;
    private static Thread mThread4;

    // 当前配置  -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
    public static void main(String[] args) throws InterruptedException {

        mThread1 = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                O o = new O();
                log.debug(i + "mThread1-------------" + ClassLayout.parseInstance(o).toPrintable());
                synchronized (o) {
                    list.add(o);
                    log.debug(i + "mThread1-------------" + ClassLayout.parseInstance(o).toPrintable());
                }
                log.debug(i + "mThread1-------------" + ClassLayout.parseInstance(o).toPrintable());
            }
            LockSupport.unpark(mThread4);
        });

        /*
        05 50 38 20 (00000101 01010000 00111000 00100000) (540561413)
        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
//        mThread2 = new Thread(() -> {
//        LockSupport.park();
//        log.debug("--------------------------------------------------------------");
//        log.debug("--------------------------------------------------------------");
//        log.debug("--------------------------------------------------------------");
//            for (int i = 0; i < 20; i++) {
//                O o = list.get(i);
//                log.debug(i + "-------------" + ClassLayout.parseInstance(o).toPrintable());
//                synchronized (o) {
//                    log.debug(i + "-------------" + ClassLayout.parseInstance(o).toPrintable());
//                }
//                log.debug(i + "-------------" + ClassLayout.parseInstance(o).toPrintable());
//            }
//        });
//        mThread2.start();
//        mThread2.join();

        /*
         * 批量重偏向 (一个类中的对象，进行了20次撤销，第21次就是偏向锁)
         *
         * 1-20条的三个报文头 （只显示第一行）
         * 05 f9 c9 1f (00000101 11111001 11001001 00011111) (533330181)
         * e8 ef 3d 20 (11101000 11101111 00111101 00100000) (540930024)
         * 01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *
         * 21-40条的三个报文头 （只显示第一行）
         * 05 d0 c9 1f (00000101 11010000 11001001 00011111) (533319685)
         * 05 d0 c9 1f (00000101 11010000 11001001 00011111) (533319685)
         * 05 d0 c9 1f (00000101 11010000 11001001 00011111) (533319685)
         */
//        mThread3 = new Thread(() -> {
//            LockSupport.park();
//            log.debug("--------------------------------------------------------------");
//            log.debug("--------------------------------------------------------------");
//            log.debug("--------------------------------------------------------------");
//
//            for (int i = 0; i < 40; i++) {
//                O o = list.get(i);
//                log.debug(i + "mThread3-------------" + ClassLayout.parseInstance(o).toPrintable());
//                synchronized (o) {
//                    log.debug(i + "mThread3-------------" + ClassLayout.parseInstance(o).toPrintable());
//                }
//                log.debug(i + "mThread3-------------" + ClassLayout.parseInstance(o).toPrintable());
//            }
//        });
//
//        mThread1.start();
//        mThread3.start();
//        mThread1.join();

        mThread4 = new Thread(() -> {
            LockSupport.park();
            log.debug("--------------------------------------------------------------");
            log.debug("--------------------------------------------------------------");
            log.debug("--------------------------------------------------------------");

            for (int i = 0; i < 60; i++) {
                O o = list.get(i);
                log.debug(i + "mThread4-------------" + ClassLayout.parseInstance(o).toPrintable());
                synchronized (o) {
                    log.debug(i + "mThread4-------------" + ClassLayout.parseInstance(o).toPrintable());
                }
                log.debug(i + "mThread4-------------" + ClassLayout.parseInstance(o).toPrintable());
            }
        });

        mThread1.start();
        mThread4.start();
        mThread1.join();

    }
}
