package com.autonavi.patch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public final class SoHotfixInvoker {
    Object a;
    volatile int b;
    Handler c;
    Runnable d;

    @SuppressLint({"ParcelCreator"})
    class InvokerReceiver extends ResultReceiver {
        final /* synthetic */ SoHotfixInvoker a;

        /* access modifiers changed from: protected */
        public void onReceiveResult(int i, Bundle bundle) {
            this.a.c.removeCallbacks(this.a.d);
            synchronized (this.a) {
                if (this.a.b != 2) {
                    this.a.b = i;
                    synchronized (this.a.a) {
                        this.a.a.notify();
                    }
                }
            }
        }
    }
}
