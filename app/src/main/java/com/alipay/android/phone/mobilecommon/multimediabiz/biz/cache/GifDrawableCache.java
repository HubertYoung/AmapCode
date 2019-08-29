package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable.APMGifDrawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemCacheInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl.ExpiredLruMemCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.GifConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Map;
import java.util.Map.Entry;

public class GifDrawableCache {
    private static GifDrawableCache b;
    private ExpiredLruMemCache<MemCacheInfo> a;

    private GifDrawableCache() {
        GifConf conf = SimpleConfigProvider.get().getGifConfig();
        this.a = new ExpiredLruMemCache<>(conf.cacheNum, ((long) conf.cacheTime) * 60000);
    }

    public static GifDrawableCache getInstance() {
        if (b == null) {
            synchronized (GifDrawableCache.class) {
                if (b == null) {
                    b = new GifDrawableCache();
                }
            }
        }
        return b;
    }

    public void put(String key, APMGifDrawable drawable) {
        Logger.P("GifDrawableCache", "put key=" + key + ";drawable=" + drawable, new Object[0]);
        if (!TextUtils.isEmpty(key)) {
            this.a.put(key, new MemCacheInfo(drawable));
        }
    }

    public APMGifDrawable get(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        MemCacheInfo cache = (MemCacheInfo) this.a.get(key);
        if (cache != null) {
            cache.lastAccessTime = System.currentTimeMillis();
        }
        if (cache == null) {
            return null;
        }
        return (APMGifDrawable) cache.mCache;
    }

    public void remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            this.a.remove(key);
        }
    }

    public void knockOutExpired(long aliveTime) {
        Logger.D("GifDrawableCache", "knockOutExpired aliveTime: " + aliveTime, new Object[0]);
        Map snapshot = this.a.snapshot();
        if (snapshot != null) {
            for (Entry entry : snapshot.entrySet()) {
                MemCacheInfo cache = (MemCacheInfo) entry.getValue();
                if (cache == null || System.currentTimeMillis() - cache.lastAccessTime > aliveTime) {
                    Logger.D("GifDrawableCache", "knockOutExpired key: " + ((String) entry.getKey()) + ", cache: " + cache, new Object[0]);
                    this.a.remove((String) entry.getKey());
                }
            }
        }
    }

    public ExpiredLruMemCache getRealCache() {
        return this.a;
    }
}
