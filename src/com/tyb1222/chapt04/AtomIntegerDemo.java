package com.tyb1222.chapt04;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomIntegerDemo {
    static AtomicInteger ai = new AtomicInteger(10);

    final static int TIMES = 1;



/*    public static void main(String[] args) {
        for (int i = 0; i < TIMES; i++) {
            new Thread(()->{
                int result = ai.addAndGet(10);
                System.out.println(result);
                ai.compareAndSet(20,12);
                System.out.println(ai.get());
            }).start();
        }

    }*/

    public static void main(String[] args) {
        int [] arr = new int[]{1,3,5};
        AtomicIntegerArray  intArr= new AtomicIntegerArray(arr);
        intArr.compareAndSet(1,1,7);
        System.out.println("after compare and set :"+intArr.get(1));
        System.out.println("original data: "+arr[1]);
    }
}
