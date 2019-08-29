package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class IHttpRequest {
    protected long mShadow;

    /* access modifiers changed from: protected */
    public abstract IHttpRequest addHeader(String str, String str2);

    public abstract void cancel();

    /* access modifiers changed from: protected */
    public abstract void get(String str);

    /* access modifiers changed from: protected */
    public abstract int getResponseCode();

    /* access modifiers changed from: protected */
    public abstract String getResponseHeader(String str);

    /* access modifiers changed from: protected */
    public abstract long getUserData();

    /* access modifiers changed from: protected */
    public abstract void head(String str);

    /* access modifiers changed from: protected */
    public native void nativeOnRequestFailed(int i, long j);

    /* access modifiers changed from: protected */
    public native void nativeOnRequestFinished(long j);

    /* access modifiers changed from: protected */
    public native boolean nativeOnRequestReceiveData(byte[] bArr, int i, long j);

    /* access modifiers changed from: protected */
    public abstract void post(String str, String str2, int i);

    /* access modifiers changed from: protected */
    public abstract void setTimeOut(int i);

    /* access modifiers changed from: protected */
    public abstract void setUserData(long j);

    /* access modifiers changed from: protected */
    public void onRequestFinished() {
        nativeOnRequestFinished(this.mShadow);
    }

    /* access modifiers changed from: protected */
    public void onRequestFailed(int i) {
        nativeOnRequestFailed(i, this.mShadow);
    }

    /* access modifiers changed from: protected */
    public boolean onRequestReceiveData(byte[] bArr, int i) {
        return nativeOnRequestReceiveData(bArr, i, this.mShadow);
    }
}
