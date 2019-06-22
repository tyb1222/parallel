package com.tyb1222.chapt05;

import java.util.Random;

public class Test {

    static final int READ_WRITE_RATIO = 10;

    static final int MIN_WRITE_THREAD = 3;

    static class GetThread implements Runnable{
        private GoodsService service;

        public GetThread(GoodsService service) {
            this.service = service;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                try {
                    service.getNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("read goods cost :" + (end -start));
        }
    }

    static class WriteThread implements Runnable{

        private GoodsService service;

        public WriteThread(GoodsService service) {
            this.service = service;
        }


        @Override
        public void run() {
            Random random = new Random();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                try {
                    service.setNum(random.nextFloat());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("set goods cost :" + (end -start));
        }
    }


    public static void main(String[] args) throws InterruptedException {
        GoodsInfo goodsInfo = new GoodsInfo("soap",100.0f);
        GoodsService service = new SynGoodsService(goodsInfo);
//        GoodsService service = new RwLockService(goodsInfo);
        for (int i = 0; i < MIN_WRITE_THREAD; i++) {
            WriteThread writeThread = new WriteThread(service);
            for (int j = 0; j < READ_WRITE_RATIO; j++) {
                GetThread getThread = new GetThread(service);
                new Thread(getThread).start();
            }
            Thread.sleep(10);
            new Thread(writeThread).start();
        }

    }
}
