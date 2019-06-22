package com.tyb1222.chapt04;

import java.util.concurrent.atomic.AtomicReference;

public class UserAtomic {

    static AtomicReference<User> ar =new AtomicReference<>();

    static class User{
        String name;

        int age;


        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        User user = new User("tao",30);
        ar.set(user);
        System.out.println("before update "+ar.get());
        User updateUser = new User("wang",20);
        ar.compareAndSet(user,updateUser);
        System.out.println("after update "+ar.get());
        System.out.println(user);
    }
}
