package com.autonavi.jni.drive.offline;

public class HttpBaseRequest implements HttpRequestObserver {
    private HttpRequestInterface mHttpRequest;
    private long mPtr;
    private long mPtrTag;

    private native synchronized void nativeOnRequestFailed(HttpBaseRequest httpBaseRequest, int i);

    private native synchronized void nativeOnRequestFinished(HttpBaseRequest httpBaseRequest);

    private native synchronized boolean nativeOnRequestReceiveData(HttpBaseRequest httpBaseRequest, byte[] bArr, int i);

    private native synchronized void nativeOnRequestReciveHeader(HttpBaseRequest httpBaseRequest);

    public void setHttpRequest(HttpRequestInterface httpRequestInterface) {
        this.mHttpRequest = httpRequestInterface;
    }

    public void request(String str, int i) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.request(str, i);
        }
    }

    public void cancel() {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.cancel();
        }
    }

    public HttpBaseRequest addHeader(String str, String str2) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.addHeader(str, str2);
        }
        return this;
    }

    public void setTimeOut(int i) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.setTimeOut(i);
        }
    }

    public void setUserData(long j) {
        this.mPtrTag = j;
    }

    public long getUserData() {
        return this.mPtrTag;
    }

    public void setSignKes(String[] strArr) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.setSignKes(strArr);
        }
    }

    public void setParma(String str, String str2) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.setParma(str, str2);
        }
    }

    public void setNeedEncrpyt(boolean z) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.setNeedEncrpyt(z);
        }
    }

    public void setForDownloadFile(boolean z) {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.setForDownloadFile(z);
        }
    }

    public String getResponseHeader(String str) {
        return this.mHttpRequest != null ? this.mHttpRequest.getResponseHeader(str) : "";
    }

    public int getResponseCode() {
        if (this.mHttpRequest != null) {
            return this.mHttpRequest.getResponseCode();
        }
        return 0;
    }

    public void onRequestFinished() {
        nativeOnRequestFinished(this);
    }

    public void onRequestFailed(int i) {
        nativeOnRequestFailed(this, i);
    }

    public void onRequestReceiveData(byte[] bArr, int i) {
        nativeOnRequestReceiveData(this, bArr, i);
    }
}
