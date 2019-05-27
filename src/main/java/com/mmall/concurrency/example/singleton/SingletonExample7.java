package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.Recommend;
import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  16:29
 * @description: 最安全的枚举模式
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {
    //私有构造函数
    private SingletonExample7(){

    }

    public static SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton{
        INSTANCE;
        private SingletonExample7 singletonExample7;
        // JVM保证这个方法绝对只调用一次
        Singleton(){
            singletonExample7 = new SingletonExample7();
        }
        public SingletonExample7 getInstance(){
            return singletonExample7;
        }

    }


}
