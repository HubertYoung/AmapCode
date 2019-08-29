package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.PointOverlay;

public class PointOverlayItem<E extends PointOverlay> implements Cloneable {
    public Object Tag;
    private boolean bgVisible = true;
    private boolean iconVisible = true;
    public int mAngle;
    public int mAngleMode;
    public Marker mBgFocusMarker;
    public Marker mBgMarker;
    public Marker mBubbleMarker;
    public Marker mDefaultMarker;
    public Marker mFocusMarker;
    protected GeoPoint mGeoPoint;
    public long mItemId;
    public float mZ;

    public void onPrepareAddItem(E e) {
    }

    public void onPrepareSetFocus(E e) {
    }

    public PointOverlayItem(GeoPoint geoPoint) {
        this.mGeoPoint = geoPoint;
    }

    public GeoPoint getGeoPoint() {
        return this.mGeoPoint;
    }

    public void setIconVisible(boolean z) {
        this.iconVisible = z;
    }

    /* access modifiers changed from: protected */
    public void setBgVisible(boolean z) {
        this.bgVisible = z;
    }

    public boolean isIconVisible() {
        return this.iconVisible;
    }

    public boolean isBgVisible() {
        return this.bgVisible;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
