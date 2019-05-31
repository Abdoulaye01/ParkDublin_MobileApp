package com.example.abdoulayekaloga.finalyear.Util;

public class ProductDetail {

    //Instance Variable
    private String id;
    private String image;
    private String title;
    private  String description;
    private  double price;
    private  String branch;
    private  double displayprice;


    public ProductDetail() {

        //this constructor is used for firebase datasnapshot

    }

    public ProductDetail(String image, String title, String description, double price, String id,String branch,double displayprice) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.price = price;
        this.id =id;
        this.branch =branch;
        this.displayprice = displayprice;
    }

    public ProductDetail(double price,String description, String branch) {
        this.description = description;
        this.price = price;
        this.branch = branch;
    }


    //Getter and Setter


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public double getDisplayprice() {
        return displayprice;
    }

    public void setDisplayprice(double displayprice) {
        this.displayprice = displayprice;
    }
}
