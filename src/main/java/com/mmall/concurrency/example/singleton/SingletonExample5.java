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
 * 懒汉模式 ==> 双重同步锁单例模式
 * 单例实例在第一次使用时进行创建
 */
@ThreadSafe
public class SingletonExample5 {
    // 私有构造函数
    private SingletonExample5(){
        //可能这里会存在很多的操作
        //如资源加载、运算等
    }
    // 1、memory = allocate() 分配对象的内存空间
    // 2、ctorInstance() 初始化对象
    // 3、instance = memory 设置instance指向刚分配的内存

    // 单例对象 volatile + 双重检测机制 -> 禁止指令重排
    private volatile static SingletonExample5 instance = null;

    // 静态的工厂方法
    public static SingletonExample5 getInstance() {
        if (instance == null) { // 双重检测机制        // B
            synchronized (SingletonExample5.class) { // 同步锁
                if (instance == null) {
                    instance = new SingletonExample5(); // A - 3
                }
            }
        }
        return instance;
    }
}
