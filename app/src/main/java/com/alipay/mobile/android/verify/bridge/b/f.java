package com.alipay.mobile.android.verify.bridge.b;

/* compiled from: ScriptLoaderPlugin */
class f implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ d b;

    f(d dVar, String str) {
        this.b = dVar;
        this.a = str;
    }

    public void run() {
        if (this.b.b) {
            this.b.a.loadUrl(String.format("javascript: %s", new Object[]{this.a}));
        }
    }
}
