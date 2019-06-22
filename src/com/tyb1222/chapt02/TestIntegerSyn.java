package com.tyb1222.chapt02;

public class TestIntegerSyn {

    public static void main(String[] args) {
        Worker worker = new Worker(1);
        for (int i =0 ;i<5;i++){
            new Thread(worker).start();
        }

    }
    private static class Worker implements Runnable{

        private Integer i;

        public Worker(Integer i){
            this.i=i;
        }

        @Override
        public void run() {
            synchronized (this){
                Thread thread = Thread.currentThread();
                //System.out.println(thread.getName()+ "  ---  "+System.identityHashCode(i));
                i++;
                //System.out.println(thread.getName()+ "---1---"+"  ---  "+System.identityHashCode(i));

                System.out.println(thread.getName()+ "---"+i+"---"+"  ---  "+System.identityHashCode(i));
                //System.out.println( "i is :>>>>"+i+"---");
            }

        }
    }
}
