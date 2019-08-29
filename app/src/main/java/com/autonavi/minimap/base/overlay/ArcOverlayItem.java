package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;

public class ArcOverlayItem {
    public int mBorder;
    public int mColor;
    public int mColorBorder;
    public Marker mDefaultMarker;
    public int mRadius;
    public int mX;
    public int mY;

    public ArcOverlayItem(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mX = i;
        this.mY = i2;
        this.mRadius = i3;
        this.mBorder = i4;
        this.mColor = i5;
        this.mColorBorder = i6;
    }

    public GeoPoint getGeoPoint() {
        return new GeoPoint(this.mX, this.mY);
    }
}
