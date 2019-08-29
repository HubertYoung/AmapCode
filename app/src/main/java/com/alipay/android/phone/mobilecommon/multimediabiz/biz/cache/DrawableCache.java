package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import android.graphics.drawable.Drawable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl.ExpiredLruMemCache;

public class DrawableCache {
    private static DrawableCache b = new DrawableCache();
    private ExpiredLruMemCache<Drawable> a = new ExpiredLruMemCache<>(15, 300000);

    private DrawableCache() {
    }

    public static DrawableCache get() {
        return b;
    }

    public ExpiredLruMemCache getRealCache() {
        return this.a;
    }

    public Drawable get(String key) {
        return (Drawable) this.a.get(key);
    }

    public Drawable remove(String key) {
        return (Drawable) this.a.remove(key);
    }

    public void put(String key, Drawable drawable) {
        this.a.put(key, drawable);
    }
}
