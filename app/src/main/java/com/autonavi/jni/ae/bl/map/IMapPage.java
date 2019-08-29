package com.autonavi.jni.ae.bl.map;

public class IMapPage {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native void addOverlay(long j, IMapPage iMapPage, long j2, long j3, long j4);

    private static native void destroyNativeObj(long j);

    private static native String getIdentifyName(long j, IMapPage iMapPage);

    private static native long getMapStateVirtualFlags(long j, IMapPage iMapPage);

    private static native void onCreate(long j, IMapPage iMapPage);

    private static native void onDestroy(long j, IMapPage iMapPage);

    private static native void onStart(long j, IMapPage iMapPage);

    private static native void onStop(long j, IMapPage iMapPage);

    private static native void removeOverlay(long j, IMapPage iMapPage, long j2, long j3);

    private static native void setMapStateVirtualFlags(long j, IMapPage iMapPage, long j2);

    protected IMapPage(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(IMapPage iMapPage) {
        if (iMapPage == null) {
            return 0;
        }
        return iMapPage.swigCPtr;
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

    public void onStart() {
        onStart(this.swigCPtr, this);
    }

    public void onStop() {
        onStop(this.swigCPtr, this);
    }

    public void onCreate() {
        onCreate(this.swigCPtr, this);
    }

    public void onDestroy() {
        onDestroy(this.swigCPtr, this);
    }

    public String getIdentifyName() {
        return getIdentifyName(this.swigCPtr, this);
    }

    public void addOverlay(long j, long j2, long j3) {
        addOverlay(this.swigCPtr, this, j, j2, j3);
    }

    public void removeOverlay(long j, long j2) {
        removeOverlay(this.swigCPtr, this, j, j2);
    }

    public void setMapStateVirtualFlags(long j) {
        setMapStateVirtualFlags(this.swigCPtr, this, j);
    }

    public long getMapStateVirtualFlags() {
        return getMapStateVirtualFlags(this.swigCPtr, this);
    }
}
