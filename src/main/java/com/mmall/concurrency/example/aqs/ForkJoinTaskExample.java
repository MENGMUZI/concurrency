package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  15:42
 * @description: Fork/Join框架是Java7提供了的一个用于并行执行任务的框架， 是一个把大任务分割成若干个小任务，
 * 最终汇总每个小任务结果后得到大任务结果的框架.
 *
 */
@Slf4j
public class ForkJoinTaskExample extends RecursiveTask<Integer> {
    //设置分割的阈值
    public static final int threshold = 10;
    private int start;
    private int end;

    public ForkJoinTaskExample(int start ,int end) {
        this.start = start;
        this.end = end;
    }
    //任务
    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if(canCompute){
            //任务足够小的时候，直接计算，不进行分裂计算
            for (int i = start; i <= end ; i++) {
                sum += i;
            }
        }else{
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end)/2;
            //递归操作，继续分裂任务
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start,middle);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1,end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            //合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        //生成一个池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        //生成一个计算任务，计算1+2+3+.....+100
        ForkJoinTaskExample forkJoinTaskExample = new ForkJoinTaskExample(1,100);

        //执行一个任务，将任务放入池中，并开始执行，并用Future接收
        Future<Integer> future = forkJoinPool.submit(forkJoinTaskExample);

        try {
            log.info("result:{}",future.get());
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        } catch (ExecutionException e) {
            log.error("ExecutionException", e);
        }
    }
}
