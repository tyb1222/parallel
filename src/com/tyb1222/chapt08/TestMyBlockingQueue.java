package com.tyb1222.chapt08;

import java.util.concurrent.atomic.AtomicReference;

public class TestMyBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue<Integer> queue = new MyLockedBlockingQueue<>(5);
        put5Elemets(queue);
        take7Elemensts(queue);
        Thread.sleep(1*1000);
        System.out.println("replay put method");
        put5Elemets(queue);
    }

    public static void put5Elemets(MyBlockingQueue<Integer> queue) throws InterruptedException {
        queue.put(1);
        queue.put(2);
        queue.put(3);
        queue.put(4);
        queue.put(5);
    }


    private static Integer take7Elemensts(MyBlockingQueue<Integer> queue) throws InterruptedException {
        AtomicReference<Integer> result = new AtomicReference<>();
        Thread thread = new Thread(()->{
            for (int i = 0; i < 7; i++) {
                try {
                    result.set(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return result.get();
    }
}
