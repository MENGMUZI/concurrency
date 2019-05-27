package com.mmall.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  13:14
 * @description:  synchronized同步代码块与同步方法演示
 *  注意：如果某个类为父类，并且存在同步方法，子类在继承这个类后，如果子类调用该父类的同步方法后，
 *  该方法是没有synchronized关键字的，原因是synchronized不属于方法声明的一部分
 */
@Slf4j
public class SynchronizedExample1 {
    // 修饰一个代码块
    public void test1(){
        //同步代码块 作用于调用的对象
        synchronized (this){
            for (int i = 0; i <10 ; i++) {
                log.info("test1-{}",i);
            }
        }
    }

    // 修饰一个方法 同步方法 作用于调用的对象
    public synchronized void test2(int j){
        for (int i = 0; i <10 ; i++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args){
        SynchronizedExample1 synchronizedExample1 = new SynchronizedExample1();
        SynchronizedExample1 synchronizedExample11 = new SynchronizedExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            //synchronizedExample1.test1();
            synchronizedExample1.test2(1);
        });
        executorService.execute(()->{
            //synchronizedExample11.test1();
            synchronizedExample11.test2(2);
        });
        executorService.shutdown();
    }


}
