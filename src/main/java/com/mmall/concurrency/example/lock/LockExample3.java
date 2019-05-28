package com.mmall.concurrency.example.lock;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : mengmuzi
 * create at:  2019-05-26  17:40
 * @description: ReentrantReadWriteLock允许多个读线程同时访问，但不允许写线程和读线程、写线程和写线程同时访问。
 * 相对于排他锁，提高了并发性。
 *
 * 注意：
 * 在没有任何读写锁的时候才可以取得写入锁(悲观读取，容易写线程饥饿)，也就是说如果一直存在读操作，
 * 那么写锁一直在等待没有读的情况出现，这样我的写锁就永远也获取不到，就会造成等待获取写锁的线程饥饿。
 *
 */
@Slf4j
@ThreadSafe
public class LockExample3 {
    //定义一个map
    private final Map<String,Data> map = new TreeMap<>();
    //声明读和写
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    //获取读写锁中的读锁
    private final Lock readLock = reentrantReadWriteLock.readLock();
    //获取读写锁中的写锁
    private final Lock writeLock = reentrantReadWriteLock.writeLock();
    //获取
    public Data get(String key){
        readLock.lock();
        try{
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }
    //获取所有的key值
    public Set<String> getAllKeys(){
        readLock.lock();
        try{
            return map.keySet();
        }finally {
            readLock.unlock();
        }
    }
    //写
    public Data put(String key, Data value){
        //可能导致线程饥饿，处于一直等待状态
        writeLock.lock();
        try{
            return map.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }

}
class Data{

}

