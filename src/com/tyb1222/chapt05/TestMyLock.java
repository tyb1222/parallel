package com.tyb1222.chapt05;


public class TestMyLock {

    public void test(){
        final SelfLock myLock = new SelfLock();
        class Work extends Thread {

            @Override
            public void run() {
                myLock.lock();
                System.out.println(Thread.currentThread().getName() + ">>  get a lock");
                try {
                     Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                   e.printStackTrace();
                } finally {
                   myLock.unlock();
                }
            }
        }

        for (int i =0 ;i<5;i++){
            Work work = new Work();
            work.start();
        }

        for (int i =0 ;i<15;i++){
            try {
                Thread.sleep(1*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TestMyLock test = new TestMyLock();
        test.test();
    }
}
