package com.example.user.mdsapplication;

public class Product{
    private String name;
    private int quantity;
    private double price;

    Product(String name, int quantity, double price){
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
