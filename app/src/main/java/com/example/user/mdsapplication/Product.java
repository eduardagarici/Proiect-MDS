package com.example.user.mdsapplication;

import java.io.Serializable;

public class Product implements Serializable{
    private int id;
    private String resourceImage;
    private String name;
    private int quantity;
    private double pricePerUnit;

    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Product(){
        this.quantity = 0;
    }

    public Product(int id, String resourceImage, String name, double pricePerUnit) {
        this.id = id;
        this.resourceImage = resourceImage;
        this.name = name;
        this.quantity = 0;
        this.pricePerUnit = pricePerUnit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPrice(int price) {
        this.pricePerUnit = price;
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
