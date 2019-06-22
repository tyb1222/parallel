package com.tyb1222.chapt04;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {

    static class CallableCase implements Callable<Integer>{
        int sum;

        @Override
        public  Integer call() throws InterruptedException {
            System.out.println("callable thread begin");
            Thread.sleep(1);
            for (int i = 0; i < 5000*2; i++) {

                if (Thread.currentThread().isInterrupted()){
                    System.out.println("callable interrupted");
                    return null;
                }
                sum = sum+ i;
                System.out.println("i is "+i+"  sum is "+ sum);
            }
            System.out.println("callable over..result is :" + sum);
            return sum;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableCase callableCase = new CallableCase();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callableCase);
        new Thread(futureTask).start();
        Random random = new Random();
        Thread.sleep(13);
        if (random.nextInt(100)>50){
            System.out.println("callable result is :" + futureTask.get());
        }else {
            System.out.println("callable cancel");
            futureTask.cancel(true);
        }
    }
}
