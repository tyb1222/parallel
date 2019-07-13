package com.tyb1222.chapt05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SharedLock implements Lock {


    Sync sync = new Sync(4);


    private static final class Sync extends AbstractQueuedSynchronizer {

        Sync(int count){
            if (count <=0){
                throw new IllegalArgumentException("count must large than 0");
            }
            setState(count);
        }


        /**
         * 对共享锁而言，state >0 时，在AQS队列锁中，会传递通知后面所有队列中的节点去获取锁.具体参见 AQS中，doAcquireShared对yryAcquireShared的调用
         * @param count 获取同步状态成功线程个数。每个线程获取成功，则ｃcount=1 。多线程状态下，为保证线程安全，肯定是一个个线程轮流获取.count默认为0
         * @return 返回小于０，说明当前线程获取同步状态失败；大于0说明获取同步状态成功。
         * 在doAcquireShared 也是通过自旋获取锁
         *  AQS模板方法中，tryAcquireShared 返回值大于0 说明获取锁成功，反之失败。下面为代码
         *
         *    public final void acquireShared(int arg) {
         *       if (tryAcquireShared(arg) < 0)
         *           doAcquireShared(arg);
         *       }
         *
         */
        @Override
        protected int tryAcquireShared(int count) {
            for (;;){
                int currentState = getState();
                int state = currentState - count;
                /**
                 * state 小于0，获取同步锁失败。
                 */
                if (state<0 ){
                    return state;
                }
                /**
                 * CAS 指令成功，说明获取同步状态成功。此时state >0
                 */
                if (compareAndSetState(currentState,state)){
                    return state;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int count) {
            for (;;){
                int currentState = getState();
                int newState = currentState + count;
                return compareAndSetState(currentState,newState);
            }
        }

        final ConditionObject newCondition(){
            return new ConditionObject();
        }
    }


    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1)>=0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


}
