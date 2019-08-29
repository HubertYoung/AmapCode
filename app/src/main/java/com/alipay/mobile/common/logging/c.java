package com.alipay.mobile.common.logging;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingSPCache;

/* compiled from: LogContextImpl */
final class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ LogContextImpl b;

    c(LogContextImpl this$0, String str) {
        this.b = this$0;
        this.a = str;
    }

    public final void run() {
        String cacheTag = LoggingSPCache.getInstance().getString(LoggingSPCache.LOGGING_CACHE_KEY_LOG_DUMP_TAG, null);
        if (cacheTag == null || !cacheTag.equals(this.a)) {
            LoggingSPCache.getInstance().putStringCommit(LoggingSPCache.LOGGING_CACHE_KEY_LOG_DUMP_TAG, this.a);
            try {
                this.b.a((String) "applog");
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) "LogContext", t);
            }
            try {
                this.b.a((String) "trafficLog");
            } catch (Throwable t2) {
                LoggerFactory.getTraceLogger().error((String) "LogContext", t2);
            }
        }
    }
}
