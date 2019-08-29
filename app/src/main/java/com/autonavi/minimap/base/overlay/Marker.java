package com.autonavi.minimap.base.overlay;

public class Marker extends amb {
    public int mAnchor = 4;
    public int mHeight;
    public int mID = -999;
    public int mWidth;

    public Marker(int i, int i2, int i3, int i4) {
        this.mID = i;
        this.mAnchor = i2;
        this.mWidth = i3;
        this.mHeight = i4;
    }
}
