package com.tyb1222.chapt08;

public interface MyBlockingQueue<T> {

    void put(T t) throws InterruptedException;

    T take() throws InterruptedException;


}
