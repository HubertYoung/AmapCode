package com.alipay.sdk.widget;

final class c implements Runnable {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    public final void run() {
        if (this.a.f != null) {
            try {
                this.a.f.dismiss();
            } catch (Exception unused) {
            }
        }
    }
}
