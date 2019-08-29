package com.alipay.mobile.map.model;

public class IndoorLocation {
    private float accuracy;
    private float angle;
    private double floor;
    private double lat;
    private double lng;
    private float reliability;

    public IndoorLocation() {
    }

    public IndoorLocation(double d, double d2) {
        this.lng = d;
        this.lat = d2;
    }

    public IndoorLocation(double d, double d2, double d3) {
        this.lng = d;
        this.lat = d2;
        this.floor = d3;
    }

    public IndoorLocation(double d, double d2, double d3, float f, float f2, float f3) {
        this.lng = d;
        this.lat = d2;
        this.floor = d3;
        this.angle = f;
        this.accuracy = f2;
        this.reliability = f3;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double d) {
        this.lat = d;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double d) {
        this.lng = d;
    }

    public double getFloor() {
        return this.floor;
    }

    public void setFloor(double d) {
        this.floor = d;
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float f) {
        this.accuracy = f;
    }

    public float getReliability() {
        return this.reliability;
    }

    public void setReliability(float f) {
        this.reliability = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[lng:");
        sb.append(this.lng);
        sb.append(",lat:");
        sb.append(this.lat);
        sb.append(",floor:");
        sb.append(this.floor);
        sb.append(",angle:");
        sb.append(this.angle);
        sb.append(",accuracy:");
        sb.append(this.accuracy);
        sb.append(",reliability:");
        sb.append(this.reliability);
        sb.append("]");
        return sb.toString();
    }
}
