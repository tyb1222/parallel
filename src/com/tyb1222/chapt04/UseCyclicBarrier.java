package com.tyb1222.chapt04;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {

    static CyclicBarrier barrier = new CyclicBarrier(4,new CollectThread());

    static ConcurrentHashMap<String,Long> resultMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
        System.out.println("main over...");
    }

    static class CollectThread implements Runnable{
        @Override
        public void run(){
            StringBuilder result= new StringBuilder();
            for (Map.Entry<String,Long> workResult:resultMap.entrySet()){
                result.append(workResult.getValue());
            }
            System.out.println("result is :>>>[" +result+"]");
            System.out.println("do other thing...");
        }
    }

    static class SubThread implements Runnable{
        @Override
        public void run(){
            Long id = Thread.currentThread().getId();
            resultMap.put(id+"",id);
            try {
                Thread.sleep(1000);
                System.out.println("thread_"+id+"  works");
                barrier.await();
//                Thread.sleep(1000);
                System.out.println("Thread_"+id+"  other...");
                barrier.await();
                barrier.await();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

}
