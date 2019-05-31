package com.example.abdoulayekaloga.finalyear.Util;

public class Booking {
    private  String id;
    private  String image;
    private  String name;
    private  String placeGuide;

    public  Booking(){
        //this constructor is required for the datasnaphot

    }



    public Booking(String image, String name, String placeGuide, String id) {
        this.image = image;
        this.name = name;
        this.placeGuide = placeGuide;
        this.id =id;

    }
// Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceGuide() {
        return placeGuide;
    }

    public void setPlaceGuide(String placeGuide) {
        this.placeGuide = placeGuide;
    }

}
