package com.example.foodrunner.CatManagement.Model;

public class Products {
    String productId;
    String imageURL;
    String name;
    Float price;
    String category;

    public Products() {
    }

    public Products(String imageURL, String name, Float price, String category) {
        //this.productId = productId;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.category = category;
    }

    public Products(String id, String imageURL, String name, Float price, String category) {

        this.productId = productId;
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.category = category;
    }


    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}