package com.autonavi.jni.ae.gmap;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

public class GLMapState {
    private boolean is_new_instance;
    private long native_engine_instance;
    private long native_state_instance;

    public static native float nativeCalMapZoomScalefactor(int i, int i2, float f, float f2);

    public static native float nativeCalcMapZoomLevel(long j, int i, int i2, int i3, int i4, int i5, int i6, float f);

    public static native float nativeCalcMapZoomLevelWithMapRect(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float f);

    public static native float nativeGetCameraDegree(long j);

    public static native float nativeGetGLUnitWithWin(long j, int i);

    public static native float nativeGetGLUnitWithWinByY(long j, int i, int i2);

    public static native float nativeGetMapAngle(long j);

    public static native void nativeGetMapCenter(long j, Point point);

    public static native void nativeGetMapViewBound(long j, Rect rect);

    public static native void nativeGetMapViewLeftTop(long j, Point point);

    public static native void nativeGetMapViewLeftTopPercent(long j, PointF pointF);

    public static native float nativeGetMapZoomer(long j);

    public static native float nativeGetMaxCameraHeadeRangle();

    public static native int nativeGetMaxZoomLevel(long j);

    public static native int nativeGetMinZoomLevel(long j);

    public static native void nativeGetPixel20Bound(long j, Rect rect);

    public static native float nativeGetWinSkyHeight(long j);

    public static native long nativeNewInstance(int i, long j);

    public static native void nativeP20ToScreenPoint(long j, int i, int i2, int i3, PointF pointF);

    public static native void nativeRecalculate(long j);

    public static native void nativeScreenToP20Point(long j, float f, float f2, Point point);

    public static native void nativeSetCameraDegree(long j, float f);

    public static native void nativeSetMapAngle(long j, float f);

    public static native void nativeSetMapCenter(long j, int i, int i2);

    public static native void nativeSetMapState(int i, long j, long j2);

    public static native void nativeSetMapViewLeftTop(long j, float f, float f2);

    public static native void nativeSetMapViewLeftTopPercent(long j, float f, float f2);

    public static native void nativeSetMapZoomer(long j, float f);

    public static native void nativeSetMaxZoomLevel(long j, float f);

    public static native void nativeSetMinZoomLevel(long j, float f);

    public static native void nativeSetMovableArea(long j, double d, double d2, double d3, double d4);

    public static native void nativeSetProjectionCenter(long j, float f, float f2);

    public static native void nativeStateDestroy(long j);

    public float getGLUnitWithPixel20(int i) {
        return (float) i;
    }

    public GLMapState(int i, long j) {
        this.native_state_instance = 0;
        this.native_engine_instance = 0;
        this.is_new_instance = false;
        if (j != 0) {
            this.native_engine_instance = j;
            this.native_state_instance = nativeNewInstance(i, j);
            this.is_new_instance = true;
        }
    }

    public GLMapState() {
        this.native_state_instance = 0;
        this.native_engine_instance = 0;
        this.is_new_instance = false;
        this.native_state_instance = 0;
    }

    public void setMapstateInstance(long j) {
        this.native_state_instance = j;
    }

    public void recycle() {
        if (this.native_state_instance != 0 && this.is_new_instance) {
            nativeStateDestroy(this.native_state_instance);
        }
        this.native_state_instance = 0;
    }

    public long getNativeInstance() {
        return this.native_state_instance;
    }

    public long getMapengineInstance() {
        return this.native_engine_instance;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.native_state_instance != 0 && this.is_new_instance) {
            nativeStateDestroy(this.native_state_instance);
        }
        this.native_state_instance = 0;
    }

    public void setNativeMapengineState(int i, long j) {
        if (j != 0 && this.native_state_instance != 0) {
            this.native_engine_instance = j;
            nativeSetMapState(i, j, this.native_state_instance);
        }
    }

    public Point getMapGeoCenter() {
        Point point = new Point();
        nativeGetMapCenter(this.native_state_instance, point);
        return point;
    }

    public void setMapGeoCenter(int i, int i2) {
        nativeSetMapCenter(this.native_state_instance, i, i2);
    }

    public void setProjectionCenter(float f, float f2) {
        nativeSetProjectionCenter(this.native_state_instance, f, f2);
    }

    public float getCameraDegree() {
        return nativeGetCameraDegree(this.native_state_instance);
    }

    public void setCameraDegree(float f) {
        nativeSetCameraDegree(this.native_state_instance, f);
    }

    public float getMapAngle() {
        return nativeGetMapAngle(this.native_state_instance);
    }

    public void setMapAngle(float f) {
        if (f < 0.0f) {
            f += 360.0f;
        } else if (f >= 360.0f) {
            f -= 360.0f;
        }
        nativeSetMapAngle(this.native_state_instance, f);
    }

    public void SetMapViewLeftTop(float f, float f2) {
        nativeSetMapViewLeftTop(this.native_state_instance, f, f2);
    }

    public float getMapZoomer() {
        return nativeGetMapZoomer(this.native_state_instance);
    }

    public float getWinSkyHeight() {
        return nativeGetWinSkyHeight(this.native_state_instance);
    }

    public void setMapZoomer(float f) {
        if (f > ((float) nativeGetMaxZoomLevel(this.native_state_instance))) {
            f = (float) nativeGetMaxZoomLevel(this.native_state_instance);
        }
        nativeSetMapZoomer(this.native_state_instance, f);
    }

    public void recalculate() {
        nativeRecalculate(this.native_state_instance);
    }

    public Rect getMapViewBound() {
        Rect rect = new Rect();
        nativeGetMapViewBound(this.native_state_instance, rect);
        return rect;
    }

    public void p20ToScreenPoint(int i, int i2, PointF pointF) {
        nativeP20ToScreenPoint(this.native_state_instance, i, i2, 0, pointF);
    }

    public void screenToP20Point(float f, float f2, Point point) {
        nativeScreenToP20Point(this.native_state_instance, f, f2, point);
    }

    public float getGLUnitWithWin(int i) {
        return nativeGetGLUnitWithWin(this.native_state_instance, i);
    }

    public float getGLUnitWithWinByY(int i, int i2) {
        return nativeGetGLUnitWithWinByY(this.native_state_instance, i, i2);
    }

    public void getPixel20Bound(Rect rect) {
        nativeGetPixel20Bound(this.native_state_instance, rect);
    }

    public void p20ToMapPoint(int i, int i2, PointF pointF) {
        Point mapGeoCenter = getMapGeoCenter();
        pointF.x = (float) (i - mapGeoCenter.x);
        pointF.y = (float) (i2 - mapGeoCenter.y);
    }

    public void mapToScreenPointWithZ(float f, float f2, float f3, PointF pointF) {
        Point mapGeoCenter = getMapGeoCenter();
        nativeP20ToScreenPoint(this.native_state_instance, ((int) f) + mapGeoCenter.x, ((int) f2) + mapGeoCenter.y, (int) f3, pointF);
    }
}
