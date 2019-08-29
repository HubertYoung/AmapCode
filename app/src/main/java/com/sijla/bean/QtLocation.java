package com.sijla.bean;

public class QtLocation extends Info {
    private double Latitude;
    private double Longitude;
    private String address;
    private String city;
    private String district;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String str) {
        this.district = str;
    }

    public double getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(double d) {
        this.Longitude = d;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(double d) {
        this.Latitude = d;
    }
}
