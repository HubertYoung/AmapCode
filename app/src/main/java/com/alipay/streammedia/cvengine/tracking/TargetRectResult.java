package com.alipay.streammedia.cvengine.tracking;

public class TargetRectResult {
    public int X;
    public int Y;
    public int height;
    public boolean isLost;
    public int width;

    public void setIsLost(boolean isLost2) {
        this.isLost = isLost2;
    }

    public boolean isLost() {
        return this.isLost;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setX(int x) {
        this.X = x;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }
}
