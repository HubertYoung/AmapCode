package com.alipay.mobile.nebula.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5TypefaceCache {
    private static H5TypefaceCache mInstance = null;
    private final Map<String, WeakReference<Typeface>> mTypefaceMap = new ConcurrentHashMap();

    public static synchronized H5TypefaceCache getInstance() {
        H5TypefaceCache h5TypefaceCache;
        synchronized (H5TypefaceCache.class) {
            if (mInstance == null) {
                mInstance = new H5TypefaceCache();
            }
            h5TypefaceCache = mInstance;
        }
        return h5TypefaceCache;
    }

    private H5TypefaceCache() {
    }

    public void putTypeface(String bundleName, String cat, Typeface typeface) {
        if (!TextUtils.isEmpty(bundleName) && !TextUtils.isEmpty(cat) && typeface != null) {
            this.mTypefaceMap.put(bundleName + cat, new WeakReference(typeface));
        }
    }

    public Typeface getTypefaceByCat(String bundleName, String cat) {
        if (TextUtils.isEmpty(bundleName) || TextUtils.isEmpty(cat)) {
            return null;
        }
        WeakReference typefaceRef = this.mTypefaceMap.get(bundleName + cat);
        if (typefaceRef != null && typefaceRef.get() != null) {
            return (Typeface) typefaceRef.get();
        }
        this.mTypefaceMap.remove(bundleName + cat);
        return null;
    }

    public void clear() {
        this.mTypefaceMap.clear();
    }

    public static Typeface getTypeface(Context context, String bundleName, String ttfFilePath) {
        if (context == null || TextUtils.isEmpty(ttfFilePath)) {
            return null;
        }
        Typeface typeface = getInstance().getTypefaceByCat(bundleName, ttfFilePath);
        if (typeface != null) {
            return typeface;
        }
        try {
            typeface = Typeface.createFromAsset(context.getAssets(), ttfFilePath);
        } catch (Exception e) {
        }
        if (typeface == null) {
            return typeface;
        }
        getInstance().putTypeface(bundleName, ttfFilePath, typeface);
        return typeface;
    }
}
