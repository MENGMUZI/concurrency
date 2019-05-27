package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  16:41
 * @description: 不可变对象 final
 *   final关键字：类、方法、变量
 *
 * 修饰类：不能被继承，final类中的成员属性可以根据需要设置为final，但final类中所有的成员方法都被隐式指定为final方法。一般不建议将类设置为final类型。可以参考String类。
 * 修饰方法：1）锁定方法不被继承类修改；2）效率
 * 修饰变量：1）基本数据类型变量，初始化后便不能进行修改；2）引用类型变量，初始化之后不能再指向别的引用
 *
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {
    private final static Integer a = 1;
    private final static String b = "2";
    //引用类型不允许引用指向改变，但是对象值还是可以进行修改的
    private final static Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args) {
//        a = 2;              //编译时报错
//        b = "3";            //编译时报错
//        map = Maps.newHashMap();   //编译时报错
        map.put(1,3);//容易引发线程安全问题
        log.info("{}",map.get(1));
    }

    //可以修饰形参
    private void test(final int a){
//        a = 1;             //编译时报错
    }



}
