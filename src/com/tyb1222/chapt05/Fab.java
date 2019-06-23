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


        // 启动3个子线程
        for (int i = 0; i < 3; i++) {
            Work w = new Work();
            w.start();
        }
        // 主线程每隔1秒换行
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class Work extends Thread{
        @Override
        public void run() {

            try {

                int result = calc(10);
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
