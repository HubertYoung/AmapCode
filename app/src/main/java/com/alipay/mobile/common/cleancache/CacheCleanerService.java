package com.alipay.mobile.common.cleancache;

import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class CacheCleanerService extends ExternalService {

    public interface CleanExecutor {
        long executeClean();
    }

    public interface CleanListener {
        void onClean(float f, long j);
    }

    public abstract void clean(CleanListener cleanListener);

    public abstract long syncClean(CleanListener cleanListener);
}
