package com.tyb1222.chapt03;

public class Sum {

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        int [] arr = Arr.getArr();
        long begin =System.currentTimeMillis();
        for (int i = 0; i < arr.length ; i++) {
            Thread.sleep(1);
            count += arr[i];
        }
        long end = System.currentTimeMillis();
        System.out.println("count is : " +count +"time costs: "+ (end- begin));
    }
}
