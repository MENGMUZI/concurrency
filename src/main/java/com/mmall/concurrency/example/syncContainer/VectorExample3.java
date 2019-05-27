package com.mmall.concurrency.example.syncContainer;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author : mengmuzi
 * create at:  2019-05-28  00:13
 * @description: 集合的删除的三种情况
 */

/** 总结：
 * 如果在使用foreach或iterator进集合的遍历，
 * 尽量不要在操作的过程中进行remove等相关的更新操作。
 * 如果非要进行操作，则可以在遍历的过程中记录需要操作元素的序号，
 * 待遍历结束后方可进行操作，让这两个动作分开进行
 *
 *
 * 在单线程会出现以上错误，在多线程情况下，并且集合时共享的，出现异常的概率会更大，需要特别的注意。
 * 解决方案是希望在foreach或iterator时，对要操作的元素进行标记，待循环结束之后，在执行相关操作。
 * ​以上例子中，for循环是能正确的进行，因此推荐使用for循环做来做包含更新操作的便利
 *
 *
 */

public class VectorExample3 {

    //Exception in thread "main" java.util.ConcurrentModificationException
    private static void test1(Vector<Integer> v1){//foreach
        for (Integer integer : v1) {
            if(integer.equals(3)){
                v1.remove(integer);
            }
        }
                
    }
    // Exception in thread "main" java.util.ConcurrentModificationException
    private static void test2(Vector<Integer> v1){//iterator
        for (Iterator<Integer> iterator = v1.iterator(); iterator.hasNext(); ) {
            Integer next =  iterator.next();
            if(next.equals(3)){
                v1.remove(next);
            }
        }
        
    }
    // successs
    private static void test3(Vector<Integer> v1){//for
        for (int i = 0; i < v1.size(); i++) {
            if (v1.get(i).equals(3)) {
                v1.remove(i);
            }
        }

    }

    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        //test1(vector);
        //test2(vector);
        test3(vector);
    }

}
