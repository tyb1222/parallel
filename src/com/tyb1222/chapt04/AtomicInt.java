package com.tyb1222.chapt04;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInt {
    static AtomicInteger ai = new AtomicInteger(0);

    public synchronized void increase() {
        for (;;){
            int i = ai.get();
            boolean success = ai.compareAndSet(ai.get(),ai.get()+1);
            if (success){
                break;
            }

        }
    }

    public int getCount(){
        return ai.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInt atomicInt = new AtomicInt();
        int threadCount = 200;
        Thread [] threads = new Thread[threadCount];
        for (int i = 0; i <threadCount; i++) {
            threads[i] = new Thread(()->{
                for (int j =0;j<100000;j++){
                        atomicInt.increase();
                }
            });
            threads[i].start();
        }

        for (int i = 0; i <threadCount; i++) {
            System.out.println("thread :" + threads[i].getId() + " status is:>>" +threads[i].getState());

        }
        System.out.println("*****************");
        Thread.sleep(2000);
        for (int i = 0; i <threadCount; i++) {
            System.out.println("thread :" + threads[i].getId() + " status is:>>" +threads[i].getState());

        }
        System.out.println(atomicInt.getCount());
    }
}
