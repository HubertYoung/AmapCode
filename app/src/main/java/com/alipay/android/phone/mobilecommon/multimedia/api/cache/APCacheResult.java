package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

public class APCacheResult {
    public String businessId;
    public int fileCount;
    public long totalFileSize;

    public String toString() {
        return "APCacheResult{businessId='" + this.businessId + '\'' + ", totalFileSize=" + this.totalFileSize + ", fileCount=" + this.fileCount + '}';
    }
}
