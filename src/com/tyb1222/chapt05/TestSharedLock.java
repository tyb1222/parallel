package com.tyb1222.chapt05;

import java.util.concurrent.locks.Lock;

public class TestSharedLock {

    public void test(){
        final Lock lock = new SharedLock();

        class Worker extends Thread{
            public void run(){
                lock.lock();
                System.out.println("TestSharedLock work thread is :" + Thread.currentThread().getName());
                try {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }

            }


        }

        for (int i =0 ;i<20;i++){
            Worker worker = new Worker();
            worker.start();
        }

//        for (int i =0 ;i<20;i++){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println();
//        }
    }

    public static void main(String[] args) {
        TestSharedLock testObject = new TestSharedLock();
        testObject.test();
    }

}
