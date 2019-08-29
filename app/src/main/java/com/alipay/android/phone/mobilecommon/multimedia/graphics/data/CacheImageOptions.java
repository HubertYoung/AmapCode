package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class CacheImageOptions extends BaseReq {
    private boolean cacheInDisk;
    private boolean cacheInMem;

    public CacheImageOptions() {
        this.cacheInDisk = true;
        this.cacheInMem = false;
        this.cutScaleType = CutScaleType.KEEP_RATIO;
        this.scale = Float.valueOf(0.5f);
    }

    public boolean isCacheInDisk() {
        return this.cacheInDisk;
    }

    public void setCacheInDisk(boolean cacheInDisk2) {
        this.cacheInDisk = cacheInDisk2;
    }

    public boolean isCacheInMem() {
        return this.cacheInMem;
    }

    public void setCacheInMem(boolean cacheInMem2) {
        this.cacheInMem = cacheInMem2;
    }
}
