package com.ant.phone.imu.sensor;

public class SystemClock implements Clock {
    public final long a() {
        return System.nanoTime();
    }
}
