package com.tyb1222.chapt05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyReentrantLock implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int count) {
            int currentState = getState();
            int newState = currentState -count;
            if (compareAndSetState(currentState,newState)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }else if(getExclusiveOwnerThread() == Thread.currentThread()){
                int nextc = currentState + count;
                if (nextc < 0){
                    throw new Error("Maximum lock count exceeded");
                }
                setState(nextc);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int count) {
            if (getExclusiveOwnerThread() != Thread.currentThread()){
                throw new IllegalArgumentException("current thread did not hold a lock, release error~");
            }
            int currentState = getState();
            //如果状态为0，则无线程持有锁
            if (currentState == 0){
                throw new IllegalArgumentException("no thread holds a lock");
            }
            int newState = currentState -count;
            if (newState ==0){
                setExclusiveOwnerThread(null);
                return true;
            }
            setState(newState);
            return true;

        }
    }


    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
