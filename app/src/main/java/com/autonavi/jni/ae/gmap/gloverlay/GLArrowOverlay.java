package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLArrowOverlay extends GLOverlay {
    private static native void nativeSet2DArrowNeedFilter(long j, boolean z);

    private static native void nativeSet3DArrow(long j, int[] iArr, int[] iArr2, int[] iArr3);

    private static native void nativeSet3DArrowVisible(long j, boolean z);

    private static native void nativeSetArrow(long j, int[] iArr, int[] iArr2, int i, int i2, int i3);

    private static native void nativeSetArrow3DAttr(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, float f, boolean z, boolean z2, boolean z3, boolean z4, int i8, float f2, int i9, int i10);

    private static native void nativeSetArrow3DShowAttr(long j, boolean z, boolean z2);

    private static native void nativeSetHeaderTextuerInfo(long j, float f, float f2, float f3, float f4);

    private static native void nativeSetRoundCapTexInfo(long j, float f, float f2, float f3, float f4);

    private static native void nativeSetShadowCapTexInfo(long j, float f, float f2, float f3, float f4);

    private static native void nativeSetShadowHeaderTextuerInfo(long j, float f, float f2, float f3, float f4);

    private static native void nativeSetShadowInfo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nativeSetTexture(long j, int i, int i2);

    private static native void nativeSetTextureInfo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    public GLArrowOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_ARROW.ordinal());
    }

    public void setTexture(int i, int i2) {
        this.mGLMapView.d.getGLUnitWithWin(this.mEngineID, 32);
        nativeSetTexture(this.mNativeInstance, i, i2);
    }

    public void setRoundCapTexInfo(float f, float f2, float f3, float f4) {
        nativeSetRoundCapTexInfo(this.mNativeInstance, f, f2, f3, f4);
    }

    public void setHeaderTextuerInfo(float f, float f2, float f3, float f4) {
        nativeSetHeaderTextuerInfo(this.mNativeInstance, f, f2, f3, f4);
    }

    public void setTextureInfo(float f, float f2, float f3, float f4, float f5, float f6) {
        nativeSetTextureInfo(this.mNativeInstance, f, f2, f3, f4, f5, f6);
    }

    public void setArrow(int[] iArr, int[] iArr2, int i, int i2, int i3) {
        nativeSetArrow(this.mNativeInstance, iArr, iArr2, i3, i, i2);
    }

    public void setArrow3DPoint(int[] iArr, int[] iArr2, int[] iArr3) {
        nativeSet3DArrow(this.mNativeInstance, iArr, iArr2, iArr3);
    }

    public void set3DArrowVisible(boolean z) {
        nativeSet3DArrowVisible(this.mNativeInstance, z);
    }

    public void set2DArrowNeedFilter(boolean z) {
        nativeSet2DArrowNeedFilter(this.mNativeInstance, z);
    }

    public void setShadowCapTexInfo(float f, float f2, float f3, float f4) {
        nativeSetShadowCapTexInfo(this.mNativeInstance, f, f2, f3, f4);
    }

    public void setShadowHeaderTextuerInfo(float f, float f2, float f3, float f4) {
        nativeSetShadowHeaderTextuerInfo(this.mNativeInstance, f, f2, f3, f4);
    }

    public void setShadowInfo(float f, float f2, float f3, float f4, float f5, float f6) {
        nativeSetShadowInfo(this.mNativeInstance, f, f2, f3, f4, f5, f6);
    }

    public void setArrow3DAttr(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        nativeSetArrow3DAttr(this.mNativeInstance, i, i2, i3, i4, i5, i6, i7, 3.0f, false, true, true, false, 0, 150.0f, 0, 0);
    }

    public void setArrow3DAttrInfo(alx alx) {
        alx alx2 = alx;
        if (alx2 != null) {
            nativeSetArrow3DAttr(this.mNativeInstance, alx2.a, alx2.b, alx2.c, alx2.d, alx2.e, alx2.f, alx2.g, alx2.h, alx2.i, alx2.j, alx2.k, alx2.l, alx2.m, alx2.n, alx2.o, alx2.p);
        }
    }

    public void setArrow3DShow(boolean z, boolean z2) {
        nativeSetArrow3DShowAttr(this.mNativeInstance, z, z2);
    }
}
