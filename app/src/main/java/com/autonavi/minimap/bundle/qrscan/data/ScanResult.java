package com.autonavi.minimap.bundle.qrscan.data;

public class ScanResult implements IScanResult {
    private String mText;

    public int getErrorCode() {
        return 1000;
    }

    public int getErrorType() {
        return 100;
    }

    public void ScanResult(String str) {
        this.mText = str;
    }

    public String getText() {
        return this.mText;
    }
}
