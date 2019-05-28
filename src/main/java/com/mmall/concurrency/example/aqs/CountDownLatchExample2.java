package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  09:54
 * @description: CountDownLatch
 * CountDownLatch还提供在指定时间内完成的条件（超出时间没有完成，完成多少算多少），如果等待时间没有完成，则继续执行。
 * 通过countDownLatch.await(int timeout,TimeUnit timeUnit);设置，第一个参数没超时时间，第二个参数为时间单位
 *
 */
@Slf4j
public class CountDownLatchExample2 {

    private static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i <threadCount ; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try{
                    test(threadNum);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10,TimeUnit.MILLISECONDS);
        log.info("finish");
        executorService.shutdown();

    }
    private static void test(int threadNum){
        //暂停一会儿线程
        try{ TimeUnit.MILLISECONDS.sleep(200);}catch(Exception e){e.printStackTrace();}
        //暂停一会儿线程
        log.info("{}",threadNum);
    }

}
