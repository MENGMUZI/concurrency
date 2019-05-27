package com.mmall.concurrency.example.concurrency;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  01:24
 * @description: ConcurrentSkipListSet
 *
 *  TreeSet -> ConcurrentSkipListSet
 *  1.ConcurrentSkipListSet<E>是jdk6新增的类，位于java.util.concurrent并发库下
 *  2.ConcurrentSkipListSet<E>和TreeSet一样，都是支持自然排序，并且可以在构造的时候定义Comparator<E>的比较器，
 *  该类的方法基本和TreeSet中方法一样（方法签名一样）
 *  3.和其他的Set集合一样，ConcurrentSkipListSet<E>都是基于Map集合的，ConcurrentSkipListMap便是它的底层实现
 *  4.在多线程的环境下，ConcurrentSkipListSet<E>中的contains、add、remove操作是安全的，多个线程可以安全地并发
 *  执行插入、移除和访问操作。但是对于批量操作addAll、removeAll、retainAll 和 containsAll并不能保证以原子方式执行。
 *  理由很简单，因为addAll、removeAll、retainAll底层调用的还是contains、add、remove的方法，在批量操作时，只能保
 *  证每一次的contains、add、remove的操作是原子性的（即在进行contains、add、remove三个操作时，不会被其他线程打断），
 *  而不能保证每一次批量的操作都不会被其他线程打断。因此，在addAll、removeAll、retainAll 和 containsAll操作时，
 *  需要添加额外的同步操作。
 *  5.此类不允许使用 null 元素，因为无法可靠地将 null 参数及返回值与不存在的元素区分开来
 *
 */
@Slf4j
@ThreadSafe
public class ConcurrentSkipListSetExample {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static Set<Integer> set = new ConcurrentSkipListSet<>();

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