package com.autonavi.minimap.ackor.ackoramap;

public abstract class IAmapHttpRequest {
    protected static final int RequestTypeGet = 1;
    protected static final int RequestTypeHead = 0;
    protected static final int RequestTypePost = 2;
    public long mShadow;

    /* access modifiers changed from: protected */
    public abstract IAmapHttpRequest addHeader(String str, String str2);

    public abstract void cancel();

    /* access modifiers changed from: protected */
    public abstract int getResponseCode();

    /* access modifiers changed from: protected */
    public abstract String getResponseHeader(String str);

    /* access modifiers changed from: protected */
    public abstract long getUserData();

    public native void nativeOnRequestFailed(IAmapHttpRequest iAmapHttpRequest, long j, int i);

    public native void nativeOnRequestFinished(IAmapHttpRequest iAmapHttpRequest, long j);

    public native void nativeOnRequestReceiveData(IAmapHttpRequest iAmapHttpRequest, long j, byte[] bArr, int i);

    /* access modifiers changed from: protected */
    public native void nativeOnRequestReciveHeader(IAmapHttpRequest iAmapHttpRequest, long j);

    /* access modifiers changed from: protected */
    public abstract void request(String str, int i);

    /* access modifiers changed from: protected */
    public abstract void setForDownloadFile(boolean z);

    /* access modifiers changed from: protected */
    public abstract void setNeedEncrpyt(boolean z);

    /* access modifiers changed from: protected */
    public abstract void setParma(String str, String str2);

    /* access modifiers changed from: protected */
    public abstract void setSignKes(String[] strArr);

    /* access modifiers changed from: protected */
    public abstract void setTimeOut(int i);

    /* access modifiers changed from: protected */
    public abstract void setUserData(long j);
}
