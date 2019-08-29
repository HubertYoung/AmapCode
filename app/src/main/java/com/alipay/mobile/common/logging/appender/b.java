package com.alipay.mobile.common.logging.appender;

/* compiled from: ExternalFileAppender */
final class b implements Runnable {
    final /* synthetic */ ExternalFileAppender a;

    b(ExternalFileAppender this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.d();
    }
}
