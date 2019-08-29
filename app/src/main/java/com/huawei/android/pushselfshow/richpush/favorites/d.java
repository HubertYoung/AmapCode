package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Context;
import com.huawei.android.pushagent.a.a.c;

class d implements Runnable {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public void run() {
        c.a("PushSelfShowLog", "deleteTipDialog mThread run");
        boolean z = false;
        for (e eVar : this.a.a.h.a()) {
            if (eVar.a()) {
                com.huawei.android.pushselfshow.utils.a.c.a((Context) this.a.a.b, eVar.c());
                z = true;
            }
        }
        if (z) {
            if (!this.a.a.k) {
                this.a.a.h.b();
            }
            this.a.a.a.sendEmptyMessage(1001);
        }
    }
}
