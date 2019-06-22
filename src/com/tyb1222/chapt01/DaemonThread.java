package com.tyb1222.chapt01;

public class DaemonThread {

    private static class UseThread extends Thread{
        @Override
        public void run(){
            try{
                while (!isInterrupted()){
                    System.out.println("deamon thread");
                }
            }
            finally {
                System.out.println("....deamon thread finally");
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        UseThread useThread = new UseThread();
        useThread.setDaemon(true);
        useThread.start();
        Thread.sleep(5);

        useThread.interrupt();
        System.out.println("main over");
    }
}
