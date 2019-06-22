package com.tyb1222.chapt04;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class UseExchange {
    static final Exchanger<Set<String>> exchages = new Exchanger<Set<String>>();


    public static void main(String[] args) {
        new Thread(() -> {
            Set<String> setA = new HashSet<>();
            try{
                setA.add("A");
                setA= exchages.exchange(setA);
                for(String str:setA){
                    System.out.println(str +"  in setA");
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            Set<String> setB = new HashSet<>();
            try{
                setB.add("B");
                setB= exchages.exchange(setB);
                for(String str:setB){
                    System.out.println(str +"  in setB >>>>");
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}