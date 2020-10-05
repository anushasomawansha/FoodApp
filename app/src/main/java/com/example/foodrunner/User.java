package com.example.foodrunner;

public class User {

    private String email;
    private String name;
    private String mobile;
    private String location;
    private String password;

    public User() {
    }

    public User(String email, String name, String mobileNo, String location, String password) {
        this.email = email;
        this.name = name;
        this.mobile = mobile;
        this.location = location;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobileNo(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
