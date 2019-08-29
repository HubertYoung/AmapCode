package com.alipay.android.phone.mobilecommon.multimedia.api;

import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheDeleteCallback;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheSource;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.Map;
import java.util.Set;

public abstract class MultimediaCacheService extends ExternalService {
    public abstract boolean checkMultimediaCacheDir(String str, String str2);

    public abstract long deleteCache(APCacheParams aPCacheParams, APCacheDeleteCallback aPCacheDeleteCallback);

    public abstract long deleteCache(Set<String> set, int i, String str);

    public abstract long deleteExpiredCache();

    public abstract Map<String, APCacheResult> queryCacheInfos(APCacheParams aPCacheParams, APCacheQueryCallback aPCacheQueryCallback);

    public abstract APCacheInfo saveIntoCache(APCacheSource aPCacheSource);
}
