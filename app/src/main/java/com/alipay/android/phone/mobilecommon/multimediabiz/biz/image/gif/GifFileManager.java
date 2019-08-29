package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;

public class GifFileManager {
    private static GifFileManager a;

    private GifFileManager() {
    }

    public static GifFileManager getInstance() {
        if (a == null) {
            synchronized (GifFileManager.class) {
                try {
                    if (a == null) {
                        a = new GifFileManager();
                    }
                }
            }
        }
        return a;
    }

    public String generateGifPath(String key) {
        return CacheContext.get().getDiskCache().genPathByKey(key);
    }

    public void insertRecord(BitmapCacheKey cachekey, String path, int tag, String business, long expiredTime) {
        ImageDiskCache.get().savePath(cachekey, path, tag, business, expiredTime);
    }
}
