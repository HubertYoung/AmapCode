package com.alipay.mobile.framework.service.common.impl;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.mem.lru.ImageCache;
import com.alipay.mobile.framework.service.common.ImageMemCacheService;

public class ImageMemCacheServiceImpl extends ImageMemCacheService {
    private ImageCache a = ImageCache.getInstance();

    public ImageMemCacheServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public long getSize() {
        return this.a.getSize();
    }

    public long getMaxsize() {
        return this.a.getMaxsize();
    }

    public void removeByGroup(String group) {
        this.a.removeByGroup(group);
    }

    public Bitmap remove(String key) {
        return this.a.remove(key);
    }

    public Bitmap get(String owner, String key) {
        return this.a.get(owner, key);
    }

    public void put(String owner, String group, String key, Bitmap value) {
        this.a.put(owner, group, key, value);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
    }
}
