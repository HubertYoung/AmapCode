package com.autonavi.indoor.entity;

public class WorldPoint {
    public double angle;
    public double angle_error;
    public double distance;
    public int layerId;
    public double x;
    public double y;

    public WorldPoint(double d, double d2, int i, double d3, double d4, double d5) {
        this.x = d;
        this.y = d2;
        this.layerId = i;
        this.distance = d3;
        this.angle = d4;
        this.angle_error = d5;
    }
}
