package com.alipay.mobile.common.logging;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.ToolThreadUtils;

/* compiled from: LogContextImpl */
final class a implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ Bundle c;
    final /* synthetic */ LogContextImpl d;

    a(LogContextImpl this$0, String str, String str2, Bundle bundle) {
        this.d = this$0;
        this.a = str;
        this.b = str2;
        this.c = bundle;
    }

    public final void run() {
        ToolThreadUtils.getInstance(LoggerFactory.getLogContext().getApplicationContext()).start(true);
        this.d.b(this.a, this.b, this.c);
        ToolThreadUtils.getInstance(LoggerFactory.getLogContext().getApplicationContext()).end();
    }
}
