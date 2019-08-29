package com.alipay.android.nebulaapp;

import android.util.Log;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.amap.bundle.logs.AMapLog;

public class TestLogger implements TraceLogger {
    private final String TAG = "amap_miniapp";
    private String logEvent = "";
    private String logPage = "";

    public void verbose(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        AMapLog.i("amap_miniapp", sb.toString());
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void debug(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        AMapLog.d("amap_miniapp", sb.toString());
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void info(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        AMapLog.i("amap_miniapp", sb.toString());
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void warn(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        AMapLog.w("amap_miniapp", sb.toString());
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void warn(String str, Throwable th) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(Log.getStackTraceString(th));
            AMapLog.w("amap_miniapp", sb.toString());
        }
        String str2 = this.logPage;
        String str3 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(th);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str2, str3, sb2.toString());
    }

    public void warn(String str, String str2, Throwable th) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(str2);
            sb.append(": ");
            sb.append(Log.getStackTraceString(th));
            AMapLog.w("amap_miniapp", sb.toString());
        }
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        sb2.append(th);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void error(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        AMapLog.e("amap_miniapp", sb.toString());
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void error(String str, Throwable th) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(Log.getStackTraceString(th));
            AMapLog.e("amap_miniapp", sb.toString());
        }
        String str2 = this.logPage;
        String str3 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(th);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str2, str3, sb2.toString());
    }

    public void error(String str, String str2, Throwable th) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(str2);
            sb.append(": ");
            sb.append(Log.getStackTraceString(th));
            AMapLog.e("amap_miniapp", sb.toString());
        }
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(th);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void print(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        AMapLog.w("amap_miniapp", sb.toString());
        String str3 = this.logPage;
        String str4 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(str2);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str3, str4, sb2.toString());
    }

    public void print(String str, Throwable th) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(": ");
            sb.append(Log.getStackTraceString(th));
            AMapLog.w("amap_miniapp", sb.toString());
        }
        String str2 = this.logPage;
        String str3 = this.logEvent;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(":");
        sb2.append(th);
        AMapLog.logNormalNative(AMapLog.GROUP_MINIAPP, str2, str3, sb2.toString());
    }
}
