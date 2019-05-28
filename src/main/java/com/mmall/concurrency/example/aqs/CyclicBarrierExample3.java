package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  10:35
 * @description: CyclicBarrier
 *
 * 除了指定屏障数外，指定一个Runnable任务，
 * 意味着：在线程到达屏障时，优先执行Runnable任务，方便处理更复杂的业务场景
 *
 */
@Slf4j
public class CyclicBarrierExample3 {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5,()->{
        //当达到线程屏障数5时，执行任务
        //每满足一次屏障数，则执行
        log.info("callback is running");
    });
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i <10 ; i++) {
            final int threadNum = i;
            //暂停一会儿线程
            try{ TimeUnit.SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
            executorService.execute(()->{
                try {
                    race(threadNum);
                } catch (BrokenBarrierException e) {
                    log.error("exception", e);
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int threadNum) throws BrokenBarrierException, InterruptedException {
        //暂停一会儿线程
        try{ TimeUnit.SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
        log.info("{} is ready", threadNum);
        //如果当前线程就绪，则告诉CyclicBarrier 需要等待
        cyclicBarrier.await();
        // 当达到指定数量时，继续执行下面代码
        log.info("{} continue",threadNum);
    }

}
