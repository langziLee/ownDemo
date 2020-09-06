package com.jvmlearn.objectheader;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 对象头测试
 */
@Slf4j(topic = "learn")
public class ObjectHeaderTest {

    // 关闭延迟开启偏向锁
    // -XX:BiasedLockingStartupDelay=0
    // 禁止偏向锁
    // -XX:-UseBiasedLocking
    // 启用偏向锁
    // -XX:+UseBiasedLocking

    static O o = new O();

    // 当前配置  -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
    public static void main(String[] args) throws InterruptedException {

        // 打印对象信息 一个对象是8的整数倍字节 （对象头、实例数据、对齐填充）
        // 对象头占12个字节（96bit）
        // 前8个字节是 mark work     后4个字节是 klass potint (存类在元空间的初始地址信息)
        // mark work （64bit）   1 没有用到  2~5 分代年龄  6 是否可偏向    7~8 锁的状态

        /*
        无锁可偏向  （没hash）
        05 00 00 00 (00000101 00000000 00000000 00000000) (5)
        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
//        log.debug(ClassLayout.parseInstance(o).toPrintable());

        /*
        无锁不可偏向  （有hash）
        01 bd 2a 0e (00000001 10111101 00101010 00001110) (237681921)
        7c 00 00 00 (01111100 00000000 00000000 00000000) (124)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
//        log.debug(Integer.toHexString(o.hashCode()));
//        log.debug(ClassLayout.parseInstance(o).toPrintable());


//        synchronized (o) {
//            /*
//            偏向锁已偏向
//            05 e8 f2 00 (00000101 11101000 11110010 00000000) (15919109)
//            00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//            98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
//            */
//            log.debug(ClassLayout.parseInstance(o).toPrintable());
//        }
//        /*
//        继续存储原线程ID
//        05 e8 f2 00 (00000101 11101000 11110010 00000000) (15919109)
//        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
//        */
//        log.debug(ClassLayout.parseInstance(o).toPrintable());



//        log.debug(Integer.toHexString(o.hashCode()));
//        synchronized (o) {
//            /*
//            直接升级为轻量锁
//            80 f5 9d 02 (10000000 11110101 10011101 00000010) (43906432)
//            00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//            98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
//            */
//            log.debug(ClassLayout.parseInstance(o).toPrintable());
//        }

//        /*
//        无锁不可偏向  （有hash）
//        01 bd 2a 0e (00000001 10111101 00101010 00001110) (237681921)
//        7c 00 00 00 (01111100 00000000 00000000 00000000) (124)
//        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
//        */
//        log.debug(ClassLayout.parseInstance(o).toPrintable());


        /*
        偏向锁已偏向
        05 e8 f2 00 (00000101 11101000 11110010 00000000) (15919109)
        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
        Thread thread01 = new Thread(() -> { sync(); });
        thread01.start();
        /*
        线程二，轻量锁
        b8 f3 20 20 (10111000 11110011 00100000 00100000) (539030456)
        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
        Thread thread02 = new Thread(() -> {
            try {
                thread01.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sync(); });
        thread02.start();
        thread02.join();
        /*
        无锁不可偏向  （有hash）
        01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
        log.debug(Thread.currentThread().getId() + "-------------" +ClassLayout.parseInstance(o).toPrintable());

//        /*
//        重量锁
//        7a 32 74 1c (01111010 00110010 01110100 00011100) (477377146)
//        7c 00 00 00 (01111100 00000000 00000000 00000000) (124)
//        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
//        */
//        Thread thread01 = new Thread(() -> { sync(); });
//        Thread thread02 = new Thread(() -> { sync(); });
//        thread01.start();
//        thread02.start();
//        thread01.join();
//        thread02.join();
//        /*
//        重量锁
//        7a 32 74 1c (01111010 00110010 01110100 00011100) (477377146)
//        7c 00 00 00 (01111100 00000000 00000000 00000000) (124)
//        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
//        */
//        log.debug(ClassLayout.parseInstance(o).toPrintable());
    }

    public static void sync() {
        synchronized (o) {
            for (int i=0; i<1000; i++) {
                int j = 0; j--;
            }
            log.debug(Thread.currentThread().getId() + "-------------" +ClassLayout.parseInstance(o).toPrintable());
        }
    }

}
