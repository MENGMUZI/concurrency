package com.mmall.concurrency.example.threadLocal;

/**
 * @author : mengmuzi
 * create at:  2019-05-27  20:06
 * @description:
 */
public class RequestHolder {

    private final static  ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id){
        requestHolder.set(id);

    }
    public static Long getId(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }
}
