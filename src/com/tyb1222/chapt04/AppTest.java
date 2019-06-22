package com.tyb1222.chapt04;

import java.sql.Connection;
import java.util.Random;

public class AppTest {
    static DbSemaphore dbSemaphore = new DbSemaphore();

    static class SubThread extends Thread{
        @Override
        public void run(){
            Random random = new Random();
            long start = System.currentTimeMillis();
            try {
                Connection connection = dbSemaphore.getConnection();
                long end = System.currentTimeMillis();
                System.out.println("Thread " +Thread.currentThread().getId()+"获连接耗时: "+ (end - start));
                Thread.sleep(random.nextInt(100));
                System.out.println("release connection");
                dbSemaphore.releaseConnection(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new SubThread();
            thread.start();
        }
    }
}
