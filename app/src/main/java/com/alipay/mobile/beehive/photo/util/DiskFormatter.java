package com.alipay.mobile.beehive.photo.util;

import java.text.DecimalFormat;

public class DiskFormatter {
    public static final String B = "B";
    public static final String FORMAT = "#.00";
    public static final String GB = "G";
    public static final String KB = "K";
    public static final String MB = "M";
    public static final String TB = "T";
    public static final int UNIT = 1024;
    private String format = FORMAT;
    private double gbUnit;
    private double kbUnit;
    private double mbUnit;
    private double tbUnit;
    private int unit = 1024;

    public DiskFormatter() {
        calUnits();
    }

    public void setUnit(int unit2) {
        if (unit2 > 0) {
            this.unit = unit2;
            calUnits();
        }
    }

    private void calUnits() {
        this.kbUnit = (double) this.unit;
        this.mbUnit = ((double) this.unit) * this.kbUnit;
        this.gbUnit = ((double) this.unit) * this.mbUnit;
        this.tbUnit = ((double) this.unit) * this.gbUnit;
    }

    public void setFormat(String format2) {
        this.format = format2;
    }

    public String format(double size) {
        if (size < 0.0d) {
            return null;
        }
        if (size < this.kbUnit) {
            return size + B;
        }
        if (size < this.mbUnit) {
            return division(size, this.kbUnit) + "KB";
        }
        if (size < this.gbUnit) {
            return division(size, this.mbUnit) + "MB";
        }
        if (size < this.tbUnit) {
            return division(size, this.gbUnit) + "GB";
        }
        return division(size, this.tbUnit) + "TB";
    }

    private String division(double size, double unit2) {
        return new DecimalFormat(this.format).format(size / unit2);
    }
}
