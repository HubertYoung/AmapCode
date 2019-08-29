package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLPolygonOverlay extends GLOverlay {
    private static native void nativeAddPolyGonItem(long j, int[] iArr, int[] iArr2, int i, int i2);

    public GLPolygonOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_POLYGON.ordinal());
    }

    public void addItem(GLGeoPoint[] gLGeoPointArr, int i) {
        if (gLGeoPointArr != null && gLGeoPointArr.length >= 3) {
            int length = gLGeoPointArr.length;
            int[] iArr = new int[length];
            int[] iArr2 = new int[length];
            for (int i2 = 0; i2 < length; i2++) {
                iArr[i2] = gLGeoPointArr[i2].x;
                iArr2[i2] = gLGeoPointArr[i2].y;
            }
            nativeAddPolyGonItem(this.mNativeInstance, iArr, iArr2, i, this.mItemPriority);
        }
    }
}
