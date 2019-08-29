package com.amap.bundle.statistics.secondlog;

public class StorageInfo {
    public String mAvailableSize;
    public String mName;
    public String mTotalSize;

    public StorageInfo() {
    }

    public StorageInfo(String str, String str2, String str3) {
        this.mName = str;
        this.mTotalSize = str2;
        this.mAvailableSize = str3;
    }
}
