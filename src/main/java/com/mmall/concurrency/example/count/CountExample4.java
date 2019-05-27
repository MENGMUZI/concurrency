package com.mmall.concurrency.example.count;

import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author : mengmuzi
 * create at:  2019-05-26  17:40
 * @description: volatile加入count 线程不安全
 */
/**
 *  private static void add() {
 *      count++; //分3步
 *      //1.取出当前count值
 *      //2.count + 1
 *      //3.count 重新写回主存
 *  }
 *  假设同时有两个线程进行操作，两个线程同时执行到第一步（从内存中读取最新值）得到一样的最新的结果，
 *  然后进入第二步（+1操作）并进行第三步（从新写回主存）。
 *  尽管第一步获取的值是一样的，但是同时将+1后的操作写回主存，这样就会丢掉某个+1的操作，这样就会出现线程不安全问题
 *
 *
 *  结论：
 * volatile进行加操作线程不安全的，不适合计数场景
 * volatile关键字不具有原子性
 * 使用场景
 *   使用volatile必须具备两个条件
 *
 * 对变量的写操作，不依赖于当前值
 * 该变量没有包含在具有其他变量的不变式子中
 * 因此volatile适合作为状态的标记量
 * */
@Slf4j
@NotThreadSafe
public class CountExample4 {
    //请求总数
    public static int clientTotal =5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;
    //计数
    public static volatile int count = 0;
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
            e.printStackTrace();
        }
        executorService.shutdown();
        log.info("count:{}",count);
    }
    private static void add(){
        count++;
    }

}
