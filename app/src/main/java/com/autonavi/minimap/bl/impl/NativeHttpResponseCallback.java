package com.autonavi.minimap.bl.impl;

import com.autonavi.minimap.bl.net.HttpResponse;
import com.autonavi.minimap.bl.net.IHttpUploadCallback;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
class NativeHttpResponseCallback implements IHttpUploadCallback {
    long mShadow;

    private native void nativeOnCanceled(HttpResponse httpResponse, long j);

    private native void nativeOnFail(HttpResponse httpResponse, long j);

    private native void nativeOnProgress(int i, String str, long j, long j2, long j3);

    private native void nativeOnReceiveBody(HttpResponse httpResponse, long j);

    private native void nativeOnReceiveHeader(HttpResponse httpResponse, long j);

    private native void nativeOnSuccess(HttpResponse httpResponse, long j);

    NativeHttpResponseCallback() {
    }

    public void onReceiveHeader(HttpResponse httpResponse) {
        nativeOnReceiveHeader(httpResponse, this.mShadow);
    }

    public void onReceiveBody(HttpResponse httpResponse) {
        nativeOnReceiveBody(httpResponse, this.mShadow);
    }

    public void onFailed(HttpResponse httpResponse) {
        nativeOnFail(httpResponse, this.mShadow);
    }

    public void onSuccess(HttpResponse httpResponse) {
        nativeOnSuccess(httpResponse, this.mShadow);
    }

    public void onCanceled(HttpResponse httpResponse) {
        nativeOnCanceled(httpResponse, this.mShadow);
    }

    public void onProgress(int i, String str, long j, long j2) {
        nativeOnProgress(i, str, j, j2, this.mShadow);
    }
}
