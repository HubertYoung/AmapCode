package com.alipay.mobile.common.cache.disk.lru;

import android.os.StatFs;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.info.DeviceInfo;
import com.autonavi.ae.bl.map.IMapPageConstant;

public class DefaultLruDiskCache extends LruDiskCache {
    private static DefaultLruDiskCache a;

    private DefaultLruDiskCache() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static synchronized DefaultLruDiskCache getInstance() {
        DefaultLruDiskCache defaultLruDiskCache;
        synchronized (DefaultLruDiskCache.class) {
            if (a == null) {
                a = new DefaultLruDiskCache();
            }
            defaultLruDiskCache = a;
        }
        return defaultLruDiskCache;
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        a();
    }

    private void a() {
        String path = DeviceInfo.getInstance().getExternalCacheDir();
        if (path == null) {
            path = AppInfo.getInstance().getCacheDirPath();
        }
        try {
            a(path);
        } catch (Throwable th) {
            a(AppInfo.getInstance().getCacheDirPath());
        }
    }

    private void a(String path) {
        StatFs statFs = new StatFs(path);
        setDirectory(path);
        long canUseSize = (((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) - IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE;
        if (canUseSize <= 0) {
            canUseSize = 524288;
        }
        setMaxsize(canUseSize);
    }
}
