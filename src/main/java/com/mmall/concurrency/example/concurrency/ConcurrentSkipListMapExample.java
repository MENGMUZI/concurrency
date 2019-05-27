package com.mmall.concurrency.example.concurrency;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  01:34
 * @description: ConcurrentSkipListMap
 * ​ TreeMap -> ConcurrentSkipListMap
 *  内部使用``SkipList`结构实现的。跳表是一个链表，但是通过使用“跳跃式”查找的方式使得插入、读取数据时复杂度变成了O（log n）。
 */

/** 总结比较：concurrentHashMap与ConcurrentSkipListMap性能测试
 * ​ 1.在4线程1.6万数据的条件下，ConcurrentHashMap 存取速度是ConcurrentSkipListMap 的4倍左右。
 *  2.但ConcurrentSkipListMap有几个ConcurrentHashMap不能比拟的优点：
 *      * ConcurrentSkipListMap 的key是有序的，而ConcurrentHashMap是做不到的
 *      * ConcurrentSkipListMap 支持更高的并发。ConcurrentSkipListMap的存取时间是log（N），和线程数几乎无关。
 *        也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。
 *
 *  在非多线程情况下，尽量使用`TreeMap`，此外，对于并发性较低的程序，可以使用`Collections`工具所提供的方法`synchronizedSortMap`，
 *  它是将`TreeMap`进行包装。对于高并发场景下，应使用`ConcurrentSkipListMap`提供更高的并发度。并且，如果在多线程环境下，需要
 *  对`Map`的键值进行排序时，也要尽量使用`ConcurrentSkipListMap`.
 * */
@Slf4j
@ThreadSafe
public class ConcurrentSkipListMapExample {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static Map<Integer, Integer> map = new ConcurrentSkipListMap<>();

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
        log.info("size:{}", map.size());
    }

    private static void update(int i) {
        map.put(i, i);
    }
}