package com.commerce.discountfinder.Class;

public class Offer {
    private String storeName;
    private String storeDescription;
    private String storeImageUrl;
    private double storeLatitude;
    private double storeLongitude;
    private String offerId;
    private String rating;
    private String time;
    private String type;

    // Constructor
    public Offer(String storeName, String storeDescription, String storeImageUrl,
                 double storeLatitude, double storeLongitude, String offerId,
                 String rating, String time, String type) {
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeImageUrl = storeImageUrl;
        this.storeLatitude = storeLatitude;
        this.storeLongitude = storeLongitude;
        this.offerId = offerId;
        this.rating = rating;
        this.time = time;
        this.type = type;
    }

    public Offer(){}

    // Getters and setters for each field
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public String getStoreImageUrl() {
        return storeImageUrl;
    }

    public void setStoreImageUrl(String storeImageUrl) {
        this.storeImageUrl = storeImageUrl;
    }

    public double getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(double storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public double getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(double storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
