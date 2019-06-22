package com.tyb1222.chapt03;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DBPoolTest {
    static DBPool pool = new DBPool(100);

    static CountDownLatch end;

    private static class Work implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Work(int count,AtomicInteger got,AtomicInteger notGot){
            this.count = count;
            this.got =got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count>0){
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if (null!=connection){
                        try {
                            connection.createStatement();
                            connection.commit();
                            got.incrementAndGet();
                        } catch (SQLException e) {
                            connection.rollback();
                            e.printStackTrace();
                        }
                        finally {
                            pool.releaseConnection(connection);
                        }
                    }else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+" 等待超时。。。");
                    }
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    count--;
                }
            }
            end.countDown();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGet = new AtomicInteger();
        for (int i =0 ;i< threadCount;i++){
            Thread thread = new Thread(new Work(count,got,notGet),"work" +i);
            thread.start();
        }
        end.await();
        System.out.println("总共尝试了：  "+ threadCount *count);
        System.out.println("拿到链接的次数 :  " +got);
        System.out.println("没拿到链接的次数 :  " +notGet);


    }
}
