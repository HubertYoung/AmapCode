package com.autonavi.indoor.entity;

import java.util.List;

public class ScanData {
    public static final byte DEVICE_BEACON = 1;
    public static final byte DEVICE_WIFI = 0;
    public List<ScanPair> scans_;
    public long time_;
    public int type_;

    public ScanData(long j, int i, List<ScanPair> list) {
        this.type_ = i;
        this.scans_ = list;
        this.time_ = j;
    }

    public int size() {
        return this.scans_.size();
    }

    public void clear() {
        this.scans_.clear();
    }
}
