package com.example.android.smartdispatcher;

import com.google.android.gms.maps.model.LatLng;

public class locations {
    private LatLng latLng;
    private int position;
    private String address;

    public locations() {

    }

    public locations(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
