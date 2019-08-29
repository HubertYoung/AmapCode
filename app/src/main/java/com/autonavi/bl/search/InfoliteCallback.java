package com.autonavi.bl.search;

public class InfoliteCallback {
    protected transient boolean swigCMemOwn;
    private transient long swigCPtr;

    private static native void InfoliteCallback_change_ownership(InfoliteCallback infoliteCallback, long j, boolean z);

    private static native void InfoliteCallback_director_connect(InfoliteCallback infoliteCallback, long j, boolean z, boolean z2);

    private static native void callBack(long j, InfoliteCallback infoliteCallback, long j2, long j3, InfoliteResult infoliteResult);

    private static native long createNativeObj();

    private static native void destroyNativeObj(long j);

    private static native void error(long j, InfoliteCallback infoliteCallback, long j2, int i);

    public InfoliteCallback(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    public static long getCPtr(InfoliteCallback infoliteCallback) {
        if (infoliteCallback == null) {
            return 0;
        }
        return infoliteCallback.swigCPtr;
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

    /* access modifiers changed from: protected */
    public void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        InfoliteCallback_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        InfoliteCallback_change_ownership(this, this.swigCPtr, true);
    }

    public void callBack(long j, InfoliteResult infoliteResult) {
        callBack(this.swigCPtr, this, j, 0, infoliteResult);
    }

    public void error(long j, int i) {
        error(this.swigCPtr, this, j, i);
    }

    public InfoliteCallback() {
        this(createNativeObj(), true);
        search_serviceJNI.swig_jni_init();
        InfoliteCallback_director_connect(this, this.swigCPtr, this.swigCMemOwn, false);
    }
}
