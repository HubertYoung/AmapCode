package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLRasterOverlay extends GLOverlay {

    public static class GLRasterOverlayParam {
        public float mAlpha = 1.0f;
        public float mAngle = 0.0f;
        public alp mLeftBottom;
        public int mResourceID = -1;
        public alp mRightTop;
        public boolean mVisible = true;
        public int mZIndex = 0;
    }

    protected static native long nativeAddRasterItem(long j, byte[] bArr);

    protected static native long nativeAddRasterItemWithResourceId(long j, int i, float f, float f2, int i2, boolean z, double d, double d2, double d3, double d4);

    protected static native float nativeGetRasterItemAlpha(long j);

    protected static native float nativeGetRasterItemAngle(long j);

    protected static native boolean nativeGetRasterItemVisible(long j);

    protected static native int nativeGetRasterItemZIndex(long j);

    protected static native void nativeRemoveRasterItem(long j, long j2);

    protected static native void nativeSetRasterItemAlpha(long j, float f);

    protected static native void nativeSetRasterItemAngle(long j, float f);

    protected static native void nativeSetRasterItemBound(long j, double d, double d2, double d3, double d4);

    protected static native void nativeSetRasterItemVisible(long j, boolean z);

    protected static native void nativeSetRasterItemZIndex(long j, long j2, int i);

    public GLRasterOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_RASTER.ordinal());
    }

    public long addRasterItem(byte[] bArr) {
        if (this.mGLMapView == null || !this.mGLMapView.a(this.mEngineID) || bArr == null || bArr.length < 42) {
            return 0;
        }
        long nativeAddRasterItem = nativeAddRasterItem(this.mNativeInstance, bArr);
        this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        return nativeAddRasterItem;
    }

    public void removeRasterItem(long j) {
        if (0 != j && 0 != this.mNativeInstance) {
            nativeRemoveRasterItem(this.mNativeInstance, j);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public long addRasterItemWithParam(GLRasterOverlayParam gLRasterOverlayParam) {
        GLRasterOverlayParam gLRasterOverlayParam2 = gLRasterOverlayParam;
        if (this.mGLMapView == null || !this.mGLMapView.a(this.mEngineID)) {
            return 0;
        } else if (gLRasterOverlayParam2 == null || gLRasterOverlayParam2.mResourceID < 0) {
            return 0;
        } else {
            long nativeAddRasterItemWithResourceId = nativeAddRasterItemWithResourceId(this.mNativeInstance, gLRasterOverlayParam2.mResourceID, gLRasterOverlayParam2.mAlpha, gLRasterOverlayParam2.mAngle, gLRasterOverlayParam2.mZIndex, gLRasterOverlayParam2.mVisible, gLRasterOverlayParam2.mLeftBottom.a, gLRasterOverlayParam2.mLeftBottom.b, gLRasterOverlayParam2.mRightTop.a, gLRasterOverlayParam2.mRightTop.b);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
            return nativeAddRasterItemWithResourceId;
        }
    }

    public void setRasterItemAlpha(long j, float f) {
        if (0 != j) {
            nativeSetRasterItemAlpha(j, f);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public float getRasterItemAlpha(long j) {
        if (0 == j) {
            return 0.0f;
        }
        return nativeGetRasterItemAlpha(j);
    }

    public void setRasterItemAngle(long j, float f) {
        if (0 != j) {
            nativeSetRasterItemAngle(j, f);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public float getRasterItemAngle(long j) {
        if (0 == j) {
            return 0.0f;
        }
        return nativeGetRasterItemAngle(j);
    }

    public void setRasterItemVisible(long j, boolean z) {
        if (0 != j) {
            nativeSetRasterItemVisible(j, z);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public boolean getRasterItemVisible(long j) {
        if (0 == j) {
            return false;
        }
        return nativeGetRasterItemVisible(j);
    }

    public void setRasterItemBound(long j, alp alp, alp alp2) {
        if (0 != j) {
            nativeSetRasterItemBound(j, alp.a, alp.b, alp2.a, alp2.b);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void setRasterItemZIndex(long j, int i) {
        if (0 != this.mNativeInstance && 0 != j) {
            nativeSetRasterItemZIndex(this.mNativeInstance, j, i);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public int getRasterItemZIndex(long j) {
        if (0 == j) {
            return 0;
        }
        return nativeGetRasterItemZIndex(j);
    }
}
