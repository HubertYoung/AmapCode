package com.alipay.mobile.common.logging.appender;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public abstract class Appender {
    protected LogContext a;
    protected String b;
    protected Context c;
    protected String d = LoggerFactory.getProcessInfo().getProcessTag();

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void a(LogEvent logEvent);

    /* access modifiers changed from: protected */
    public abstract void a(boolean z);

    /* access modifiers changed from: protected */
    public abstract boolean a(String str, boolean z);

    /* access modifiers changed from: protected */
    public abstract boolean a(byte[] bArr, int i);

    public Appender(LogContext logContext, String logCategory) {
        this.a = logContext;
        this.b = logCategory;
        this.c = logContext.getApplicationContext();
    }

    public final String b() {
        return this.b;
    }
}
