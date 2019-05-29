package com.mmall.concurrency.example.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  23:10
 * @description:
 */
@Slf4j
public class DeadLockExample implements Runnable{
    public int flag = 1;
    //静态对象是类的所有对象共享的
    private static Object o1 = new Object(), o2 = new Object();
    @Override
    public void run() {
        log.info("flag:{}",flag);
        if(flag == 1){
            synchronized (o1){
                //暂停一会儿线程
                try{ TimeUnit.SECONDS.sleep(2);}catch(Exception e){e.printStackTrace();}
                synchronized (o2){
                    log.info("1");
                }
            }
        }
        if(flag == 0) {
            synchronized (o2) {
                //暂停一会儿线程
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    log.info("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLockExample example1 = new DeadLockExample();
        DeadLockExample example2 = new DeadLockExample();
        example1.flag = 1;
        example2.flag = 0;
        //td1,td2都处于可执行状态，但JVM线程调度先执行哪个线程是不确定的。
        //td2的run()可能在td1的run()之前运行
        new Thread(example1).start();
        new Thread(example2).start();

    }
}
