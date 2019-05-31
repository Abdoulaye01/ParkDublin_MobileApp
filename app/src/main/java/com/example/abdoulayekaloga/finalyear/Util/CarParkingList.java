package com.example.abdoulayekaloga.finalyear.Util;

import androidx.annotation.NonNull;

public class CarParkingList {
    private  String location ="";
    private  String name = "";
    private  String id ="";
    private  int spaces;
    private String website_link ="";


    public CarParkingList(String location, String name, String id, int spaces, String website_link) {
        this.location = location;
        this.name = name;
        this.id = id;
        this.spaces = spaces;
        this.website_link = website_link;
    }

    public CarParkingList() {
        //this constructor is required for datasnaphot
    }

    //Getter and Setter

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    @NonNull
    @Override
    public String toString() {
        return "CarParkingList{" +
                "Location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", spaces=" + spaces +
                ", Website_link='" + website_link + '\'' +
                '}';
    }
}
