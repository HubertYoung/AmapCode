package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLPlaneOverlay extends GLOverlay {
    protected static native long nativeAddPlaneItem(long j, int i, int i2, int i3, int i4, int i5, int i6);

    protected static native void nativeRemovePlaneItem(long j, long j2);

    protected static native void nativeUpdatePlaneItem(long j, int i, int i2, int i3, int i4, int i5, int i6);

    public boolean getPlaneItemVisible(long j) {
        return true;
    }

    public void setPlaneItemVisble(long j, boolean z) {
    }

    public GLPlaneOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_PLANE.ordinal());
    }

    public long addPlaneItem(amd amd) {
        if (this.mGLMapView == null || !this.mGLMapView.a(this.mEngineID)) {
            return 0;
        }
        return nativeAddPlaneItem(this.mNativeInstance, amd.a, amd.b, amd.c, amd.d, amd.e, amd.f);
    }

    public void removePlaneItem(long j) {
        if (0 != j && 0 != this.mNativeInstance) {
            nativeRemovePlaneItem(this.mNativeInstance, j);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void updatePlaneItem(long j, amd amd) {
        if (0 != this.mNativeInstance && 0 != j) {
            nativeUpdatePlaneItem(j, amd.a, amd.b, amd.c, amd.d, amd.e, amd.f);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void releaseInstance() {
        super.releaseInstance();
    }
}
