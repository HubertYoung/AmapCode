package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.jni.ae.gmap.GLMapEngine;

public abstract class GLOverlay {
    public static final int OverlayDrawEventTypeAll = -1;
    public static final int OverlayDrawEventTypeFinished = 1;
    public static final int OverlayDrawEventTypeNone = 0;
    protected boolean isNightStyle = false;
    protected int mCode;
    private OnDrawOverlayListener mDrawOverlayListener = null;
    protected int mEngineID;
    protected akq mGLMapView;
    protected boolean mIsInBundle = false;
    protected int mItemPriority = 0;
    protected long mNativeInstance = 0;
    private int mOverlayDrawObserverType = 0;

    public enum AnimationStatusType {
        AnimationStatusTypeNone(0),
        AnimationStatusTypeStart(1),
        AnimationStatusTypeDoing(2),
        AnimationStatusTypeNormalEnd(3),
        AnimationStatusTypeForceEnd(4),
        AnimationStatusTypeRemoved(5);
        
        private final int value;

        private AnimationStatusType(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }

        public static AnimationStatusType valueOf(int i) {
            switch (i) {
                case 1:
                    return AnimationStatusTypeStart;
                case 2:
                    return AnimationStatusTypeDoing;
                case 3:
                    return AnimationStatusTypeNormalEnd;
                case 4:
                    return AnimationStatusTypeForceEnd;
                case 5:
                    return AnimationStatusTypeRemoved;
                default:
                    return AnimationStatusTypeNone;
            }
        }
    }

    public enum EAMapOverlayTpye {
        AMAPOVERLAY_POINT,
        AMAPOVERLAY_POLYLINE,
        AMAPOVERLAY_POLYGON,
        AMAPOVERLAY_NAVI,
        AMAPOVERLAY_GPS,
        AMAPOVERLAY_ARC,
        AMAPOVERLAY_ARROW,
        AMAPOVERLAY_VECTOR,
        AMAPOVERLAY_MODEL,
        AMAPOVERLAY_RCTROUTE,
        AMAPOVERLAY_ROUTE,
        AMAPOVERLAY_WATERWAVE,
        AMAPOVERLAY_PLANE,
        AMAPOVERLAY_RASTER,
        AMAPOVERLAY_SKELETON
    }

    public enum EOverlaySubType {
        ESubTypeMember(20180622);
        
        private int mValue;

        private EOverlaySubType(int i) {
            this.mValue = i;
        }

        public final int value() {
            return this.mValue;
        }
    }

    public interface OnDrawOverlayListener {
        void onProcessOverlayDrawEvent(OverlayDrawEvent overlayDrawEvent);
    }

    public static class OverlayAnimationEvent {
        public long mAnimationObject;
        public int mEngineID;
        public AnimationStatusType mStatus;

        public OverlayAnimationEvent(int i, int i2, long j) {
            this.mStatus = AnimationStatusType.valueOf(i);
            this.mEngineID = i2;
            this.mAnimationObject = j;
        }
    }

    public interface OverlayAnimationListener {
        void onProcessOverlayAnimationEvent(OverlayAnimationEvent overlayAnimationEvent);
    }

    public static class OverlayDrawEvent {
        int mEventType;
        GLOverlay mOverlay;

        public OverlayDrawEvent(GLOverlay gLOverlay, int i) {
            this.mOverlay = gLOverlay;
            this.mEventType = i;
        }

        public GLOverlay getOverlay() {
            return this.mOverlay;
        }

        public int getEventType() {
            return this.mEventType;
        }
    }

    private static native int nativeGetCount(long j);

    private static native float[] nativeGetDisplayScale(long j);

    private static native int nativeGetOverlayDrawObserverType(long j, GLOverlay gLOverlay);

    private static native int nativeGetOverlayPriority(long j);

    private static native int nativeGetSubType(long j);

    private static native int nativeGetType(long j);

    private static native boolean nativeIsClickable(long j);

    private static native boolean nativeIsVisible(long j);

    private static native void nativeRemoveAll(long j);

    private static native void nativeRemoveItem(long j, int i);

    private static native void nativeSetClickable(long j, boolean z);

    private static native void nativeSetMaxDisplayLevel(long j, float f);

    private static native void nativeSetMinDisplayLevel(long j, float f);

    private static native void nativeSetOverlayDrawObserver(long j, OnDrawOverlayListener onDrawOverlayListener, GLOverlay gLOverlay);

    private static native void nativeSetOverlayDrawObserverType(long j, int i, GLOverlay gLOverlay);

    private static native void nativeSetOverlayItemPriority(long j, int i);

    private static native void nativeSetOverlayOnTop(long j, boolean z);

    private static native void nativeSetOverlayPriority(long j, int i);

    private static native void nativeSetOverlayPriority(long j, int i, int i2);

    private static native void nativeSetOverlaySubType(long j, int i);

    private static native void nativeSetShownMaxCount(long j, int i);

    protected static native void nativeSetVisible(long j, boolean z);

    public void clearFocus() {
    }

    public GLOverlay(int i, akq akq, int i2) {
        this.mEngineID = i;
        this.mGLMapView = akq;
        this.mCode = i2;
        this.mNativeInstance = 0;
        this.mItemPriority = 0;
    }

    public long getNativeInstatnce() {
        return this.mNativeInstance;
    }

    public int getCode() {
        return this.mCode;
    }

    public int getType() {
        if (this.mNativeInstance == 0) {
            return -1;
        }
        return nativeGetType(this.mNativeInstance);
    }

    public int getSubType() {
        if (this.mNativeInstance == 0) {
            return -1;
        }
        return nativeGetSubType(this.mNativeInstance);
    }

    public void removeItem(int i) {
        if (this.mNativeInstance != 0) {
            nativeRemoveItem(this.mNativeInstance, i);
        }
    }

    public void removeAll() {
        if (this.mNativeInstance != 0) {
            nativeRemoveAll(this.mNativeInstance);
            if (this.mGLMapView != null) {
                this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
            }
        }
    }

    public int getSize() {
        if (this.mNativeInstance == 0) {
            return 0;
        }
        return nativeGetCount(this.mNativeInstance);
    }

    public void setVisible(boolean z) {
        if (this.mNativeInstance != 0) {
            nativeSetVisible(this.mNativeInstance, z);
            this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
        }
    }

    public boolean isVisible() {
        if (this.mNativeInstance == 0) {
            return false;
        }
        return nativeIsVisible(this.mNativeInstance);
    }

    public void setClickable(boolean z) {
        if (this.mNativeInstance != 0) {
            nativeSetClickable(this.mNativeInstance, z);
        }
    }

    public boolean isClickable() {
        if (this.mNativeInstance == 0) {
            return false;
        }
        return nativeIsClickable(this.mNativeInstance);
    }

    public boolean getIsInBundle() {
        return this.mIsInBundle;
    }

    public void setMaxCountShown(int i) {
        nativeSetShownMaxCount(this.mNativeInstance, i);
    }

    public void setOverlayOnTop(boolean z) {
        nativeSetOverlayOnTop(this.mNativeInstance, z);
    }

    public void setMinDisplayLevel(float f) {
        nativeSetMinDisplayLevel(this.mNativeInstance, f);
    }

    public void setMaxDisplayLevel(float f) {
        nativeSetMaxDisplayLevel(this.mNativeInstance, f);
    }

    public void setOverlayPriority(int i, int i2) {
        if (0 != this.mNativeInstance) {
            nativeSetOverlayPriority(this.mNativeInstance, i, i2);
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public void setOverlayPriority(int i) {
        nativeSetOverlayPriority(this.mNativeInstance, i);
        GLOverlayBundle<BaseMapOverlay<?, ?>> s = this.mGLMapView.s(this.mEngineID);
        if (s != null) {
            s.sortOverlay();
        }
    }

    public int getOverlayPriority() {
        return nativeGetOverlayPriority(this.mNativeInstance);
    }

    public void setOverlayItemPriority(int i) {
        this.mItemPriority = i;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        releaseInstance();
        super.finalize();
    }

    public void releaseInstance() {
        setDrawOverlayListener(null);
        if (this.mNativeInstance != 0) {
            long j = this.mNativeInstance;
            this.mNativeInstance = 0;
            GLMapEngine.destoryOverlay(this.mEngineID, j);
        }
    }

    public float getMinDisplayLevel() {
        float[] displayScale = getDisplayScale();
        if (displayScale == null || displayScale.length != 2) {
            return 0.0f;
        }
        return displayScale[0];
    }

    public float getMaxDisplayLevel() {
        float[] displayScale = getDisplayScale();
        if (displayScale == null || displayScale.length != 2) {
            return 0.0f;
        }
        return displayScale[1];
    }

    public float[] getDisplayScale() {
        if (this.mNativeInstance == 0) {
            return null;
        }
        return nativeGetDisplayScale(this.mNativeInstance);
    }

    public void setSubType(EOverlaySubType eOverlaySubType) {
        if (this.mNativeInstance != 0) {
            nativeSetOverlaySubType(this.mNativeInstance, eOverlaySubType.value());
        }
    }

    public void useNightStyle(boolean z) {
        this.isNightStyle = z;
    }

    public void setDrawOverlayListener(OnDrawOverlayListener onDrawOverlayListener) {
        this.mDrawOverlayListener = onDrawOverlayListener;
        if (this.mNativeInstance != 0) {
            nativeSetOverlayDrawObserver(this.mNativeInstance, this.mDrawOverlayListener, this);
            if (!(this.mOverlayDrawObserverType == 0 || this.mDrawOverlayListener == null)) {
                nativeSetOverlayDrawObserverType(this.mNativeInstance, this.mOverlayDrawObserverType, this);
            }
        }
    }

    public void setOverlayDrawObserverType(int i) {
        this.mOverlayDrawObserverType = i;
        if (this.mNativeInstance != 0) {
            nativeSetOverlayDrawObserverType(this.mNativeInstance, i, this);
        }
    }

    public int getOverlayDrawObserverType() {
        if (this.mNativeInstance == 0 || this.mDrawOverlayListener == null) {
            return this.mOverlayDrawObserverType;
        }
        return nativeGetOverlayDrawObserverType(this.mNativeInstance, this);
    }
}
