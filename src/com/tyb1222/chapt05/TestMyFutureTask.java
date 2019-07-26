package com.tyb1222.chapt05;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TestMyFutureTask {

    private static class CalcThread implements Callable<Integer> {
        int sum = 0;

        @Override
        public Integer call() throws InterruptedException {
//            System.out.println("current thread is :" + Thread.currentThread().getId());
            for (int i=0 ;i<=10;i++){
                Thread.sleep(1000);
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("thread was interrupted..");
                    return null;
                }
                sum +=i ;
            }
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread is :" + Thread.currentThread().getId());
        System.out.println("***********************");
        List<MyFutureTask<Integer>>  tasks = new ArrayList<>();
        for(int i =0 ;i<30; i++) {
            CalcThread thread = new CalcThread();
            MyFutureTask<Integer> task = new MyFutureTask(thread);
            tasks.add(task);
            new Thread(task).start();

        }
        for(MyFutureTask<Integer> entry : tasks){
            System.out.println(" >>>   result is :"  + entry.get());
        }

    }

}
