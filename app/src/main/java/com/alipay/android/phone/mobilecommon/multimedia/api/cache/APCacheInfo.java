package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

import android.os.Bundle;

public class APCacheInfo {
    public static final String EXTRA_HEIGHT = "height";
    public static final String EXTRA_ROTATION = "rotation";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_WIDTH = "width";
    public Bundle extra;
    public long length;
    public String localId;
    public String path;

    public String toString() {
        return "APCacheInfo{localId='" + this.localId + '\'' + ", path='" + this.path + '\'' + ", length=" + this.length + ", extra=" + this.extra + '}';
    }
}
