package com.tyb1222.chapt05;

public class TestExpress {
    static Express express = new Express(0,Express.CITY,3);

    static class CheckKm extends Thread {@Override
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

    public static void main(String[] args) {
        for (int i=0;i<3;i++){
            new CheckKm().start();
        }
        for (int i=0;i<3;i++){
            new CheckSite().start();
        }
        System.out.println("main thread goes on");
//        express.changeKM();/
        express.changeSite();
        System.out.println("main over...");
    }

}