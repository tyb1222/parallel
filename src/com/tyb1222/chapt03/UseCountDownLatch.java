package com.tyb1222.chapt03;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {
    static CountDownLatch latch = new CountDownLatch(6);

    static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Thread_" +Thread.currentThread().getId() +"  ready init work ");
            latch.countDown();
            for (int i = 0; i < 2; i++) {
                System.out.println("Thread_" +Thread.currentThread().getId() +"  continue init work...");

            }
        }
    }

    static class BusiThread implements Runnable{

        @Override
        public void run() {
            try {
                System.out.println("busi thread walk in ");
                latch.await();
                System.out.println("busi thread goes on ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread_" +Thread.currentThread().getId() +" business work");
            }
        }
    }

    static void init(){
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread_" +Thread.currentThread().getId() +" 1st ..");
            latch.countDown();
            System.out.println("begin 2st ,,,");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end 2st ,,,");
            latch.countDown();
        }).start();
    }

    public static void main(String[] args) {
        init();
        new Thread(new BusiThread()).start();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.countDown();
        System.out.println("main over...");
    }
}
