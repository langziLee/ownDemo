package com.jvmlearn.objectheader;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象头测试2  （测试分代年龄）
 */
@Slf4j(topic = "learn")
public class ObjectHeaderTest2 {


    static O o = new O();
    static List<char[]> list = new ArrayList<char[]>();

    // 当前配置  -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
    public static void main(String[] args) throws InterruptedException {

        /*
        分代年龄是6
        35 00 00 00 (00110101 00000000 00000000 00000000) (53)
        00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        98 87 01 f8 (10011000 10000111 00000001 11111000) (-134117480)
        */
        new Thread(() -> {
            while (true) {
                char[] a = new char[1024];
                list.add(a);
                log.debug(ClassLayout.parseInstance(o).toPrintable());
            }
        }).start();
    }
}
