package com.tyb1222.chapt05;

public interface GoodsService {
    GoodsInfo getNum() throws InterruptedException;

    void setNum(float price) throws InterruptedException;
}
