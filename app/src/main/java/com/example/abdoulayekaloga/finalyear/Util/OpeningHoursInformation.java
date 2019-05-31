package com.example.abdoulayekaloga.finalyear.Util;

public class OpeningHoursInformation {

    //Instance Variable
    private String id;
    private  String address;
    private  String phone;
    private String mon_wed;
    private String thur;
    private  String fri;
    private String sat;
    private  String sun;
    private  String dayParking;

    public OpeningHoursInformation() {
        //this constructor is used for the constructor
    }

    public OpeningHoursInformation(String id, String address, String phone, String mon_wed, String thur, String fri, String sat, String sun, String dayParking) {
        this.id =id;
        this.address = address;
        this.phone = phone;
        this.mon_wed = mon_wed;
        this.thur = thur;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.dayParking =dayParking;
    }


    //Getter and Setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMon_wed() {
        return mon_wed;
    }

    public void setMon_wed(String mon_wed) {
        this.mon_wed = mon_wed;
    }

    public String getThur() {
        return thur;
    }

    public void setThur(String thur) {
        this.thur = thur;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getDayParking() {
        return dayParking;
    }

    public void setDayParking(String dayParking) {
        this.dayParking = dayParking;
    }
}
