package com.mmall.concurrency.example.singleton;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  15:45
 * @description: 安全发布对象
 *
 * 安全发布对象的四种方法：
 * 1.在静态初始化函数中初始化一个对象引用
 * 2.将对象的引用保存到volatile类型域或者AtomicReference对象中
 * 3.将对象的引用保存到某个正确构造对象的final类型域中
 * 4.将对象的引用保存到一个由锁保护的域中
 *
 * 案例分析
 *   Spring 框架中，Spring管理的类都是单例模式。如何保证一个实例只被初始化一次，且线程安全？
 *   通过不同单例的写法，具体描述安全发布对象的四种方法：
 */

import com.mmall.concurrency.annoations.NotThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 */
@NotThreadSafe
public class SingletonExample1 {
    // 私有构造函数
    private SingletonExample1(){
        //可能这里会存在很多的操作
        //如资源加载、运算等
    }
    //单例对象
    private static SingletonExample1 instance = null;
    // 静态的工厂方法
    // 单线毫无问题
    public static SingletonExample1 getInstance(){
        //多线程环境下存在线程安全问题
        if(instance == null){
            instance = new SingletonExample1();
        }
        return instance;
    }

}
