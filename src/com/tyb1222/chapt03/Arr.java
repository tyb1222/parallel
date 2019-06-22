package com.tyb1222.chapt03;

public class Arr {
    public static final int ARRAY_LENGTH = 10000;
    public static final int THRESHOLD =13;


    public static int[] getArr(){
       int [] result = new int[ARRAY_LENGTH];
       for (int i =0;i< ARRAY_LENGTH;i++){
           result[i] = i* THRESHOLD;
       }
       return result;
    }
}
