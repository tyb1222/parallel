package com.tyb1222.chapt04;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampDemo {


    static User user = new User("tao",20);

    static AtomicStampedReference<User> asr = new AtomicStampedReference<>(user,0);

    static class User{
        String name;

        int age;


        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp();
        final User user = asr.getReference();
        Thread rightThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"current variable is :"+ user + " and old stamp is "+oldStamp);
                User update = new User("wang",28);
                System.out.println(asr.compareAndSet(user,update,oldStamp,oldStamp+1)
                + "    ----------after cas ,user is :" + asr.getReference()+"...stamp is :"+ asr.getStamp()) ;
            }
        });

        Thread errorThread = new Thread(() -> {
            User oldRef = asr.getReference();
            System.out.println(Thread.currentThread().getName()+"current variable is :"+ oldRef + " and old stamp is "+asr.getStamp());
            User update = new User("TTTWWW",48);
            System.out.println(asr.compareAndSet(oldRef,update,oldStamp,oldStamp+1)
                    + "    ----------after cas ,user is :" + asr.getReference()+"...stamp is :"+ asr.getStamp()) ;
            System.out.println("new stamp is : "+ asr.getStamp());
        });
//        rightThread.start();
//        rightThread.join();
        errorThread.start();
        errorThread.join();
    }
}
