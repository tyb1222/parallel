package com.tyb1222.chapt09.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {

    private  static int default_thread_size = 8;

    private int thread_size;

    private BlockingQueue<Runnable> queue;

    WorkThread[] threads;

    public MyThreadPool(){
        this(default_thread_size,100);
    }


    public MyThreadPool(int thread_size,int queueCapacity) {
        if (thread_size < 0){
            thread_size = default_thread_size;
        }
        this.thread_size = thread_size;
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        threads = new WorkThread[thread_size];
        for (int i = 0; i < thread_size; i++) {
            threads[i] = new WorkThread();
            threads[i].start();
        }

    }

    public void submit(Runnable runnable){
        queue.add(runnable);

    }

    public void destory(){
        for (int i = 0; i < thread_size; i++) {
            threads[i].stopWorkThread();
            threads[i] = null;
        }
        queue.clear();
    }


    private class WorkThread extends Thread{

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Runnable task = queue.take();
                    if (null != task) {
                        System.out.println("thread id :" + getId()+ "  start work.");
                        task.run();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void stopWorkThread() {
            interrupt();
        }
    }
}
