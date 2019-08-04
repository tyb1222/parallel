package com.tyb1222.chapt08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockedBlockingQueue<T> implements MyBlockingQueue<T> {

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notFullCondition = lock.newCondition();

    private final Condition emptyCondition = lock.newCondition();

    private final List<T> arr;

    private final int capacity;


    public MyLockedBlockingQueue(int capacity) {
        this.arr = new ArrayList<>();
        this.capacity = capacity;
    }


    @Override
    public void put(T t) throws InterruptedException {
        lock.lockInterruptibly();
        try{
            if (arr.size() == capacity){
                System.out.println("the capacity is up to max ...");
                notFullCondition.await();
            }else{
                System.out.println("put element to queue ...");
                arr.add(t);
                emptyCondition.signal();
//                System.out.println("emptyCondition.signal invoke...");
            }

        }finally {
            lock.unlock();
        }
    }


    @Override
    public T take() throws InterruptedException {
        System.out.println(">>>>>>>>>enter take method>>>>>>>>>");
        lock.lockInterruptibly();
        T result = null;
        try{
            if (arr.size() == 0){
                System.out.println("the capacity is empty...will be blocked");
                emptyCondition.await();
            }
            result = arr.get(0);
            arr.remove(0);
            System.out.println("take element  "+ result + "   from queue");
            notFullCondition.signal();

        }finally {
            lock.unlock();
        }
        return result;
    }
}
