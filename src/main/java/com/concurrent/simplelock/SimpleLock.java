package com.concurrent.simplelock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * 简易的锁
 */
public class SimpleLock {

    // 获取Unsafe
    private static final Unsafe unsafe = getUnsafe();

    // 上锁状态  0  1
    private volatile int status = 0;

    //定义一个变量来记录 volatile int status的地址
    //因为CAS需要的是一个地址,于是就定义这个变量来标识status在内存中的地址
    private static long valueOffset = 0;

    /**
     * 初始化的获取status在内置的偏移量
     * 说白了就是status在内存中的地址
     * 方便后面对他进行CAS操作
     */
    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                    (SimpleLock.class.getDeclaredField("status"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    public void lock() {
        while (!compareAndSwap(0, 1)) {

            //加锁失败会进入到这里空转
        }
    }

    public void unLock() {
        status = 0;
    }

    private boolean compareAndSwap(int source, int end) {
        //如果 this的valueOffset位置的status变量值 = source 那么改成 end
        return unsafe.compareAndSwapInt(this, valueOffset, source, end);
    }


    /**
     * 获取Unsafe对象
     * @return
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe)field.get(null);

        } catch (Exception e) {
        }
        return null;
    }

}
