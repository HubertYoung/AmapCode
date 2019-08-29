package com.autonavi.minimap.route.sharebike.model;

public class BicycleBasicItem extends AbsBicycleItem {
    private String iconCode = "";
    private String id;
    private String source;
    private double x;
    private double y;

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getIconCode() {
        return this.iconCode;
    }

    public void setIconCode(String str) {
        this.iconCode = str;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double d) {
        this.x = d;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double d) {
        this.y = d;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}
