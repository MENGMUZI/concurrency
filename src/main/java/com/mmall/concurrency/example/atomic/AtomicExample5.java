package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  10:57
 * @description: AtomicIntegerFieldUpdater的使用
 *
 */
@Slf4j
@ThreadSafe
public class AtomicExample5 {
    //AtomicIntegerFieldUpdater 原子性的更新某一个类的实例的指定的某一个字段
    //并且该字段由volatile进行修饰同时不能被static修饰
    //有些网上说而且不能被private修饰？下文将进行验证
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");
    @Getter
    public volatile int count =100;
    public static void main(String[] args) {
        AtomicExample5 example5 = new AtomicExample5();
        if(updater.compareAndSet(example5,100,120)){
            log.info("update success1:{}", example5.count);
        }
        if(updater.compareAndSet(example5,100,120)){
            log.info("update success2:{}", example5.count);
        }else{
            log.info("update false:{}", example5.count);
        }
    }

}
