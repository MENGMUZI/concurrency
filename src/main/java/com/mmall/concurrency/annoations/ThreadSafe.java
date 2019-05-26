package com.mmall.concurrency.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 课程里用来标记【线程安全】的类或者写法
 */
@Target(ElementType.TYPE) //作用域，作用于类上
@Retention(RetentionPolicy.SOURCE) //注解存在的范围，编译时忽略
public @interface ThreadSafe {
    //给默认值，方便扩展
    String value() default "";
}
