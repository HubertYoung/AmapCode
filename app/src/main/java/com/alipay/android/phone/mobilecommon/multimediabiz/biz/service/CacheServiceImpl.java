package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaCacheService;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheDeleteCallback;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheSource;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheStorageManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.service.CacheServiceMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import java.util.Map;
import java.util.Set;

public class CacheServiceImpl extends MultimediaCacheService {
    public long deleteCache(APCacheParams params, APCacheDeleteCallback callback) {
        return CacheServiceMgr.get().deleteCache(params, callback);
    }

    public long deleteCache(Set<String> whiteList, int limit, String bizType) {
        return CacheServiceMgr.get().deleteCache(whiteList, limit, bizType);
    }

    public boolean checkMultimediaCacheDir(String dirPath, String bizType) {
        return CacheServiceMgr.get().checkMultimediaCacheDir(dirPath, bizType);
    }

    public Map<String, APCacheResult> queryCacheInfos(APCacheParams params, APCacheQueryCallback callback) {
        return CacheServiceMgr.get().queryCacheInfos(params, callback);
    }

    public long deleteExpiredCache() {
        return CacheServiceMgr.get().deleteExpiredCache();
    }

    public APCacheInfo saveIntoCache(APCacheSource source) {
        return CacheStorageManager.saveIntoCache(source);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        FRWBroadcastReceiver.initOnce();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
