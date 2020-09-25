package com.concurrent.reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock的例子1
 */
public class ReentrantReadWriteLockExample01 {

    /**
     * 缓存数据
     */
    class CachedData {
        Object data;
        volatile boolean cacheValid; //  true 正常    false 过期
        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        /**
         * 处理缓存
         */
        void processCachedData() {
            // 读锁
            rwl.readLock().lock();
            // 判断是否过期 过期则
            if (!cacheValid) {
                // 读锁关闭，因为要进行写锁
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    // 判断是否过期 过期则处理
                    if (!cacheValid) {
                        // 数据的处理
                        //data = ...
                        cacheValid = true;
                    }
                    // 写锁释放之前，对读锁进行加锁， 降级
                    rwl.readLock().lock();
                } finally {
                    rwl.writeLock().unlock();
                }
            }

            try {
                //use(data);
            } finally {
                rwl.readLock().unlock();
            }
        }
    }
}
