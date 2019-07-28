package com.tyb1222.chapt09.threadpool;

public class TestMyThreadpool {


    public static void main(String[] args) {
        MyThreadPool threadPool = new MyThreadPool(5,100);
        threadPool.submit(new CalcTask(10));
        threadPool.submit(new CalcTask(20));
        threadPool.submit(new CalcTask(30));
        threadPool.submit(new CalcTask(40));
        threadPool.submit(new CalcTask(50));
        threadPool.submit(new CalcTask(60));
        threadPool.submit(new CalcTask(70));
        threadPool.submit(new CalcTask(80));

    }


    private static class CalcTask implements Runnable{

        int up ;

        public CalcTask(int up) {
            this.up = up;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int i = 0; i <= up; i++) {
                sum += i;
            }
            System.out.println(up + " >>> calculate over .result is " + sum);
        }
    }
}
