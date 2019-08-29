package com.alipay.android.phone.inside.log.biz;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LogContext;

public class ContextManager {
    private static LogContext a;

    public static void a(LogContext logContext) {
        if (logContext != null) {
            a = logContext;
        }
    }

    public static LogContext a() {
        if (a == null) {
            a = new LogContext() {
                public final Context getContext() {
                    return null;
                }

                public final String getInfo(String str) {
                    return null;
                }
            };
        }
        return a;
    }
}
