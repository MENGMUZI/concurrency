package com.mmall.concurrency.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  22:54
 * @description: newSingleThreadExecutor
 * 单线程化的线程池，用唯一的一个共用线程执行任务，保证所有任务按指定顺序执行（FIFO、优先级…）
 */
@Slf4j
public class SingleThreadExecutorExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(()->{
                log.info("task:{}",index);
            });
        }
        executorService.shutdown();
    }

}
