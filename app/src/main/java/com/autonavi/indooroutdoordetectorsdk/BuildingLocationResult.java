package com.autonavi.indooroutdoordetectorsdk;

import android.text.format.DateFormat;

public class BuildingLocationResult {
    public String bid;
    public int d = 0;
    public long t = System.currentTimeMillis();
    public double x = 0.0d;
    public double y = 0.0d;
    public int z = 0;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DateFormat.format("yyyy-MM-dd HH:mm:ss", this.t));
        sb.append(" Result: ");
        sb.append(this.bid);
        sb.append(", ");
        sb.append(this.d);
        sb.append(", ");
        sb.append(this.z);
        sb.append(" Floor (");
        sb.append(this.x);
        sb.append(",");
        sb.append(this.y);
        sb.append("), ");
        sb.append(this.t);
        return sb.toString();
    }
}
