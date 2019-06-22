package com.tyb1222.chapt01;

public class UseJoin {
    static class Goddess implements Runnable{

        private Thread thread;

        public Goddess(){}

        public Goddess(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println("Goddess 开始排队打饭");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != thread){
                try {
                    thread.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + "Goddess 打饭完成");
        }
    }

    static class GoddnessBoy implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("GodnessBoy 开始排队打饭");
            System.out.println(Thread.currentThread().getName() + "GoddnessBoy 打饭完成");
        }
    }

    public static void main(String[] args) {
        Thread main = Thread.currentThread();
        GoddnessBoy goddnessBoy = new GoddnessBoy();
        Thread boy = new Thread(goddnessBoy);
        Goddess goddess = new Goddess(boy);
        Thread thread = new Thread(goddess);
        thread.start();
        boy.start();
        System.out.println("main 开始排队打饭");
//        try {
//            thread.join();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+ "   main 打饭完成");
    }
}
