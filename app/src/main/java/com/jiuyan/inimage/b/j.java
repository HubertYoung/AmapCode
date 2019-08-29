package com.jiuyan.inimage.b;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.q;

/* compiled from: AlipayBatchDownLoader */
class j implements s {
    final /* synthetic */ h a;

    j(h hVar) {
        this.a = hVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(String str) {
        q.a("BatchFileDownLoader", "onSuccess " + str);
        q.a("BatchFileDownLoader", "onSuccess " + str);
        k a2 = this.a.b(str);
        if (a2 != null) {
            a2.b = true;
        }
        this.a.a();
    }

    public void b(String str) {
        q.a("BatchFileDownLoader", "onFailed " + str);
        q.a("BatchFileDownLoader", "onFailed " + str);
        k a2 = this.a.b(str);
        if (a2 != null) {
            a2.c = true;
        }
        this.a.a();
    }
}
