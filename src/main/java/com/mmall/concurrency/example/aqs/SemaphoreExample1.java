package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  09:54
 * @description: Semaphore来做流控
 *
 */
@Slf4j
public class SemaphoreExample1 {

    private static int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i <threadCount ; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try{
                    semaphore.acquire();// 获取一个许可
                    test(threadNum);
                    semaphore.release();// 获取一个许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        log.info("finish");
        executorService.shutdown();

    }
    private static void test(int threadNum){
        //暂停一会儿线程
        //try{ TimeUnit.MILLISECONDS.sleep(200);}catch(Exception e){e.printStackTrace();}
        //暂停一会儿线程
        log.info("{}",threadNum);
        //暂停一会儿线程
        try{ TimeUnit.SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
    }

}
