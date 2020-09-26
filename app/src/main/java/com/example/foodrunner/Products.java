package com.example.foodrunner;

public class Products {

    String name;
    String category;
    Float price;

    public Products(String name, String category, Float price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Products() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Float getPrice() {
        return price;
    }
}
