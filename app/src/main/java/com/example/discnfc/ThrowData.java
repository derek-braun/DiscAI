package com.example.discnfc;

import android.location.Location;

public class ThrowData {
    private int holeNumber;
    private int stroke;
    private String discID;
    private Location location;

    public ThrowData(int holeNumber, int stroke, String discID, Location location){
        this.holeNumber = holeNumber;
        this.stroke = stroke;
        this.discID = discID;
        this.location = location;
    }
}
