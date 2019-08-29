package com.autonavi.minimap.ajx3.core;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class JsFunctionCallbackImpl implements JsFunctionCallback {
    private int mIndex;
    private boolean mIsForMock;
    private long mShadow;

    private native Object nativeFunctionCallback(long j, int i, Object... objArr);

    private native void nativeRelease(long j);

    public JsFunctionCallbackImpl(long j, int i, long j2, boolean z) {
        this.mIsForMock = z;
        this.mShadow = j;
        this.mIndex = i;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mShadow != 0) {
            nativeRelease(this.mShadow);
            this.mShadow = 0;
        }
        super.finalize();
    }

    public boolean isForMock() {
        return this.mIsForMock;
    }

    public Object callback(Object... objArr) {
        return nativeFunctionCallback(this.mShadow, this.mIndex, objArr);
    }
}
