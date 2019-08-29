package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.fast.FastBitmapMemDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl.HeapLruMemCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.impl.SoftImageLruCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.Cache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.impl.LruDiskCache;
import com.alipay.diskcache.utils.StaticResources;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class CacheContext {
    public static final String COMMON_NONE_BUSINESS = ("x6q,.dwun#" + System.currentTimeMillis());
    private static CacheContext a;
    private DiskCache b;
    private MemoryCache c;
    private MemoryCache d;
    private MemoryCache e;
    private String f = "";
    private boolean g = ConfigManager.getInstance().getAshmemConfSwitch();
    private ConcurrentHashMap<String, MemoryCache> h = new ConcurrentHashMap<>();

    public static CacheContext get() {
        if (a == null) {
            synchronized (CacheContext.class) {
                try {
                    if (a == null) {
                        a = new CacheContext();
                    }
                }
            }
        }
        return a;
    }

    private CacheContext() {
        a();
        b();
    }

    private void a() {
        if (this.c == null) {
            boolean useTQ = ConfigManager.getInstance().getCommonConfigItem().cache.isUseTQCache();
            if (e()) {
                this.c = new HeapLruMemCache(d(), useTQ);
            } else if (this.g) {
                this.c = new HeapLruMemCache(c(), useTQ);
            } else {
                this.c = new FastBitmapMemDiskCache(c(), useTQ);
            }
        }
        if (this.d == null) {
            this.d = new HeapLruMemCache(d(), ConfigManager.getInstance().getCommonConfigItem().cache.isUseCommonTQCache());
        }
    }

    private void b() {
        StaticResources.sExecutors = TaskScheduleManager.get().commonExecutor();
        if (this.b == null) {
            String pkgName = AppUtils.getApplicationContext().getPackageName();
            Logger.I("CacheContext", "pkgname:" + pkgName, new Object[0]);
            String cacheRoot = CacheConfig.getCacheDirNew().getAbsolutePath();
            Logger.I("CacheContext", "getCacheDirNew:" + cacheRoot, new Object[0]);
            if (!TextUtils.isEmpty(pkgName) && !pkgName.equals("com.eg.android.AlipayGphone")) {
                cacheRoot = cacheRoot + File.separator + pkgName;
            }
            Logger.I("CacheContext", "cacheRoot:" + cacheRoot, new Object[0]);
            this.f = cacheRoot;
            this.b = new LruDiskCache(AppUtils.getApplicationContext(), cacheRoot, (long) ConfigManager.getInstance().getCommonConfigItem().diskConf.diskCacheSize, 10);
        }
    }

    public String getRootDir() {
        return this.f;
    }

    public MemoryCache getMemCache() {
        if (this.c == null) {
            synchronized (this) {
                if (this.c == null) {
                    a();
                }
            }
        }
        return this.c;
    }

    public MemoryCache getCommonMemCache() {
        return this.d;
    }

    public MemoryCache getMemCache(String business) {
        MemoryCache memoryCache = business == null ? null : this.h.get(business);
        return memoryCache != null ? memoryCache : getMemCache();
    }

    public DiskCache getDiskCache() {
        if (this.b == null) {
            synchronized (this) {
                try {
                    if (this.b == null) {
                        b();
                    }
                }
            }
        }
        return this.b;
    }

    public void clear() {
        if (this.c != null) {
            this.c.clear();
        }
    }

    public void destroy() {
        synchronized (this) {
            clear();
            if (this.c != null) {
                this.c = null;
            }
            if (this.b != null) {
                this.b = null;
            }
            a = null;
        }
    }

    private static int c() {
        int size = Math.max(50331648, Math.min((AppUtils.getHeapGrowthLimit() * 3) / 8, 100663296));
        Logger.P("CacheContext", "getNativeMemCacheSize size: " + size, new Object[0]);
        return size;
    }

    private static int d() {
        Cache cacheConf = ConfigManager.getInstance().getCommonConfigItem().cache;
        int growthLimit = AppUtils.getHeapGrowthLimit();
        int presetMax = (int) Math.min(((float) growthLimit) * cacheConf.maxRatioInCommCache, (float) cacheConf.maxCommCacheSize);
        int max = (int) Math.min(((float) growthLimit) * cacheConf.commRationInCommCache, (float) presetMax);
        int size = (int) Math.max(cacheConf.minCommCacheSize, (long) max);
        Logger.D("CacheContext", "getCommonMemCacheSize size: " + size + ", cacheConf: " + cacheConf + ", growthLimit: " + growthLimit + ", presetMax: " + presetMax + ", max: " + max, new Object[0]);
        return size;
    }

    public String queryAliasKey(String key) {
        String[] parts = key.split("##", 2);
        String aliasKey = ImageDiskCache.get().queryAliasKey(parts[0]);
        if (aliasKey != null) {
            return aliasKey + "##" + parts[1];
        }
        return null;
    }

    public void resetUseAshmem(boolean useAshmem) {
        Logger.D("CacheContext", "resetUseAshmem useAshmem: " + useAshmem + ", mUseAshmem: " + this.g, new Object[0]);
        if (useAshmem != this.g && this.c != null) {
            this.g = useAshmem;
            this.c.clear();
            this.c = null;
            a();
            Logger.D("CacheContext", "resetUseAshmem mImageMemCache: " + this.c, new Object[0]);
        }
    }

    public void registerCommonMemCache(String business) {
        Logger.D("CacheContext", "registerCommonMemCache business: " + business, new Object[0]);
        if (business != null) {
            this.h.put(business, this.d);
        }
    }

    public void debugInfo() {
        if (this.d != null) {
            this.d.debugInfo();
        }
        if (this.c != null) {
            this.c.debugInfo();
        }
        if (this.e != null) {
            this.e.debugInfo();
        }
    }

    public MemoryCache getSoftReferenceMemCache() {
        if (this.e == null) {
            synchronized (this) {
                if (this.e == null) {
                    this.e = new SoftImageLruCache(10);
                }
            }
        }
        return this.e;
    }

    private static boolean e() {
        return DeviceWrapper.isMatchDevice(ConfigManager.getInstance().getCommonConfigItem().cache.forceHeapMem);
    }
}
