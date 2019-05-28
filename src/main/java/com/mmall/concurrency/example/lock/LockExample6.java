package com.mmall.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  14:24
 * @description: Condition
 */

/*
 * Condition作为一个条件类，很好的维护了一个等待信号的队列，并在适合的时候，将自身队列中的
 * 结点加入到AQS等待队列中，实现唤醒操作。使得某个线程等待某个条件，实际上使用很少
 */
@Slf4j
public class LockExample6 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        //从ReentrantLock中取得Condition对象
        //此时在AQS中生成Condition队列（可以有多个）
        Condition condition = reentrantLock.newCondition();

        //线程1
        new Thread(()->{
            try{
                reentrantLock.lock();
                log.info(Thread.currentThread().getName() + "\twait signal");//1
                condition.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + "\tget signal");//4
            reentrantLock.unlock();
        },"t1").start();

        new Thread(()->{
            //因为线程1释放锁，这时得到锁
            reentrantLock.lock();
            log.info(Thread.currentThread().getName()+"\tget lock");//2
            //暂停一会儿线程
            try{ TimeUnit.SECONDS.sleep(2);}catch(Exception e){e.printStackTrace();}
            //发送信号，这时Condition队列中有线程1的结点，被取出加入AQS等待队列（注意，线程1没有被唤醒）
            condition.signalAll();
            log.info(Thread.currentThread().getName() + "\tsend signal ~");//3
            //释放锁会唤醒AQS队列
            reentrantLock.unlock();
        },"t2").start();
    }
}
