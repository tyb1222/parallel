package com.tyb1222.chapt03;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkSum {

    private static class SumTask extends RecursiveTask<Integer>{
        private final static int THRESHOLD = Arr.ARRAY_LENGTH/10;
        private int[] src;
        private int from;
        private int end;

        public SumTask(int[] src, int from, int end) {
            this.src = src;
            this.from = from;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - from <= THRESHOLD){
                System.out.println("from index : " + from + "to index : "+ end);
                int count = 0;
                for (int i = from; i <= end; i++) {
                    System.out.println("sum thread is :>>>" + Thread.currentThread().getId());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count += src[i];
                }
                return count;
            }else{
                int mid = (from + end) /2;
                SumTask left = new SumTask(src,from,mid);
                SumTask right = new SumTask(src,mid+1,end);
                System.out.println("fork thread is :>>>" + Thread.currentThread().getId());
                invokeAll(left,right);
                return left.join() + right.join();
            }
        }
    }


    public static void main(String[] args) {
        int [] arr = Arr.getArr();
        ForkJoinPool pool = new ForkJoinPool();
        SumTask inner = new SumTask(arr,0,arr.length-1);
        long begin = System.currentTimeMillis();
        pool.submit(inner);
        pool.invoke(inner);
        long end = System.currentTimeMillis();
        System.out.println("count is : " + inner.join());
        System.out.println("time cost : " + (end - begin));
    }
}
