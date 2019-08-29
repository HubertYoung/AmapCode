package com.alipay.inside.android.phone.mrpc.core.utils;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;

public final class MiscUtils {
    private static Boolean DEBUG = null;
    private static String TAG = "MiscUtils";

    public static final boolean isDebugger(Context context) {
        if (DEBUG != null) {
            return DEBUG.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0);
            DEBUG = valueOf;
            return valueOf.booleanValue();
        } catch (Exception e) {
            TraceLogger f = LoggerFactory.f();
            String str = TAG;
            StringBuilder sb = new StringBuilder("isDebugger ex:");
            sb.append(e.toString());
            f.d(str, sb.toString());
            return false;
        }
    }

    public static final Throwable getRootCause(Throwable th) {
        Throwable th2;
        Throwable th3 = null;
        try {
            Throwable cause = th.getCause();
            while (true) {
                Throwable th4 = cause;
                th2 = th3;
                th3 = th4;
                if (th3 == null) {
                    break;
                }
                cause = th3.getCause();
            }
            if (th2 != null) {
                return th2;
            }
            return th;
        } catch (Exception unused) {
            TraceLogger f = LoggerFactory.f();
            String str = TAG;
            StringBuilder sb = new StringBuilder("getRootCause exception : ");
            sb.append(th.toString());
            f.d(str, sb.toString());
        }
    }
}
