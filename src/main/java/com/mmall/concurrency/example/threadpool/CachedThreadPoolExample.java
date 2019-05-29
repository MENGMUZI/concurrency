package com.mmall.concurrency.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  22:48
 * @description: Executors.newCachedThreadPool
 *  创建一个可缓存的线程池，如果线程池的长度超过了处理的需要，可以灵活回收空闲线程。如果没有可回收的就新建线程。
 */
@Slf4j
public class CachedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(()->{
                log.info("task:{}",index);
            });
        }
        executorService.shutdown();
    }

}
