package com.tyb1222.chapt05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RwLockService implements GoodsService {

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock(); //读锁
    private static Lock writeLock = readWriteLock.writeLock();// 写锁

    private GoodsInfo goodsInfo;

    public RwLockService(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() throws InterruptedException {
        readLock.lock();
        GoodsInfo result = null;
        try {
            Thread.sleep(10);
            result = this.goodsInfo;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            readLock.unlock();
        }
        return result;
    }

    @Override
    public void setNum(float price) throws InterruptedException {
        writeLock.lock();
        try {
            Thread.sleep(10);
            goodsInfo.changePrice(10.0f);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            writeLock.unlock();
        }

    }
}
