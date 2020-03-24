package com.aistar.pojo;

public class Customer {
    private String username;
    private String userpassword;

    public Customer() {
    }

    public Customer(String username, String userpassword) {
        this.username = username;
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                '}';
    }
}
