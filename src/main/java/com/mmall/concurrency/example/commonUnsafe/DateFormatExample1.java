package com.mmall.concurrency.example.commonUnsafe;

import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  20:59
 * @description: SimpleDateFormat是JAVA提供的一个日期转换类
 */
/**
 * 当方法运行的时候，则会抛出异常，原因是SimpleDateFormat在多线程下共享使用就会出现线程不安全情况。
 * 建议将SimpleDateFormat声明为局部变量，这样才会避免线程不安全所带来的异常
 * */
@Slf4j
@NotThreadSafe
public class DateFormatExample1 {
    //private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }
    private static void update(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try{
            simpleDateFormat.parse("20190527");
            log.info("it is Ok!");
        }catch(Exception e){
            log.error("parse exception",e);
        }
    }

}
