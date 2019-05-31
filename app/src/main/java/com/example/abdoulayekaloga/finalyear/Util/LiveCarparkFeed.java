package com.example.abdoulayekaloga.finalyear.Util;

public class LiveCarparkFeed {

    //instance variables
    private  String name;
    private  String spaces;
    private  String timestamp;

    public LiveCarparkFeed(String name, String spacess,String timestamp) {
        this.name = name;
        this.spaces = spaces;
        this.timestamp =timestamp;

    }

    public LiveCarparkFeed() {
        //this constructor is required for datasnapshot
    }

    //Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}