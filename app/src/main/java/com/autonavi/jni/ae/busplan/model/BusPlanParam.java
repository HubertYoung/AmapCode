package com.autonavi.jni.ae.busplan.model;

import java.io.Serializable;

public class BusPlanParam implements Serializable {
    public String ad1 = "";
    public String ad2 = "";
    public String eta = "9";
    public String req_cli = "0";
    public String req_num = "1";
    public String server_ver = "0";
    public String timestamp = "";
    public String type = "0";
    public double x1 = 0.0d;
    public double x2 = 0.0d;
    public double y1 = 0.0d;
    public double y2 = 0.0d;

    public BusPlanParam(double d, double d2, double d3, double d4) {
        this.x1 = d;
        this.y1 = d2;
        this.x2 = d3;
        this.y2 = d4;
    }

    public void setType(int i) {
        this.type = String.valueOf(i);
    }
}
