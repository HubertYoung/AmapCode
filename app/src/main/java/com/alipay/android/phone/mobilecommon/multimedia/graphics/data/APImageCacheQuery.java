package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.ImageWorkerPlugin;
import com.alipay.sdk.util.h;

public class APImageCacheQuery extends APImageQuery {
    public int height;
    public ImageWorkerPlugin plugin;
    public int width;

    public APImageCacheQuery(String path) {
        this(path, -1, -1);
        this.cutScaleType = CutScaleType.SCALE_AUTO_LIMIT;
    }

    public APImageCacheQuery(String path, int width2, int height2, ImageWorkerPlugin plugin2) {
        super(path);
        this.width = width2;
        this.height = height2;
        this.plugin = plugin2;
    }

    public APImageCacheQuery(String path, int width2, int height2) {
        this(path, width2, height2, null);
    }

    public String toString() {
        return "APImageCacheQuery{width=" + this.width + ", height=" + this.height + ", plugin=" + this.plugin + ", path=" + this.path + h.d;
    }
}
