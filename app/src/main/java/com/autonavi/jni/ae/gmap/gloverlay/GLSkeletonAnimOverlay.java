package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLSkeletonAnimOverlay extends GLOverlay {
    private static native void nativeAddSkeletonAnimItem(long j, byte[] bArr, int i);

    public GLSkeletonAnimOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_SKELETON.ordinal());
    }

    public void addItem(byte[] bArr) {
        nativeAddSkeletonAnimItem(this.mNativeInstance, bArr, this.mItemPriority);
    }
}
