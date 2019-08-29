package com.alipay.mobile.android.verify.sdk.a;

import android.content.Context;
import com.alipay.mobile.android.verify.logger.LogAdapter;
import com.alipay.mobile.common.logging.api.LoggerFactory;

/* compiled from: ZolozLogAdapter */
public class b implements LogAdapter {
    public boolean isLoggable(int i, String str) {
        return i >= 4;
    }

    public b(Context context) {
        LoggerFactory.init(context);
    }

    public void log(int i, String str, String str2) {
        if (i == 2) {
            LoggerFactory.getTraceLogger().verbose("ZMSDK", str2);
        } else if (i == 3) {
            LoggerFactory.getTraceLogger().debug("ZMSDK", str2);
        } else if (i == 4) {
            a.c(str2);
        } else {
            if (i == 5 || i == 6 || i == 7) {
                a.d(str2);
            }
        }
    }
}
