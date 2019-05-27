package com.mmall.concurrency.example.commonUnsafe;
import com.mmall.concurrency.annoations.NotThreadSafe;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  20:49
 * @description: StringBuffer是线程安全的类
 */
/**
 * 总结
 * ​   通过以上两个例子可以知道，StringBuffer为线程安全类，StringBuilder为线程不安全类。
 * ​   1.StringBuffer在方法的实现上使用了synchronized关键字对方法进行同步，因此是线程安全的，
 *      而StringBuilder则没有进行特殊的同步或并发处理。
 * ​   2.StringBuffer使用了同步锁，同一时间只能有一个线程进行访问，因为在系统性能会有损耗，适用于多线程环境下使用。
 *      通常情况下，字符串拼接出现在方法内，使用StringBuilder进行字符串的拼接会大大提高性能，属于堆栈封闭，
 *      单个线程的操作对象，因此不存在线程不安全问题，优先选择使用StringBuilder。
 *      两种字符串拼接类分别适用不同的场景，这就是为什么JAVA同时提供了这两种类。
 * */
@Slf4j
@ThreadSafe
public class StringExample2 {
    //请求总数
    public static int clientTotal =5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;
    //计数
    public static StringBuffer stringBuffer = new StringBuffer();
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    update();
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
            e.printStackTrace();
        }
        executorService.shutdown();
        log.info("size:{}",stringBuffer.length());
    }
    private static void update(){
        stringBuffer.append("1");
    }
}
