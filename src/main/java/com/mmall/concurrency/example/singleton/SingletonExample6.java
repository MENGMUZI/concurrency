package com.mmall.concurrency.example.singleton;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  16:22
 * @description: 饿汉模式（静态域初始化）
 */

import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在类装载时进行创建
 *
 * 静态域与静态代码块是顺序执行的，若将1 2 处位置进行交换则会出现空指针异常
 */
@ThreadSafe
public class SingletonExample6 {
    //私有构造函数
    private SingletonExample6(){

    }
    //1.
    //单例对象
    private static SingletonExample6 instance = null;

    //2.
    static {
        instance = new SingletonExample6();
    }

    //静态工厂方法
    public static SingletonExample6 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }

}
