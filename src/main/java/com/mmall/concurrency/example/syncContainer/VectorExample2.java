package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  21:31
 * @description: Vector
 * 注意：同步容器不一定是线程安全的。
 */

/**
 * VectorExample2程序的运行，在get()中会不断的抛出ArrayIndexOutOfBoundsException。
 * Vector是线程同步容器，size()、get()与remove()都是被synchronized修饰的，但是为什么还是会存在线程安全问题呢？
 *
 * 1. 线程1和线程2都执行完vector.size()，获得的size大小相同，并且当两个线程都是i=9
 * 2. 线程1执行remove操作，删除索引为9的数据
 * 3. 线程2执行get操作，获取索引为9的数据，那么就会抛出数组越界异常，
 *
 *
 * 在使用同步容器的时候，并不是所有的场合下都能够做到线程安全。
 * */
@Slf4j
@ThreadSafe
public class VectorExample2 {
    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while(true){
            for (int i = 0; i <10 ; i++) {
                vector.add(i);
            }
            new Thread(()->{
                for (int i = 0; i <vector.size() ; i++) {
                    vector.remove(i);
                }
            },"t1").start();

            new Thread(()->{
                for (int i = 0; i <vector.size() ; i++) {
                    vector.get(i);
                }
            },"t2").start();
        }
    }

}
