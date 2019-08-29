package com.autonavi.jni.ae.gmap.gloverlay;

import android.graphics.Point;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.jni.ae.gmap.GLMapEngine;

public class GLNaviOverlay extends GLOverlay {
    int mBearing;
    int mP3dx;
    int mP3dy;
    float mP3dz;
    int mPx;
    int mPy;
    float mfBearing = 0.0f;
    float mfPitch;

    private static native boolean nativeNaviIsClickable(long j);

    private static native boolean nativeNaviIsVisible(long j);

    private static native void nativeNaviRemoveAll(long j);

    private static native void nativeNaviSetClickable(long j, boolean z);

    protected static native void nativeNaviSetVisible(long j, boolean z);

    private static native void nativeSetArcColorsAndAngles(long j, int i, int i2, int i3, int i4, int i5);

    /* access modifiers changed from: private */
    public static native void nativeSetArcInfo(long j, int i, int i2, int i3, int i4);

    private static native void nativeSetCar3DPosition(long j, int i, int i2, float f, float f2, float f3);

    private static native void nativeSetCarAnimationTime(long j, int i);

    private static native void nativeSetCarPosition(long j, int i, int i2, int i3, int i4);

    private static native void nativeSetCompassMarkerTextures(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nativeSetDirIndicatorAngle(long j, int i);

    private static native void nativeSetEnd3DPoint(long j, int i, int i2, float f);

    private static native void nativeSetEndPoint(long j, int i, int i2);

    private static native void nativeSetMaxCameraDegree(long j, float f);

    private static native void nativeSetNaviEndLine(long j, int i, int i2, int i3);

    private static native void nativeSetNaviTextures(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nativeSetNaviTexturesEx(long j, int i, int i2, int i3, int i4, int i5, int i6);

    private static native void nativeSetOcclusionCar(long j, boolean z);

    public void clearFocus() {
    }

    public int getOverlayPriority() {
        return 0;
    }

    public void setMaxCountShown(int i) {
    }

    public void setMaxDisplayLevel(float f) {
    }

    public void setMinDisplayLevel(float f) {
    }

    public void setOverlayItemPriority(int i) {
    }

    public void setOverlayOnTop(boolean z) {
    }

    public void setOverlayPriority(int i) {
    }

    public GLNaviOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createNaviOverlayController(i);
    }

    public void setArcInfo(int i, int i2, int i3, int i4) {
        int C = this.mGLMapView.C(this.mEngineID);
        akq akq = this.mGLMapView;
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        AnonymousClass1 r2 = new Runnable() {
            public void run() {
                GLNaviOverlay.nativeSetArcInfo(GLNaviOverlay.this.mNativeInstance, i5, i6, i7, i8);
            }
        };
        akq.a(C, (Runnable) r2);
    }

    public void setArcColorsAndAngles(int i, int i2, int i3, int i4, int i5) {
        nativeSetArcColorsAndAngles(this.mNativeInstance, i, i2, i3, i4, i5);
    }

    public void setDirIndicatorAngle(int i) {
        nativeSetDirIndicatorAngle(this.mNativeInstance, i);
    }

    public void SetNaviTexture(int i, int i2, int i3, int i4, int i5) {
        nativeSetNaviTextures(this.mNativeInstance, i5, i3, i, i2, i4);
        if (this.mGLMapView != null) {
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void SetNaviTexture(int i, int i2, int i3, int i4, int i5, int i6) {
        nativeSetNaviTexturesEx(this.mNativeInstance, i6, i4, i, i2, i3, i5);
        if (this.mGLMapView != null) {
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void SetCompassMarkerTextures(int i, int i2, int i3, int i4, int i5) {
        nativeSetCompassMarkerTextures(this.mNativeInstance, i, i2, i3, i4, i5);
        if (this.mGLMapView != null) {
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void setEndLineTexture(int i, int i2, int i3) {
        nativeSetNaviEndLine(this.mNativeInstance, i3, i2, i);
    }

    public void setEndPoint(int i, int i2) {
        nativeSetEndPoint(this.mNativeInstance, i, i2);
    }

    public void setEnd3DPoint(int i, int i2, float f) {
        nativeSetEnd3DPoint(this.mNativeInstance, i, i2, f);
    }

    public void setCarPosition(int i, int i2, int i3) {
        if (this.mNativeInstance != 0) {
            nativeSetCarPosition(this.mNativeInstance, i, i2, 0, i3);
        }
        this.mPx = i;
        this.mPy = i2;
        this.mBearing = i3;
    }

    public void setCarAnimationTime(int i) {
        if (this.mNativeInstance != 0) {
            nativeSetCarAnimationTime(this.mNativeInstance, i);
        }
    }

    public Point getCarPosition() {
        return new Point(this.mPx, this.mPy);
    }

    public int getCarAngle() {
        return this.mBearing;
    }

    public void setMaxCameraDegree(float f) {
        if (this.mNativeInstance != 0) {
            nativeSetMaxCameraDegree(this.mNativeInstance, f);
        }
    }

    public void setCar3DPostion(int i, int i2, float f, float f2, float f3) {
        this.mP3dx = i;
        this.mP3dy = i2;
        this.mP3dz = f;
        this.mfPitch = f2;
        this.mfBearing = f3;
        if (this.mNativeInstance != 0) {
            nativeSetCar3DPosition(this.mNativeInstance, i, i2, f, f2, f3);
        }
    }

    public void setOcclusionCar(boolean z) {
        if (this.mNativeInstance != 0) {
            nativeSetOcclusionCar(this.mNativeInstance, z);
        }
    }

    public GLGeoPoint getCar3DPosition() {
        return new GLGeoPoint(this.mP3dx, this.mP3dy, this.mP3dz);
    }

    public float getCarPitch() {
        return this.mfPitch;
    }

    public float get3DBearing() {
        return this.mfBearing;
    }

    public int getType() {
        return this.mNativeInstance == 0 ? -1 : 3;
    }

    public int getSubType() {
        return this.mNativeInstance == 0 ? -1 : 0;
    }

    public void removeItem(int i) {
        if (this.mNativeInstance != 0) {
        }
    }

    public void removeAll() {
        if (this.mNativeInstance != 0) {
            nativeNaviRemoveAll(this.mNativeInstance);
            if (this.mGLMapView != null) {
                this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
            }
        }
    }

    public int getSize() {
        return this.mNativeInstance == 0 ? 0 : 0;
    }

    public void setVisible(boolean z) {
        if (this.mNativeInstance != 0) {
            nativeNaviSetVisible(this.mNativeInstance, z);
            this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
        }
    }

    public boolean isVisible() {
        if (this.mNativeInstance == 0) {
            return false;
        }
        return nativeNaviIsVisible(this.mNativeInstance);
    }

    public void setClickable(boolean z) {
        if (this.mNativeInstance != 0) {
            nativeNaviSetClickable(this.mNativeInstance, z);
        }
    }

    public boolean isClickable() {
        if (this.mNativeInstance == 0) {
            return false;
        }
        return nativeNaviIsClickable(this.mNativeInstance);
    }

    public boolean getIsInBundle() {
        return this.mIsInBundle;
    }

    public void releaseInstance() {
        if (this.mNativeInstance != 0) {
            long j = this.mNativeInstance;
            this.mNativeInstance = 0;
            GLMapEngine.destroyNaviOverlayController(this.mEngineID, j);
        }
    }
}
