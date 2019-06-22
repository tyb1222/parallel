package com.tyb1222.chapt05;

public class GoodsInfo {
    String name;

    float price;

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public GoodsInfo(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public void changePrice(float price) {
        this.price = price;
    }
}
