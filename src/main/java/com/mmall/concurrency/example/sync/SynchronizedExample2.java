package com.mmall.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  13:14
 * @description:  synchronized 修饰静态方法与修饰类演示
 *                同一个类的不同对象执行同步修饰的方法，执行的顺序是同步的
 *
 */
@Slf4j
public class SynchronizedExample2 {
    // 修饰一个代码块
    public static void test1(int j){
        //同步代码块 作用于调用的对象
        synchronized (SynchronizedExample2.class){
            for (int i = 0; i <10 ; i++) {
                log.info("test1 {} - {}",j,i);
            }
        }
    }

    // 修饰一个静态方法 同步方法 作用于调用的对象
    public static synchronized void test2(int j){
        for (int i = 0; i <10 ; i++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args){
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            synchronizedExample1.test1(1);
            //synchronizedExample1.test2(1);
        });
        executorService.execute(()->{
            //synchronizedExample11.test1();
            synchronizedExample2.test1(2);
        });
        executorService.shutdown();
    }


}
