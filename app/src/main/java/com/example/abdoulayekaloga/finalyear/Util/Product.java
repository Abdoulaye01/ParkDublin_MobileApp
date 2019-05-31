package com.example.abdoulayekaloga.finalyear.Util;

public class Product {

    //Instance Variable
    private  String id;
    private String image;
    private String name, description;

    public Product(){
        //this constructor is used for the firebase datasnapshot

    }
    public Product(String name){
        this.name = name;
        //this constructor is used for the firebase datasnapshot

    }

    public Product(String image, String title, String description,String id) {
        this.image = image;
        this.name = title;
        this.description = description;
        this.id =id;

    }

    //Getter and Setter
    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() { return id; }
}