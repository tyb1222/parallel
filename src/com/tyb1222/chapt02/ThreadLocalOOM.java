package com.tyb1222.chapt02;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalOOM {

    static final int TASK_LOOP_SIZE =500;

    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,10, TimeUnit.MINUTES,new LinkedBlockingQueue<>());

    static class LocalVariable{
        byte [] a =new byte[1024*1024*5];
    }

    final static ThreadLocal<LocalVariable> localVariables = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i =0;i<TASK_LOOP_SIZE;i++){
            poolExecutor.execute( ()->{

                    localVariables.set(new LocalVariable());
                    System.out.println("use local variable");
                    localVariables.remove();
                });
            Thread.sleep(100);
        }
        System.out.println("pool executor over...");
    }
}
