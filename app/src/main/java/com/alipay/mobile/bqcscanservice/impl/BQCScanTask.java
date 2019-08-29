package com.alipay.mobile.bqcscanservice.impl;

import android.hardware.Camera;
import android.hardware.Camera.Size;

public abstract class BQCScanTask<T> implements Runnable {
    protected Camera mCamera;
    protected byte[] mData;
    protected int mPreviewFormat;
    protected Size mPreviewSize;

    public void setData(byte[] mData2, Camera mCamera2, Size previewSize, int previewFormat) {
        this.mData = mData2;
        this.mCamera = mCamera2;
        this.mPreviewSize = previewSize;
        this.mPreviewFormat = previewFormat;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(T result) {
        this.mData = null;
        this.mCamera = null;
    }
}
