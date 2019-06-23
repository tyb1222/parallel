package com.tyb1222.chapt05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * AQS 实现独占锁
 */

public class MySyncLock implements Lock {



    static class Sync extends AbstractQueuedSynchronizer{

        protected Sync() {
            super();
        }

        /*获取所 */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean isHeldExclusively() {
            return 1== getState();
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (0 == getState()){
                throw new IllegalMonitorStateException();
            }
//            if (compareAndSetState(1,0)){
//                setExclusiveOwnerThread(null);
//                return true;
//            }
//            return false;
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }



        Condition newCondition() {
            return new ConditionObject();
        }

    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        System.out.println(Thread.currentThread().getName() +" get lock");
        sync.acquire(1);
        System.out.println(Thread.currentThread().getName()+ " locked successfully");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName()+"  try release lock ");
        sync.release(0);
        System.out.println(Thread.currentThread().getName()+ " release ok");
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked(){
        return sync.isHeldExclusively();
    }

}
