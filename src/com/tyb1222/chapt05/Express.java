package com.tyb1222.chapt05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Express {
    public final static String CITY ="Shanghai";
    static final Lock kmLock = new ReentrantLock();
    static final Lock siteLock = new ReentrantLock();
    Condition kmCondition = kmLock.newCondition();
    Condition siteCondition = siteLock.newCondition();

    private int km;
    private String site;
    private int date;

    public Express(){

    }

    public Express(int km, String site, int date) {
        this.km = km;
        this.site = site;
        this.date = date;
    }

    public synchronized void changeKM(){
        kmLock.lock();
        try {
            this.km =101;
            kmCondition.signal();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            kmLock.unlock();
        }

    }

    public synchronized void changeSite(){
        siteLock.lock();
        try{

            this.site = "Beijing";
            siteCondition.signal();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            siteLock.unlock();
        }

    }

    public synchronized void changeDate(){
        this.date = 5;
    }

    public void waiteKm() throws InterruptedException {
        kmLock.lock();
        try{
            while (this.km<100){
                kmCondition.await();
                System.out.println("km thread :>>>" + Thread.currentThread().getName()+" has be notified ");
            }
            System.out.println("the KM is "+this.km+",I will write to db");
        }finally {
            kmLock.unlock();
        }

    }

    public void waiteSite() throws InterruptedException {
        siteLock.lock();
        try {
            while (this.site.equals(CITY)){
                siteCondition.await();
                System.out.println("site thread " + Thread.currentThread().getName()+" has be notified ");
            }
            System.out.println(Thread.currentThread().getName()+"  the site is "+this.site+",<<<<");
        }finally {
            siteLock.unlock();
        }

    }
}
