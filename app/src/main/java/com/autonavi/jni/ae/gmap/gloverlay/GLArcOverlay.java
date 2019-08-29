package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLArcOverlay extends GLOverlay {
    private int mP20X = 0;
    private int mP20Y = 0;

    private static native void nativeAddArcItem(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nativeSetArcPosition(long j, int i, int i2, boolean z);

    public GLArcOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_ARC.ordinal());
    }

    public void addArcOverlayItem(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        nativeAddArcItem(this.mNativeInstance, i, i2, i4, i6, i3);
    }

    public void setArcItemPosition(int i, int i2, boolean z) {
        this.mP20X = i;
        this.mP20Y = i2;
        nativeSetArcPosition(this.mNativeInstance, i, i2, z);
    }

    public void getArcItemPosition(GLGeoPoint gLGeoPoint) {
        gLGeoPoint.x = this.mP20X;
        gLGeoPoint.y = this.mP20Y;
    }
}
