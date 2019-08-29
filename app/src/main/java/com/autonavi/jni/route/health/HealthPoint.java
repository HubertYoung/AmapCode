package com.autonavi.jni.route.health;

public class HealthPoint implements Cloneable {
    public double accuracy;
    public int angle;
    public long gps_time;
    public double latitude;
    public double longitude;
    public double speed;
    public HealthPointStatus status;

    public HealthPoint clone() {
        try {
            return (HealthPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
