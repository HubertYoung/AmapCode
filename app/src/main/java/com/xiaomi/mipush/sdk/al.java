package com.xiaomi.mipush.sdk;

import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.av;

class al extends ContentObserver {
    final /* synthetic */ aj a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    al(aj ajVar, Handler handler) {
        // this.a = ajVar;
        super(handler);
    }

    public void onChange(boolean z) {
        this.a.l = Integer.valueOf(av.a(this.a.c).b());
        if (this.a.l.intValue() != 0) {
            this.a.c.getContentResolver().unregisterContentObserver(this);
            if (d.c(this.a.c)) {
                this.a.d();
            }
        }
    }
}
