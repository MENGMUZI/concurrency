package com.mmall.concurrency.example.commonUnsafe;

import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  20:59
 * @description: JodaTime  线程安全的日期格式化
 */

/**
 * 总结
 * ​   在使用日期转换的时候，更建议使用JodaTime所提供的日期转换类，不仅是因为它是线程安全的，而且在类实际处理转换中有其他的优势。
 *    ArrayList、HashSet、HashMap 等 Collections
 * ​   通常使用以上类，都是声明在方法内，作为局部变量使用，一般很少碰上线程不安全的问题。但如果定义为可以多个线程修改的时候，就会出现线程安全问题。
 *
 * */
@Slf4j
@NotThreadSafe
public class DateFormatExample2 {
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
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
    }
    private static void update(int i){

        log.info("{},{}",i,DateTime.parse("20190527",dateTimeFormatter).toDate());
    }

}
