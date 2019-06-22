package com.tyb1222.chapt01;

public class SyncThread {
    private int count = 0;

    private Object obj ;

    public int getCount() {
        return count;
    }

    public void setCount(int count1){
        this.count = count1;
    }

    public SyncThread(){}

    public SyncThread(Object obj){
        this.obj = obj;
    }

    public void increaseCount(){
        //synchronized (obj) {
            count++;
        //}
    }
    public synchronized void  increaseCount1(){
        count++;
    }

    public void increaseCount2(){
        synchronized (this){
            count++;
        }
    }

    private static class Count extends Thread{
        private SyncThread simplOper;

        public Count(SyncThread syncThread){
            this.simplOper = syncThread;
        }

        @Override
        public void run(){
            for (int i =0;i<100000;i++){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                simplOper.increaseCount();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncThread test = new SyncThread();
        Count count1 = new Count(test);
        Count count2 = new Count(test);
        SyncThread test1 = new SyncThread();
        Count count3 = new Count(test1);
        Count count4 = new Count(test1);
        count1.start();
        count2.start();
        count3.start();
        count4.start();
        Thread.sleep(100);
        System.out.println("count is >>>  :  "+ test.count);
        System.out.println("count is >>>  :  "+ test1.count);

    }
}
