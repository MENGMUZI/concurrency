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

import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在类装载时进行创建
 * 缺点：1.若构造方法中存在过多的处理、会导加载缓慢，从而引起性能问题
 *      2.只进行加载，并无实际调用，导致资源浪费
 */
@ThreadSafe
public class SingletonExample2 {
    // 私有构造函数
    private SingletonExample2(){
        //可能这里会存在很多的操作
        //如资源加载、运算等
    }
    //单例对象
    private static SingletonExample2 instance = new SingletonExample2();
    // 静态的工厂方法
    public static SingletonExample2 getInstance(){
        return instance;
    }

}
