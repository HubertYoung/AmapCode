package com.autonavi.jni.ae.gmap.gloverlay;

import android.graphics.Rect;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLCrossVector extends GLOverlay {
    private int mErrorCode = 0;

    public static class AVectorCrossAttr {
        public boolean dayMode = true;
        public int fArrowBorderWidth;
        public int fArrowLineWidth;
        public int stAreaColor;
        public Rect stAreaRect;
        public int stArrowBorderColor;
        public int stArrowLineColor;
    }

    public static class AVectorCrossLink {
        public int nCount = 0;
        public AVectorCrossLinkAttr stAttr = new AVectorCrossLinkAttr();
        public int[] xs;
        public int[] ys;
        public int[] zs;
    }

    public static class AVectorCrossLinkAttr {
        public int IsExitLink = 0;
        public int IsRoute = 0;
        public int fDashLineWidth;
        public int fImportBorderWidth;
        public int fImportLineWidth;
        public int fUnImportBorderWidth;
        public int fUnImportLineWidth;
        public int nAngle;
        public int nCenterX;
        public int nCenterY;
        public int nFormway = 0;
        public int nRoadClass = 0;
        public int stDashLineColor;
        public int stImportBorderColor;
        public int stImportLineColor;
        public Rect stRectMax;
        public Rect stRectMin;
        public int stUnImportBorderColor;
        public int stUnImportLineColor;
    }

    private static native void nativeAddVectorCar(long j, int i, int i2, int i3);

    private static native int nativeAddVectorData(long j, int[] iArr, byte[] bArr);

    private static native void nativeSetArrowResId(long j, boolean z, int i);

    private static native void nativeSetBackgroundResId(long j, int i);

    private static native void nativeSetCarResId(long j, int i);

    public void addVectorRemainDis(int i) {
    }

    public void setRoadResId(boolean z, int i) {
    }

    public void setSkyResId(boolean z, int i) {
    }

    public GLCrossVector(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_VECTOR.ordinal());
    }

    private int copy2Inter(int[] iArr, int i, AVectorCrossLink aVectorCrossLink) {
        int i2 = aVectorCrossLink.nCount;
        System.arraycopy(aVectorCrossLink.xs, 0, iArr, i, i2);
        int i3 = i + i2;
        System.arraycopy(aVectorCrossLink.ys, 0, iArr, i3, i2);
        int i4 = i3 + i2;
        System.arraycopy(aVectorCrossLink.zs, 0, iArr, i4, i2);
        return i4 + i2;
    }

    public boolean addVectorItem(AVectorCrossAttr aVectorCrossAttr, byte[] bArr, int i) {
        if (aVectorCrossAttr == null || bArr == null || i == 0) {
            return false;
        }
        this.mErrorCode = nativeAddVectorData(this.mNativeInstance, new int[]{aVectorCrossAttr.stAreaRect.left, aVectorCrossAttr.stAreaRect.top, aVectorCrossAttr.stAreaRect.right, aVectorCrossAttr.stAreaRect.bottom, aVectorCrossAttr.stAreaColor, aVectorCrossAttr.fArrowBorderWidth, aVectorCrossAttr.stArrowBorderColor, aVectorCrossAttr.fArrowLineWidth, aVectorCrossAttr.stArrowLineColor, aVectorCrossAttr.dayMode}, bArr);
        if (this.mErrorCode == 0) {
            return true;
        }
        return false;
    }

    public void addVectorCar(int i, int i2, int i3) {
        nativeAddVectorCar(this.mNativeInstance, i, i2, i3);
    }

    public void setArrowResId(boolean z, int i) {
        nativeSetArrowResId(this.mNativeInstance, z, i);
    }

    public void setCarResId(int i) {
        nativeSetCarResId(this.mNativeInstance, i);
    }

    public void setBackgroundResId(int i) {
        nativeSetBackgroundResId(this.mNativeInstance, i);
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}
