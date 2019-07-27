package com.tyb1222.chapt05;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyFutureTask2<V> implements Runnable, Future<V> {

    private final Callable<V> callable;

    private V result;

    private Sync sync;

    public MyFutureTask2(Callable<V> callable) {
        this.callable = callable;
        sync = new Sync();
    }

    @Override
    public void run() {
        try {
            sync.acquireShared(0);
            result = callable.call();
            System.out.println(" thread : "+ Thread.currentThread().getId() +" result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sync.releaseShared(0);
        }
    }



    @Override
    public V get() {
        for(;;){
            int threadState = sync.getThreadState();
            if (threadState == ThreadState.FINISHED.getState()){
                return result;
            }
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
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    private static class Sync extends AbstractQueuedSynchronizer{


        @Override
        protected int tryAcquireShared(int arg) {
            int currentState = getState();
            for (;;){
                if ( compareAndSetState(currentState,ThreadState.RUNNING.getState())){
                    return ThreadState.RUNNING.getState();
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int currentState = getState();
            for (;;){
                return compareAndSetState(currentState,ThreadState.FINISHED.getState());
            }
        }

        private int getThreadState(){
            return getState();
        }
    }

    private enum ThreadState{
        RUNNING(1,"运行中"),FINISHED(-1,"完成");

        private int state;

        private String desc;

        ThreadState(int state, String desc) {
            this.state = state;
            this.desc = desc;
        }


        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
