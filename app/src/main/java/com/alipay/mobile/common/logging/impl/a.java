package com.alipay.mobile.common.logging.impl;

import android.content.Context;
import com.alipay.mobile.common.nativecrash.NativeCrashHandler;

/* compiled from: StatisticalExceptionHandler */
final class a implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ StatisticalExceptionHandler b;

    a(StatisticalExceptionHandler this$0, Context context) {
        this.b = this$0;
        this.a = context;
    }

    public final void run() {
        NativeCrashHandler.initialize(this.a);
        this.b.c = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this.b);
    }
}
