package com.jiuyan.inimage.b;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.util.n;
import java.util.List;

/* compiled from: AlipayBatchDownLoader */
class i implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ Context b;
    final /* synthetic */ h c;

    i(h hVar, List list, Context context) {
        this.c = hVar;
        this.a = list;
        this.b = context;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void run() {
        for (p pVar : this.a) {
            k kVar = new k(this.c, null);
            kVar.d = pVar;
            kVar.e = new l(this.b);
            this.c.a.add(kVar);
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < this.c.a.size()) {
                k kVar2 = (k) this.c.a.get(i2);
                kVar2.a = true;
                if (h.c(n.a(kVar2.d.b))) {
                    kVar2.b = true;
                    this.c.a();
                } else {
                    this.c.a(this.b, kVar2);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }
}
