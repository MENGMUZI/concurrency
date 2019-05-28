package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  14:51
 * @description: 因为Future只是一个接口，所以是无法直接用来创建对象使用的，因此就有了下面的FutureTask。
 * Future接口代表异步计算的结果，通过Future接口提供的方法可以查看异步计算是否执行完成，
 * 或者等待执行结果并获取执行结果，同时还可以取消执行。
 */
@Slf4j
public class FutureExample {

    //Callable任务
    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            //暂停一会儿线程
            try{ TimeUnit.SECONDS.sleep(5);}catch(Exception e){e.printStackTrace();}
            return "Done";
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.生成线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do something in main");
        //暂停一会儿线程
        try{ TimeUnit.SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
        String result = future.get();
        log.info("result: {}",result);
    }


}
