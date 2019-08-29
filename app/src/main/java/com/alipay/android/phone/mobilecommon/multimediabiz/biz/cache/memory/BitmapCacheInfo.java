package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory;

import android.graphics.Bitmap;

public class BitmapCacheInfo {
    public long lastAccessTime = System.currentTimeMillis();
    public final Bitmap mBitmap;

    public BitmapCacheInfo(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
