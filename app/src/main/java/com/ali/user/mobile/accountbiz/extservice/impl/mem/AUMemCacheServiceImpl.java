package com.ali.user.mobile.accountbiz.extservice.impl.mem;

import com.ali.user.mobile.accountbiz.extservice.AUMemCacheService;

public class AUMemCacheServiceImpl extends AUMemCacheService {
    private static AUMemCacheService mMemCacheService;
    private LruMemCache mLruMemCache = LruMemCache.getInstance();

    private AUMemCacheServiceImpl() {
    }

    public static AUMemCacheService getInstance() {
        if (mMemCacheService == null) {
            synchronized (AUMemCacheServiceImpl.class) {
                try {
                    if (mMemCacheService == null) {
                        mMemCacheService = new AUMemCacheServiceImpl();
                    }
                }
            }
        }
        return mMemCacheService;
    }

    public void removeByGroup(String str) {
        this.mLruMemCache.removeByGroup(str);
    }

    public Object remove(String str) {
        return this.mLruMemCache.remove(str);
    }

    public Object get(String str, String str2) {
        return this.mLruMemCache.get(str, str2);
    }

    public void put(String str, String str2, String str3, Object obj) {
        this.mLruMemCache.put(str, str2, str3, obj);
    }
}
