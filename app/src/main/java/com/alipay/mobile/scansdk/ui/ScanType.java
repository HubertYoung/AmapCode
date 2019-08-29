package com.alipay.mobile.scansdk.ui;

import android.text.TextUtils;

public enum ScanType {
    SCAN_MA("MA");
    
    private String value;

    private ScanType(String value2) {
        this.value = value2;
    }

    public static ScanType getType(String value2) {
        ScanType[] values;
        for (ScanType scanType : values()) {
            if (TextUtils.equals(scanType.value, value2)) {
                return scanType;
            }
        }
        return SCAN_MA;
    }

    public String toBqcScanType() {
        switch (this) {
            case SCAN_MA:
                return "MA";
            default:
                return "MA";
        }
    }
}
