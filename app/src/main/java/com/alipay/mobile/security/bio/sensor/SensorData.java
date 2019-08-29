package com.alipay.mobile.security.bio.sensor;

public class SensorData {
    String a;
    String b;
    String c;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.a);
        sb.append(",");
        sb.append(this.b);
        sb.append(",");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }
}
