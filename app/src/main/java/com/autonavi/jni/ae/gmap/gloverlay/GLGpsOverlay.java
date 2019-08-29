package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLGpsOverlay extends GLOverlay {
    GPSValues mGpsValue = new GPSValues();

    public enum EAMapGPSDirection {
        AMAP_GPS_DIRECTION_NORTH,
        AMAP_GPS_DIRECTION_HEADER
    }

    static class GPSValues {
        int nAngle = 0;
        int nArcMarker = 0;
        int nGeoX = 0;
        int nGeoY = 0;
        int nGpsMarker = 0;
        int nRadius = 0;
        int nShineMarker = 0;

        GPSValues() {
        }
    }

    private native void nativeSetGpsDirection(long j, int i);

    private static native void nativeSetGpsOverlayCenterLocked(long j, boolean z);

    private native void nativeSetGpsOverlayItem(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    public GLGpsOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_GPS.ordinal());
    }

    public void setGpsDirection(EAMapGPSDirection eAMapGPSDirection) {
        nativeSetGpsDirection(this.mNativeInstance, eAMapGPSDirection.ordinal());
    }

    public void setGpsOverlayCenterLocked(boolean z) {
        nativeSetGpsOverlayCenterLocked(this.mNativeInstance, z);
    }

    public void setGpsOverlayItem(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = i;
        int i9 = i2;
        int i10 = i3;
        int i11 = i4;
        int i12 = i5;
        int i13 = i6;
        int i14 = i7;
        int abs = Math.abs(this.mGpsValue.nGeoX - i8) + Math.abs(this.mGpsValue.nGeoY - i9);
        int abs2 = Math.abs(this.mGpsValue.nAngle - i11);
        if (abs >= 2 || this.mGpsValue.nRadius != i10 || abs2 >= 2 || this.mGpsValue.nGpsMarker != i12 || this.mGpsValue.nShineMarker != i13 || this.mGpsValue.nArcMarker != i14) {
            this.mGpsValue.nGeoX = i8;
            this.mGpsValue.nGeoY = i9;
            this.mGpsValue.nRadius = i10;
            this.mGpsValue.nAngle = i11;
            this.mGpsValue.nGpsMarker = i12;
            this.mGpsValue.nShineMarker = i13;
            this.mGpsValue.nArcMarker = i14;
            nativeSetGpsOverlayItem(this.mNativeInstance, i8, i9, i10, i11, i12, i13, i14);
        }
    }

    public void removeAll() {
        if (this.mNativeInstance != 0) {
            this.mGpsValue.nGeoX = 0;
            this.mGpsValue.nGeoY = 0;
            this.mGpsValue.nRadius = 0;
            this.mGpsValue.nAngle = 0;
            this.mGpsValue.nGpsMarker = 0;
            this.mGpsValue.nShineMarker = 0;
            this.mGpsValue.nArcMarker = 0;
        }
    }
}
