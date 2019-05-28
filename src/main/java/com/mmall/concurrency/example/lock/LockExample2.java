package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : mengmuzi
 * create at:  2019-05-26  17:40
 * @description: ReentrantLock
 */
@Slf4j
@ThreadSafe
public class LockExample2 {
    //请求总数
    public static int clientTotal =5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;
    //计数
    public static int count = 0;
    //声明锁的实例,调用构造方法，默认生成一个不公平的锁
    private final static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("exception",e);
        }
        executorService.shutdown();
        log.info("count:{}",count);
    }
    private static void add(){
        //上锁
        lock.lock();
        try{
            count++;
        }finally {
            //解锁
            lock.unlock();
        }

    }

}
