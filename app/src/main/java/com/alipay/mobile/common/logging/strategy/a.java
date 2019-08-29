package com.alipay.mobile.common.logging.strategy;

/* compiled from: LogStrategyManager */
final class a implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ boolean b;
    final /* synthetic */ LogStrategyManager c;

    a(LogStrategyManager this$0, String str, boolean z) {
        this.c = this$0;
        this.a = str;
        this.b = z;
    }

    public final void run() {
        this.c.syncRequestLogConfig(this.a, this.b);
    }
}
