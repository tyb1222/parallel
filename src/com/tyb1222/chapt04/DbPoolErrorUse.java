package com.tyb1222.chapt04;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class DbPoolErrorUse {
    final static int POOL_SIZE = 10 ;

    final Semaphore useful, useless;

    static LinkedList<Connection> pool = new LinkedList<>();

    static DbPoolErrorUse dbPoolErrorUse = new DbPoolErrorUse();

    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectionImpl.fetchConnection());
        }
    }

    public DbPoolErrorUse(){
        this.useful= new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    public void releaseConnection(Connection connection) throws InterruptedException {
        useless.acquire();
        if (connection!=null){
            System.out.println("当前有"+useful.getQueueLength()+"等待连接！！！"+ "目前可用连接数为： " + useful.availablePermits());
        }
        synchronized (pool){
            pool.addLast(connection);
        }
        useful.release();
    }

    public Connection getConnection() throws InterruptedException {
        useful.acquire();
        Connection connection;
        synchronized (pool){
            connection = pool.removeFirst();
        }
        useless.release();
        return connection;
    }

    static class SubThread extends Thread{
        @Override
        public void run(){
            Random random = new Random();
            long start = System.currentTimeMillis();
            try {
//                Connection connection = dbPoolErrorUse.getConnection();
                long end = System.currentTimeMillis();
                System.out.println("Thread " +Thread.currentThread().getId()+"获连接耗时: "+ (end - start));
                Thread.sleep(random.nextInt(100));
                System.out.println("release connection");
                dbPoolErrorUse.releaseConnection(new SqlConnectionImpl());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }
}
