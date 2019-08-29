package com.ali.auth.third.core.task;

import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.trace.SDKLogger;

class c implements Runnable {
    final /* synthetic */ InitResultCallback[] a;
    final /* synthetic */ boolean b;
    final /* synthetic */ int c;
    final /* synthetic */ String d;
    final /* synthetic */ a e;

    c(a aVar, InitResultCallback[] initResultCallbackArr, boolean z, int i, String str) {
        this.e = aVar;
        this.a = initResultCallbackArr;
        this.b = z;
        this.c = i;
        this.d = str;
    }

    public void run() {
        InitResultCallback[] initResultCallbackArr;
        for (InitResultCallback initResultCallback : this.a) {
            try {
                if (this.b) {
                    initResultCallback.onSuccess();
                } else {
                    initResultCallback.onFailure(this.c, this.d);
                }
            } catch (Exception e2) {
                SDKLogger.e("kernel", e2.getMessage(), e2);
            }
        }
    }
}
