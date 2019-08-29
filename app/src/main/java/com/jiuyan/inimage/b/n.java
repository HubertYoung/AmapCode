package com.jiuyan.inimage.b;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: AlipayDownloader */
class n implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ l b;

    n(l lVar, String str) {
        this.b = lVar;
        this.a = str;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        if (this.b.c != null) {
            this.b.c.a(this.a);
        }
    }
}
