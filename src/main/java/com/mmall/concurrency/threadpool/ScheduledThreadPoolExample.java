package com.mmall.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  22:56
 * @description: newScheduledThreadPool  定长线程池，支持定时和周期任务执行
 */
@Slf4j
public class ScheduledThreadPoolExample {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                log.warn("schedule run");
            }
        },3, TimeUnit.SECONDS);

        executorService.shutdown();
    }

}
