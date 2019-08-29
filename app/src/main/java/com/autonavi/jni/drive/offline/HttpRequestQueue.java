package com.autonavi.jni.drive.offline;

public class HttpRequestQueue {
    private OnCreateHttpRequestListener mOnCreateHttpRequestListener;
    private long mPtr;

    public interface OnCreateHttpRequestListener {
        void onHttpRequestCreate(HttpBaseRequest httpBaseRequest);
    }

    public HttpBaseRequest createHttpRequest() {
        HttpBaseRequest httpBaseRequest = new HttpBaseRequest();
        new StringBuilder("HttpBaseRequest createHttpRequest is called()--mOnCreateHttpRequestListener=").append(this.mOnCreateHttpRequestListener);
        if (this.mOnCreateHttpRequestListener != null) {
            this.mOnCreateHttpRequestListener.onHttpRequestCreate(httpBaseRequest);
        }
        return httpBaseRequest;
    }

    public void setOnCreateHttpRequestListener(OnCreateHttpRequestListener onCreateHttpRequestListener) {
        this.mOnCreateHttpRequestListener = onCreateHttpRequestListener;
    }
}
