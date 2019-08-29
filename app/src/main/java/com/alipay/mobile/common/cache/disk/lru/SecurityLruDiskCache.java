package com.alipay.mobile.common.cache.disk.lru;

import android.os.StatFs;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.info.AppInfo;
import com.autonavi.ae.bl.map.IMapPageConstant;

public class SecurityLruDiskCache extends LruDiskCache {
    private static SecurityLruDiskCache a;

    private SecurityLruDiskCache() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static synchronized SecurityLruDiskCache getInstance() {
        SecurityLruDiskCache securityLruDiskCache;
        synchronized (SecurityLruDiskCache.class) {
            if (a == null) {
                a = new SecurityLruDiskCache();
            }
            securityLruDiskCache = a;
        }
        return securityLruDiskCache;
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        a();
    }

    private void a() {
        String path = AppInfo.getInstance().getCacheDirPath();
        StatFs statFs = new StatFs(path);
        setDirectory(path);
        long canUseSize = (((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) - IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE;
        if (canUseSize <= 0) {
            canUseSize = 524288;
        }
        setMaxsize(canUseSize);
    }
}
