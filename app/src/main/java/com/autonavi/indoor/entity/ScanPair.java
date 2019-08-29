package com.autonavi.indoor.entity;

import android.net.wifi.ScanResult;

public class ScanPair {
    public String mID = "";
    public int mRSSI;
    public String mSsid = "";

    public ScanPair(ScanResult scanResult) {
        this.mID = scanResult.BSSID;
        this.mID = this.mID.replace(':', '_');
        this.mID = this.mID.toUpperCase();
        this.mRSSI = scanResult.level;
        this.mSsid = scanResult.SSID;
    }

    public ScanPair(String str, int i) {
        this.mID = str;
        this.mRSSI = i;
    }
}
