package com.tyb1222.chapt02;

public class TestWait {
    static Express express = new Express(0,Express.CITY,3);

    static class CheckKm extends Thread{

        @Override
        public void run(){
            try {
                express.waiteKm();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class CheckSite extends Thread{

        @Override
        public void run(){
            try {
                express.waiteSite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<3;i++){
            new CheckKm().start();
        }
        for (int i=0;i<3;i++){
            new CheckSite().start();
        }
        Thread.sleep(1000);
//        express.changeSite();
//        express.changeDate();
//        Thread.sleep(8000);
        express.changeKM();
    }
}
