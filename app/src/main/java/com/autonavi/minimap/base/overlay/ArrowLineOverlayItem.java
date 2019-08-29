package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;

public class ArrowLineOverlayItem extends LineOverlayItem {
    public int mColor;
    public int mColorSide;
    public int mInnerTextureId;
    private boolean mIsTurn;
    public int mOuterTextureId;
    public GeoPoint[] mPoints3D;
    public int mShadowTextureId;
    public int mWidth;

    public ArrowLineOverlayItem(GeoPoint[] geoPointArr, int i, int i2, int i3) {
        super(1, geoPointArr, i);
        this.mPoints = geoPointArr;
        this.mWidth = i;
        this.mColor = i2;
        this.mColorSide = i3;
    }

    public void setInnerTexturedId(int i) {
        this.mInnerTextureId = i;
    }

    public void setOuterTexturedId(int i) {
        this.mOuterTextureId = i;
    }

    public void setShadowTextureId(int i) {
        this.mShadowTextureId = i;
    }

    public void setIsTurn(boolean z) {
        this.mIsTurn = z;
    }

    public boolean isTurn() {
        return this.mIsTurn;
    }
}
