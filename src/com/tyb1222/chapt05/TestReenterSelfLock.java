package com.tyb1222.chapt05;


import java.util.concurrent.locks.Lock;

/**
 *类说明：实现可重入锁
 */
public class TestReenterSelfLock {

//    static final Lock lock = new ReenterSelfLock();
    static final Lock lock = new MySyncLock();

    public void reenter(int x){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+":递归层级:"+x);
            int y = x - 1;
            if (y==0) return;
            else{
                reenter(y);
            }
        } finally {
            lock.unlock();
        }

    }

    public void test() {
        class Worker extends Thread {
			public void run() {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reenter(3);
            }
        }
        // 启动3个子线程
        for (int i = 0; i < 3; i++) {
            Worker w = new Worker();
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

    public static void main(String[] args) {
        TestReenterSelfLock testMyLock = new TestReenterSelfLock();
        testMyLock.test();
    }
}
