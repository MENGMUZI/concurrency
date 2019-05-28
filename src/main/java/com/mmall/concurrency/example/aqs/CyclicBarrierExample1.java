package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  10:35
 * @description: CyclicBarrier
 *
 * 当某个线程调用await()方法之后，该线程就进入等待状态，而且计数器是执行加一操作，当计数器值达到初始值（设定的值），
 * 因为调用await()方法进入等待的线程，会被唤醒，继续执行他们后续的操作。由于CyclicBarrier在等待线程释放之后，可
 * 以进行重用，所以称之为循环屏障。
 *
 */
@Slf4j
public class CyclicBarrierExample1 {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
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
