package com.tyb1222.chapt02;

public class Express {
    public final static String CITY ="Shanghai";

    private int km;
    private String site;
    private int date;

    public Express(){

    }

    public Express(int km, String site,int date) {
        this.km = km;
        this.site = site;
        this.date = date;
    }

    public synchronized void changeKM(){
        this.km =101;
        notify();
    }

    public synchronized void changeSite(){
        this.site = "Beijing";
        notifyAll();
    }

    public synchronized void changeDate(){
        this.date = 5;
        notifyAll();
    }

    public synchronized void waiteKm() throws InterruptedException {
        while (this.km<100){
            wait();
            System.out.println("km thread :>>>" + Thread.currentThread().getName()+" has be notified ");
        }

        System.out.println("the KM is "+this.km+",I will write to db");
    }

    public synchronized void waiteSite() throws InterruptedException {
        while (this.site.equals(CITY)){
            wait();
            System.out.println("site thread " + Thread.currentThread().getName()+" has be notified ");
        }
        System.out.println("the site is "+this.site+",<<<<");
    }
}
