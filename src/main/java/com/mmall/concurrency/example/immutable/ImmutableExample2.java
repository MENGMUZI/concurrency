package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  17:19
 * @description: java提供Collections工具类，在类中提供了多种不允许修改的方法
 * Collections.unmodifiableXXX：Collection、List、Set、Map...
 */
@Slf4j
@ThreadSafe
public class ImmutableExample2 {
    private static Map<Integer,Integer> map = Maps.newHashMap();
    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        //处理过后的map是不可以再进行修改的
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        //允许操作，但是操作会报错，扔出异常
        map.put(1,3);
        log.info("{}",map.get(1));//Exception in thread "main" java.lang.UnsupportedOperationException
    }

}
