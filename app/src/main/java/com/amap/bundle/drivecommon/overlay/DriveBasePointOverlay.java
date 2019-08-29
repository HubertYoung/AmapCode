package com.amap.bundle.drivecommon.overlay;

import android.graphics.Rect;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay;
import defpackage.sz;

public abstract class DriveBasePointOverlay<E extends sz> extends PointOverlay<E> {
    public DriveBasePointOverlay(bty bty) {
        super(bty);
    }

    public DriveBasePointOverlay(int i, bty bty) {
        super(i, bty);
    }

    public bty getMapView() {
        return this.mMapView;
    }

    public GLPointOverlay getGLOverlay() {
        return (GLPointOverlay) super.getGLOverlay();
    }

    public E getFocus() {
        return (sz) super.getFocus();
    }

    public Rect getBound() {
        if (getSize() == 0) {
            return null;
        }
        int i = 0;
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MIN_VALUE;
        int i5 = Integer.MIN_VALUE;
        while (i < this.mItemList.size()) {
            try {
                sz szVar = (sz) this.mItemList.get(i);
                i2 = Math.min(i2, szVar.getGeoPoint().x);
                i3 = Math.min(i3, szVar.getGeoPoint().y);
                i4 = Math.max(i4, szVar.getGeoPoint().x);
                i5 = Math.max(i5, szVar.getGeoPoint().y);
                i++;
            } catch (Exception unused) {
                return null;
            }
        }
        Rect rect = new Rect();
        rect.set(i2, i3, i4, i5);
        return rect;
    }

    public boolean onPointOverlayClick(int i) {
        if (!((GLPointOverlay) this.mGLOverlay).isVisible() || !((GLPointOverlay) this.mGLOverlay).isClickable() || i < 0 || i >= this.mItemList.size()) {
            return false;
        }
        sz szVar = (sz) this.mItemList.get(i);
        if (szVar == null || (szVar.mDefaultMarker != null && szVar.mDefaultMarker.mID == -999 && this.mOverlayDefaultMarker != null && this.mOverlayDefaultMarker.mID == -999)) {
            return false;
        }
        if (this.mAutoSetFocus) {
            setFocus(i, true);
        }
        if (this.mOnItemClickListener != null && szVar.f) {
            this.mOnItemClickListener.onItemClick(this.mMapView, this, szVar);
        }
        return true;
    }

    public void setOverlayPriority(int i) {
        ((GLPointOverlay) this.mGLOverlay).setOverlayPriority(i);
    }

    public boolean isVisible() {
        return this.mGLOverlay != null && ((GLPointOverlay) this.mGLOverlay).isVisible();
    }
}
