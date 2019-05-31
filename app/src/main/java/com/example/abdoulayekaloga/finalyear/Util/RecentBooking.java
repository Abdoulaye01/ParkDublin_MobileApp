package com.example.abdoulayekaloga.finalyear.Util;

public class RecentBooking {
    private  String carNumberPlate;
    private  String carparkingName;
    private  String typeofProduct;
    private  String price;
    private  String username;

    public RecentBooking() {
    }

    public RecentBooking(String carNumberPlate, String carparkingName, String typeofProduct, String price, String username) {
        this.carNumberPlate = carNumberPlate;
        this.carparkingName = carparkingName;
        this.typeofProduct = typeofProduct;
        this.price = price;
        this.username = username;
    }

    public String getCarNumberPlate() {
        return carNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        this.carNumberPlate = carNumberPlate;
    }

    public String getCarparkingName() {
        return carparkingName;
    }

    public void setCarparkingName(String carparkingName) {
        this.carparkingName = carparkingName;
    }

    public String getTypeofProduct() {
        return typeofProduct;
    }

    public void setTypeofProduct(String typeofProduct) {
        this.typeofProduct = typeofProduct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
