package com.alipay.android.phone.mobilecommon.logger;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.security.bio.utils.Logger;

/* compiled from: AlipayLogger */
public final class a extends Logger {
    public final int verbose(String str, String str2) {
        LoggerFactory.getTraceLogger().verbose(str, str2);
        return 0;
    }

    public final int debug(String str, String str2) {
        LoggerFactory.getTraceLogger().debug(str, str2);
        return 0;
    }

    public final int info(String str, String str2) {
        LoggerFactory.getTraceLogger().info(str, str2);
        return 0;
    }

    public final int warn(String str, String str2) {
        LoggerFactory.getTraceLogger().warn(str, str2);
        return 0;
    }

    public final int error(String str, String str2) {
        LoggerFactory.getTraceLogger().error(str, str2);
        return 0;
    }

    /* access modifiers changed from: protected */
    public final String getStackTraceString(Throwable th) {
        return LoggingUtil.throwableToString(th);
    }
}
