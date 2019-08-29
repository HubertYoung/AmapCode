package com.alipay.mobile.framework.service.common.impl;

import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.mem.lru.LruMemCache;
import com.alipay.mobile.framework.service.common.GenericMemCacheService;

public class GenericMemCacheServiceImpl extends GenericMemCacheService {
    private LruMemCache a = LruMemCache.getInstance();

    public GenericMemCacheServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void removeByGroup(String group) {
        this.a.removeByGroup(group);
    }

    public void removeByOwner(String owner) {
        this.a.removeByOwner(owner);
    }

    public Object remove(String key) {
        return this.a.remove(key);
    }

    public Object get(String owner, String key) {
        return this.a.get(owner, key);
    }

    public void put(String owner, String group, String key, Object value) {
        this.a.put(owner, group, key, value);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
    }
}
