package com.ali.user.mobile.accountbiz.extservice.impl.mem;

import com.autonavi.ae.bl.map.IMapPageConstant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruMemCache extends MemCache<Object> {
    private static LruMemCache INSTANCE;

    /* access modifiers changed from: protected */
    public void recordPut(Entity<Object> entity) {
    }

    /* access modifiers changed from: protected */
    public void recordRemove(Entity<Object> entity) {
    }

    public static synchronized LruMemCache getInstance() {
        LruMemCache lruMemCache;
        synchronized (LruMemCache.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new LruMemCache();
                }
                lruMemCache = INSTANCE;
            }
        }
        return lruMemCache;
    }

    private LruMemCache() {
        this.mMap = new LinkedHashMap<String, Entity<Object>>(10, 0.75f, true) {
            private static final long serialVersionUID = -3776592521668005864L;

            /* access modifiers changed from: protected */
            public boolean removeEldestEntry(Entry<String, Entity<Object>> entry) {
                return Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() < IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE;
            }
        };
        this.mGroup = new HashMap();
    }

    public synchronized void put(String str, String str2, String str3, Object obj) {
        super.put(str, str2, str3, obj);
    }

    public synchronized Object get(String str, String str2) {
        try {
        }
        return super.get(str, str2);
    }

    public synchronized Object remove(String str) {
        try {
        }
        return super.remove(str);
    }

    /* access modifiers changed from: protected */
    public Entity<Object> makeEntity(String str, String str2, Object obj) {
        return new Entity<>(str, str2, obj);
    }
}
