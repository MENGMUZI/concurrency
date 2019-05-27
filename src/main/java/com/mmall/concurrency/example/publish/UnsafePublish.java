package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  15:10
 * @description: 发布
 *              通过public访问级别发布了类的域，在类的外部任何线程都可以访问这些域，这样发布对象是不安全的，
 *              因为我们无法假设，其他线程不会修改这些域，从而造成类状态的错误。
 */
@Slf4j
@NotThreadSafe
public class UnsafePublish {
    private String[] states = {"a","b","c"};
    public String[] getStates(){
        return states;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("{}", Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";
        log.info("{}", Arrays.toString(unsafePublish.getStates()));
    }

}
