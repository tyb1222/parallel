package com.tyb1222.chapt05;

public class SynGoodsService implements GoodsService {

    private GoodsInfo goodsSInfo;

    public SynGoodsService(GoodsInfo goodsSInfo) {
        this.goodsSInfo = goodsSInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() throws InterruptedException {
        Thread.sleep(10);
        return this.goodsSInfo;
    }

    @Override
    public synchronized void setNum(float price) throws InterruptedException {
        Thread.sleep(10);
        goodsSInfo.changePrice(10.0f);
    }
}
