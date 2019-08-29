package com.alipay.mobile.common.cache.mem.lru;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.cache.mem.Entity;
import com.alipay.mobile.common.cache.mem.MemCache;
import com.autonavi.ae.bl.map.IMapPageConstant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruMemCache extends MemCache<Object> {
    private static LruMemCache a;

    public static synchronized LruMemCache getInstance() {
        LruMemCache lruMemCache;
        synchronized (LruMemCache.class) {
            if (a == null) {
                a = new LruMemCache();
            }
            lruMemCache = a;
        }
        return lruMemCache;
    }

    private LruMemCache() {
        this.mMap = new LinkedHashMap<String, Entity<Object>>(10, 0.75f, true) {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            /* access modifiers changed from: protected */
            public boolean removeEldestEntry(Entry<String, Entity<Object>> eldest) {
                if (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() < IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE) {
                    return true;
                }
                return false;
            }
        };
        this.mGroup = new HashMap();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public synchronized void put(String owner, String group, String key, Object value) {
        super.put(owner, group, key, value);
    }

    public synchronized Object get(String owner, String key) {
        return super.get(owner, key);
    }

    public synchronized Object remove(String key) {
        return super.remove(key);
    }

    /* access modifiers changed from: protected */
    public Entity<Object> makeEntity(String owner, String group, Object value) {
        return new Entity<>(owner, group, value);
    }

    /* access modifiers changed from: protected */
    public void recordRemove(Entity<Object> entity) {
    }

    /* access modifiers changed from: protected */
    public void recordPut(Entity<Object> entity) {
    }
}
