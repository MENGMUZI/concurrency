package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  15:13
 * @description: FutureTask
 *  Future只是一个接口，不能直接用来创建对象，FutureTask是Future的实现类。
 *  public interface RunnableFuture<V> extends Runnable, Future<V> {}
 */
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something in callable");
                //暂停一会儿线程
                try{ TimeUnit.SECONDS.sleep(5);}catch(Exception e){e.printStackTrace();}
                return "Done";
            }
        });
        new Thread(futureTask).start();
        log.info("do something in main");
        //暂停一会儿线程
        try{ TimeUnit.SECONDS.sleep(1);}catch(Exception e){e.printStackTrace();}
        String result = futureTask.get();
        log.info("result:{}",result);

    }


}
