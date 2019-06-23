package com.tyb1222.chapt05;

import java.util.concurrent.locks.Lock;

public class Fab {

//    private Lock lock = new MySyncLock();
    private Lock lock = new MyReentrantLock();


    public int calc(int num) throws Exception {
        lock.lock();
        try {
            if (1 == num){
                return 1;
            }
            if (2 ==num){
                return 1;
            }
            return calc(num-1)+calc(num-2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Exception("exception");
        } finally {
            lock.unlock();
        }

    }



    public void test() {
        for (int i = 0; i < 3; i++) {
            Work w = new Work();
            w.start();
        }

    }


    class Work extends Thread{
        @Override
        public void run() {

            try {

                int result = calc(1);
                System.out.println(Thread.currentThread().getName() + "  "+result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    public static void main(String[] args) {
        Fab fab = new Fab();
        fab.test();
    }
}
