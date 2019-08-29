package com.alipay.android.phone.inside.log.util;

import android.content.Context;

public class LoggingUtil {
    private static boolean a = false;
    private static boolean b = false;

    public static boolean a(Context context) {
        boolean z = false;
        if (context == null) {
            return false;
        }
        if (!a) {
            a = true;
            try {
                if ((context.getApplicationContext().getApplicationInfo().flags & 2) == 2) {
                    z = true;
                }
                b = z;
            } catch (Throwable unused) {
            }
        }
        return b;
    }

    public static String a(Throwable th) {
        String str = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(b(th));
            String sb2 = sb.toString();
            try {
                if (th == th.getCause()) {
                    return sb2;
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(" 》》 ");
                str = sb3.toString();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(b(th.getCause()));
                return sb4.toString();
            } catch (Throwable unused) {
                return sb2;
            }
        } catch (Throwable unused2) {
            return str;
        }
    }

    private static String b(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(th.getClass().getName());
        stringBuffer.append(":");
        stringBuffer.append(th.getMessage());
        stringBuffer.append("》");
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                StringBuilder sb = new StringBuilder();
                sb.append(stackTraceElement.toString());
                sb.append("》");
                stringBuffer.append(sb.toString());
            }
        }
        return stringBuffer.toString();
    }
}
