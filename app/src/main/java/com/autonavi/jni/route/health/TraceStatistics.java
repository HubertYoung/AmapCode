package com.autonavi.jni.route.health;

public class TraceStatistics {
    public double average_speed;
    public int calorie;
    public HealthPoint[] gps_array;
    public double max_speed;
    public int steps;
    public int trace_length;
    public long trace_time;

    public TraceStatistics clone() {
        try {
            return (TraceStatistics) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
