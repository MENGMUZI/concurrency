package com.mmall.concurrency.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  22:52
 * @description: newFixedThreadPool  定长线程池，可以线程现成的最大并发数，超出在队列等待
 */
@Slf4j
public class FixedThreadPoolExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(()->{
                log.info("task:{}",index);
            });
        }
        executorService.shutdown();
    }

}
