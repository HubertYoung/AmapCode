package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APCacheBitmapReq extends BaseReq {
    public boolean loadFromDiskCache;
    public String path;

    public APCacheBitmapReq(String path2, int width, int height) {
        this.loadFromDiskCache = true;
        this.path = path2;
        this.width = Integer.valueOf(width);
        this.height = Integer.valueOf(height);
        this.cutScaleType = CutScaleType.KEEP_RATIO;
    }

    public APCacheBitmapReq(String path2) {
        this(path2, -1, -1);
        this.cutScaleType = CutScaleType.SCALE_AUTO_LIMIT;
    }

    public String toString() {
        return "[path: " + this.path + ", width: " + this.width + ", height: " + this.height + ", srcSize: " + this.srcSize + ", cutScaleType: " + this.cutScaleType + ", plugin: " + this.plugin + ", loadFromDiskCache: " + this.loadFromDiskCache + ", imageMarkRequest: " + this.imageMarkRequest + "]";
    }
}
