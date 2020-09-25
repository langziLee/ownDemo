package com.concurrent.guardedsuspend;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "learn")
public class GuardedObject {

    private Object o = new Object();
    private Object mResponse;


    public void setResponse(Object pResponse) {
        synchronized (o) {
            mResponse = pResponse;
            log.debug("设置完成之后唤醒主线程");
            o.notifyAll();
        }
    }



    public void getResponse() {
        synchronized (o) {
            while (null == mResponse) {
                try {
                    log.debug("主线程 获取 response 如果为null则wait");
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 增加超时时间
     *
     * @param pTimeout
     */
    public void getResponse(long pTimeout) {
        synchronized (o) {

            long start = System.currentTimeMillis();
            long timePassed = 0L;

            while (null == mResponse) {

                timePassed = System.currentTimeMillis() - start;
                if (pTimeout <= timePassed) {
                    log.debug("超时了, 不等了");
                    break;
                }

                try {
                    log.debug("主线程 获取 response 如果为null则wait");
                    o.wait(pTimeout - timePassed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
