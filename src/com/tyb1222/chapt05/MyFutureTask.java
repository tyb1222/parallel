package com.tyb1222.chapt05;

import java.util.concurrent.*;

public class MyFutureTask<V> implements RunnableFuture<V> {


    private Callable callable;

    private V result;

    public MyFutureTask(Callable callable) {
        this.callable = callable;
    }



    @Override
    public void run() {
        System.out.println("run thread " + Thread.currentThread().getId());
        System.out.println("future task runs");
        try {
            result = (V)(callable.call());
            System.out.println("result value is "+ result);
            System.out.println("call next step");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        synchronized (this){
            this.notifyAll();
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public  V get() throws InterruptedException {
        if(null!=result){
            return result;
        }
        synchronized (this){
            wait();
        }
        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) {
        try {
            V result = (V)(callable.call());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
