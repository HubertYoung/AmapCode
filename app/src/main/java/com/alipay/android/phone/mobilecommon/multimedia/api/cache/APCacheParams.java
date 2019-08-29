package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

import android.text.TextUtils;

public class APCacheParams {
    public static final int TYPE_CLEAN_ALBUM_VIDEO = 64;
    public static final int TYPE_CLEAN_ALBUM_VIDEO_THUMB = 128;
    public static final int TYPE_CLEAN_ALL = 65535;
    public static final int TYPE_CLEAN_AUDIO = 1;
    public static final int TYPE_CLEAN_BIG_IMG = 2;
    public static final int TYPE_CLEAN_FILE = 256;
    public static final int TYPE_CLEAN_ORI_IMG = 4;
    public static final int TYPE_CLEAN_SHORT_VIDEO = 16;
    public static final int TYPE_CLEAN_SHORT_VIDEO_THUMB = 32;
    public static final int TYPE_CLEAN_THUMB_IMG = 8;
    public boolean bUseAccessTime = false;
    public String businessId = null;
    public String businessIdPrefix = null;
    public int cleanTypes = 65535;
    public long oldInterval = -1;
    public boolean skipLock = true;

    public void validParams() {
        if (!TextUtils.isEmpty(this.businessId) && !TextUtils.isEmpty(this.businessIdPrefix)) {
            throw new RuntimeException("both businessId and businessIdPrefix has been set, which one do you want?");
        }
    }

    public String toString() {
        return "APCacheParams{businessId='" + this.businessId + '\'' + ", businessIdPrefix='" + this.businessIdPrefix + '\'' + ", oldInterval=" + this.oldInterval + ", skipLock=" + this.skipLock + ", bUseAccessTime=" + this.bUseAccessTime + ", cleanTypes=" + Integer.toBinaryString(this.cleanTypes) + '}';
    }
}
