package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.annoations.NotRecommend;
import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  15:16
 * @description: 逸出
 *  在构造对象时发生，它会使类的this引用发生逸出，从而使线程看到一个构造不完整的对象。
 *  下面的内部类的实例包含了对封装实例隐含的引用，这样在对象没有被正确构造之前，就会被发布，有可能会有不安全因素。
 *
 *  一个导致this引用在构造期间逸出的错误，是在构造函数中启动一个线程，无论是隐式启动线程，还是显式启动线程，
 *  都会造成this引用逸出，新线程总会在所属对象构造完毕前看到它。所以如果要在构造函数中创建线程，那么不要启动它，
 *  而应该采用一个专有的start或initialize方法来统一启动线程。我们可以采用工厂方法和私有构造函数来完成对象创建和监听器的注册，
 *  这样就可以避免不正确的创建。记住，我们的目的是，在对象未完成构造之前，不可以将其发布。
 *
 */
@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {

    private int thisCanBeEscape = 0;
    public Escape(){
        new InnnerClass();
    }
    private class InnnerClass{
        public InnnerClass(){
            //这里可以在Escape对象完成构造前提前引用到Escape的private变量
            log.info("{}",Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }


}
