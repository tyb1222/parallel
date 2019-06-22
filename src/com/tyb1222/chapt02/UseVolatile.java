package com.tyb1222.chapt02;

public class UseVolatile {

    private volatile static boolean subFlag = true;

    private volatile static boolean num ;

    private static class SubVolatile extends Thread{
//        @Override
//        public void run() {
//            while (subFlag) {
//                System.out.println("sub run..." + Thread.currentThread().getName());
//            }
//            System.out.println("num is : "+ num);
//
//        }

        @Override
        public void run() {
            System.out.println("sub thread is running");
            num =true;
            while (subFlag){

            }
            System.out.println("num is : "+ num);
            System.out.println("sub thread will exit ");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SubVolatile subThread = new SubVolatile();
        subThread.start();

        System.out.println("current thread is :"+ Thread.currentThread().getName());
        Thread.currentThread().sleep(1);
        subFlag = false;
        num =false;
        System.out.println("main over");
    }
}
