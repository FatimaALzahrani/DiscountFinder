package com.commerce.discountfinder.Class;

public class Shop {
    private String id;
    private String name;
    private String location;
    private String discount;
    private String duration;  // مدة العرض

    public Shop() {
        // Constructor فارغ مطلوب من Firebase
    }

    public Shop(String id, String name, String location, String discount, String duration) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.discount = discount;
        this.duration = duration;
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
