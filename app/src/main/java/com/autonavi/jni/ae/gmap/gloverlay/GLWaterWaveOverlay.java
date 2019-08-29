package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLWaterWaveOverlay extends GLOverlay {
    protected static native void nativeRestartAnimation(long j);

    protected static native void nativeSetParam(long j, int i, int i2, int i3, int i4);

    public GLWaterWaveOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_WATERWAVE.ordinal());
    }

    public void SetParam(int i, int i2, int i3, int i4) {
        if (this.mNativeInstance != 0) {
            nativeSetParam(this.mNativeInstance, i, i2, i3, i4);
        }
    }

    public void RestartAnimation() {
        if (this.mNativeInstance != 0) {
            nativeRestartAnimation(this.mNativeInstance);
        }
    }
}
