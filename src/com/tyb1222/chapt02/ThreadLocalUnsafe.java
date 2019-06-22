package com.tyb1222.chapt02;

public class ThreadLocalUnsafe implements Runnable {

    Number number = new Number(0);

    ThreadLocal<Number> threadLocal = new ThreadLocal<>(){
        @Override
        public Number initialValue(){
            return new Number(0);
        }
    };


    @Override
    public void run() {
        number.setNum(number.getNum() + 1);
        threadLocal.set(number);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("current number is :" + number.getNum());
    }


    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            new Thread(new ThreadLocalUnsafe()).start();
        }

    }

    private static class Number{
        int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        private Number(int num){
            this.num = num;
        }
    }



}
