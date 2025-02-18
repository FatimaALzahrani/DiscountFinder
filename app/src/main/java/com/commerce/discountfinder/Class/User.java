package com.commerce.discountfinder.Class;

public class User {
    private String uid;
    private String Email;
    private String Name;
    private String role;
    private String Phone;

    public User(String name, String email, String phone) {
        Name = name;
        Email = email;
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public User() {
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String uid, String role) {
        this.uid = uid;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public String getRole() {
        return role;
    }
}
