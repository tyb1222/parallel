package com.tyb1222.chapt09.threadpool;

import java.util.Arrays;
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

    /*将任务提交到线程池*/
    public void submit(Runnable runnable){
        queue.add(runnable);

    }

    /*销毁线程*/
    public void destroy(){
        for (int i = 0; i < thread_size; i++) {
            threads[i].stopWorkThread();
            threads[i] = null;
        }
        queue.clear();
    }


    @Override
    public String toString() {
        return "MyThreadPool{" +
                "thread_size=" + thread_size +
                ", queue size =" + queue.size() +
                ", threads=" + Arrays.toString(threads) +
                '}';
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
                    task = null;
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
