package com.tyb1222.chapt03;

import java.sql.Connection;
import java.util.LinkedList;

public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    public DBPool(int initialSize){
        if (initialSize >0){
            for (int i = 0; i<initialSize;i++){
                pool.add(SqlConnectionImpl.fetchConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if (null!= connection){
            synchronized (pool){
                pool.add(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException{
        synchronized (pool){
            if (0>= mills){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }
            long now = System.currentTimeMillis();
            long future = now+ mills;
            long remaining = mills;
            while (pool.isEmpty()
                    && remaining>=0){
                pool.wait(mills);
                remaining = future - System.currentTimeMillis();
            }
            Connection connection = null;
            if (!pool.isEmpty()){
                connection = pool.removeFirst();
            }
            return connection;
        }
    }

}
