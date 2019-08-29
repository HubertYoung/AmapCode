package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory;

import android.graphics.Bitmap;
import java.util.Collection;

public interface MemoryCache {
    void clear();

    void debugInfo();

    Bitmap get(String str);

    Bitmap get(String str, Bitmap bitmap);

    long getMemoryMaxSize();

    Collection<String> keys();

    void knockOutExpired(long j);

    boolean put(String str, Bitmap bitmap);

    Bitmap remove(String str);

    void trimToSize(int i);
}
