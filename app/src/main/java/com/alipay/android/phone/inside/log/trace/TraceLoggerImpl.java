package com.alipay.android.phone.inside.log.trace;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.util.LoggingUtil;

public class TraceLoggerImpl implements TraceLogger {
    private StringBuffer a;

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "inside_log";
        }
        return str.startsWith("inside_") ? str : "inside_".concat(String.valueOf(str));
    }

    private static boolean a() {
        return LoggingUtil.a(ContextManager.a().getContext());
    }

    public final void a(String str, String str2) {
        if (a()) {
            d(str2, (Throwable) null);
            a(str);
        }
    }

    public final void b(String str, String str2) {
        if (a()) {
            d(str2, (Throwable) null);
            a(str);
        }
    }

    public final void c(String str, String str2) {
        if (a()) {
            d(str2, (Throwable) null);
            a(str);
        }
    }

    public final void a(String str, Throwable th) {
        if (a()) {
            d((String) null, th);
            a(str);
        }
    }

    public final void a(String str, String str2, Throwable th) {
        if (a()) {
            d(str2, th);
            a(str);
        }
    }

    public final void d(String str, String str2) {
        if (a()) {
            d(str2, (Throwable) null);
            a(str);
        }
    }

    public final void b(String str, Throwable th) {
        if (a()) {
            d((String) null, th);
            a(str);
        }
    }

    public final void b(String str, String str2, Throwable th) {
        if (a()) {
            d(str2, th);
            a(str);
        }
    }

    public final void e(String str, String str2) {
        if (a()) {
            a(str);
        }
    }

    public final void c(String str, Throwable th) {
        if (a()) {
            a(str);
        }
    }

    private String d(String str, Throwable th) {
        if (this.a == null) {
            this.a = new StringBuffer();
        }
        String name = Thread.currentThread().getName();
        try {
            StringBuffer stringBuffer = this.a;
            stringBuffer.append('[');
            stringBuffer.append(name);
            stringBuffer.append("] ");
            if (!TextUtils.isEmpty(str)) {
                this.a.append(str);
            }
            if (th != null) {
                String a2 = LoggingUtil.a(th);
                StringBuffer stringBuffer2 = this.a;
                stringBuffer2.append(" THROWABLE: ");
                stringBuffer2.append(a2);
            }
        } catch (Throwable unused) {
        }
        String stringBuffer3 = this.a.toString();
        this.a.setLength(0);
        return stringBuffer3;
    }
}
