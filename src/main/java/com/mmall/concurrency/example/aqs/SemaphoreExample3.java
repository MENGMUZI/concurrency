package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  09:54
 * @description: tryAcquire
 *
 *  尝试获取许可，如果获取不成功，则放弃操作
 *  tryAcquire() : boolean
 *  tryAcquire(int permits) : boolean  尝试获取指定数量的许可
 *  tryAcquire(int permits,long timeout,TimeUnit timeUnit) : boolean
 *  tryAcquire(long timeout,TimeUnit timeUnit) : boolean 尝试获取许可的时候可以等待一段时间，在指定时间内未获取到许可则放弃
 *
 */
@Slf4j
public class SemaphoreExample3 {

    private static int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i <threadCount ; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try{
                    //如果获取失败，则不进行操作
                    //semaphore.tryAcquire(5000,TimeUnit.MILLISECONDS)
                    if(semaphore.tryAcquire(5000,TimeUnit.MILLISECONDS)){// 尝试获取一个许可
                        test(threadNum);
                        semaphore.release();// 释放一个许可
                    }
                } catch (Exception e) {
                    log.error("exception",e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        //log.info("finish");
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
