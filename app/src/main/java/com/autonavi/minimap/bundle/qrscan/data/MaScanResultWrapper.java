package com.autonavi.minimap.bundle.qrscan.data;

import com.alipay.mobile.mascanengine.MaScanResult;

public class MaScanResultWrapper implements IScanResult {
    private MaScanResult mMaScanResult;

    public int getErrorCode() {
        return 1000;
    }

    public int getErrorType() {
        return 100;
    }

    public MaScanResultWrapper(MaScanResult maScanResult) {
        this.mMaScanResult = maScanResult;
    }

    public String getText() {
        if (this.mMaScanResult == null) {
            return null;
        }
        return this.mMaScanResult.text;
    }

    public MaScanResult getMaScanResult() {
        return this.mMaScanResult;
    }
}
