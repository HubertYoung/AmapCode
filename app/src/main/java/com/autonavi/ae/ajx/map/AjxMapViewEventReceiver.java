package com.autonavi.ae.ajx.map;

public class AjxMapViewEventReceiver {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native long createNativeObj();

    private static native void destroyNativeObj(long j);

    private static native void onEngineActionGesture(int i, int i2, int i3, boolean z);

    private static native boolean onLineOverlayClick(int i, float f, float f2);

    private static native void onLongPress(int i, float f, float f2);

    private static native void onMapAnimationFinished(int i, int i2, int i3, int i4);

    private static native void onMapLevelChange(int i, boolean z);

    private static native void onMapTouchEvent(int i, float f, float f2, int i2);

    private static native void onMotionFinished(int i, int i2);

    private static native boolean onPointOverlayClick(int i, float f, float f2);

    private static native void onSingleTapUp(int i, float f, float f2);

    protected AjxMapViewEventReceiver(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(AjxMapViewEventReceiver ajxMapViewEventReceiver) {
        if (ajxMapViewEventReceiver == null) {
            return 0;
        }
        return ajxMapViewEventReceiver.swigCPtr;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        delete();
    }

    public synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                destroyNativeObj(this.swigCPtr);
            }
            this.swigCPtr = 0;
        }
    }

    public static void onLongPressS(int i, float f, float f2) {
        onLongPress(i, f, f2);
    }

    public static boolean onPointOverlayClickS(int i, float f, float f2) {
        return onPointOverlayClick(i, f, f2);
    }

    public static boolean onLineOverlayClickS(int i, float f, float f2) {
        return onLineOverlayClick(i, f, f2);
    }

    public static void onSingleTapUpS(int i, float f, float f2) {
        onSingleTapUp(i, f, f2);
    }

    public static void onEngineActionGestureS(int i, int i2, int i3, boolean z) {
        onEngineActionGesture(i, i2, i3, z);
    }

    public static void onMapLevelChangeS(int i, boolean z) {
        onMapLevelChange(i, z);
    }

    public static void onMotionFinishedS(int i, int i2) {
        onMotionFinished(i, i2);
    }

    public static void onMapTouchEventS(int i, float f, float f2, int i2) {
        onMapTouchEvent(i, f, f2, i2);
    }

    public static void onMapAnimationFinishedS(int i, int i2, int i3, int i4) {
        onMapAnimationFinished(i, i2, i3, i4);
    }

    public AjxMapViewEventReceiver() {
        this(createNativeObj(), true);
    }
}
