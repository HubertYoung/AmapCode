package com.ali.auth.third.core.trace;

import android.content.Context;
import android.util.Log;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.message.Message;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SDKLogger {
    private static String a() {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("time =");
        sb.append(b());
        return sb.toString();
    }

    private static String b() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date(System.currentTimeMillis()));
    }

    public static void d(String str, String str2) {
        try {
            if (isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(a());
            }
        } catch (Throwable unused) {
        }
    }

    public static void d(String str, String str2, Throwable th) {
        try {
            if (isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(a());
            }
        } catch (Throwable unused) {
        }
    }

    public static void e(String str, String str2) {
        try {
            if (isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(a());
            }
        } catch (Throwable unused) {
        }
    }

    public static void e(String str, String str2, Throwable th) {
        try {
            if (isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(a());
            }
        } catch (Throwable unused) {
        }
    }

    public static void i(String str, String str2) {
        try {
            if (isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(a());
            }
        } catch (Throwable unused) {
        }
    }

    public static boolean isApkDebugable(Context context) {
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static boolean isDebugEnabled() {
        return ConfigManager.DEBUG || isApkDebugable(KernelContext.getApplicationContext());
    }

    public static void log(String str, Message message) {
        log(str, message, null);
    }

    public static void log(String str, Message message, Throwable th) {
        if (isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("***********************************************************\n");
            sb.append("错误编码 = ");
            sb.append(message.code);
            sb.append("\n");
            sb.append("错误消息 = ");
            sb.append(message.message);
            sb.append("\n");
            sb.append("解决建议 = ");
            sb.append(message.action);
            sb.append("\n");
            if (th != null) {
                sb.append("错误堆栈 = ");
                sb.append(Log.getStackTraceString(th));
                sb.append("\n");
            }
            sb.append("***********************************************************\n");
            String str2 = message.type;
            if ("D".equals(str2)) {
                d(str, sb.toString());
            } else if ("E".equals(str2)) {
                e(str, sb.toString());
            } else if ("W".equals(str2)) {
                w(str, sb.toString());
            } else {
                i(str, sb.toString());
            }
        }
    }

    public static void w(String str, String str2) {
        try {
            if (isDebugEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(a());
            }
        } catch (Throwable unused) {
        }
    }
}
