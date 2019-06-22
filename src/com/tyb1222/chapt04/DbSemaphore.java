package com.tyb1222.chapt04;

import java.awt.*;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DbSemaphore {
    static int POOL_SIZE =10;

    private final Semaphore useful, useless;

    static LinkedList<Connection> pool = new LinkedList<>();

    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(SqlConnectionImpl.fetchConnection());
        }
    }

    public DbSemaphore(){
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    public void releaseConnection(Connection connection) throws InterruptedException {
        if (connection!=null){
            System.out.println("当前有"+useful.getQueueLength()+"等待连接！！！"+ "目前可用连接数为： " + useful.availablePermits());
        }
        useless.acquire();
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
}
