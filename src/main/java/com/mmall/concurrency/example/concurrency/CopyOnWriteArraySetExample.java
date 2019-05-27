package com.mmall.concurrency.example.concurrency;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  01:21
 * @description: CopyOnWriteArraySet
 *
 * ​ HashSet -> CopyOnWriteArraySet
 *
 *  CopyOnWriteArraySet底层实现是采用CopyOnWriteArrayList，合适比较小的集合，其中所有可变操作（add、set、remove等等）
 *  都是通过对底层数组进行一次新的复制来实现的,一般需要很大的开销。迭代器支持hasNext(), next()等不可变操作，
 *  不支持可变的remove操作；使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。
 *  在构造迭代器时，迭代器依赖于不变的数组快照。
 *
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArraySetExample {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static Set<Integer> set = new CopyOnWriteArraySet<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("size:{}", set.size());
    }

    private static void update(int i) {
        set.add(i);
    }
}
