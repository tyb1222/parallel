package com.tyb1222.chapt02;

public class UserThreadLocal {
    private static ThreadLocal<Integer> threadLocalMy = new ThreadLocal<Integer>(){
        @Override
        public Integer initialValue(){
            return 1;
        }
    };

    public void startThreadArray(){
        Thread [] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new TestThread(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class TestThread implements Runnable{
        int id;
        public TestThread(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() +"   start...");
            final Integer value = threadLocalMy.get();
            threadLocalMy.set(value+id);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":"+threadLocalMy.get());
        }
    }

    public static void main(String[] args) {
        UserThreadLocal userThreadLocal= new UserThreadLocal();
        userThreadLocal.startThreadArray();
    }
}
