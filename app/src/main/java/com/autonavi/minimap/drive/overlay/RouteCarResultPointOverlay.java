package com.autonavi.minimap.drive.overlay;

import android.graphics.Rect;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointItem;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;

public abstract class RouteCarResultPointOverlay<E extends DriveBaseBoardPointItem> extends DriveBaseBoardPointOverlay<E> {
    public RouteCarResultPointOverlay(bty bty) {
        super(bty);
    }

    public E getFocus() {
        return (DriveBaseBoardPointItem) super.getFocus();
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
                DriveBaseBoardPointItem driveBaseBoardPointItem = (DriveBaseBoardPointItem) this.mItemList.get(i);
                i2 = Math.min(i2, driveBaseBoardPointItem.getGeoPoint().x);
                i3 = Math.min(i3, driveBaseBoardPointItem.getGeoPoint().y);
                i4 = Math.max(i4, driveBaseBoardPointItem.getGeoPoint().x);
                i5 = Math.max(i5, driveBaseBoardPointItem.getGeoPoint().y);
                i++;
            } catch (Exception unused) {
                return null;
            }
        }
        Rect rect = new Rect();
        rect.set(i2, i3, i4, i5);
        return rect;
    }

    public void setOverlayPriority(int i) {
        ((GLPointOverlay) this.mGLOverlay).setOverlayPriority(i);
    }
}
