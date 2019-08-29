package com.xiaomi.push.service;

import com.xiaomi.push.service.module.PushChannelRegion;

class c implements Runnable {
    final /* synthetic */ PushChannelRegion a;
    final /* synthetic */ long b;
    final /* synthetic */ int c;
    final /* synthetic */ a d;

    c(a aVar, PushChannelRegion pushChannelRegion, long j, int i) {
        this.d = aVar;
        this.a = pushChannelRegion;
        this.b = j;
        this.c = i;
    }

    public void run() {
        this.d.a(this.d.j, this.a);
        this.d.p = true;
        synchronized (this.d.h) {
            this.d.h.notifyAll();
        }
        long currentTimeMillis = System.currentTimeMillis() - this.b;
        if (currentTimeMillis > ((long) this.c)) {
            bf.a(this.d.j, "category_region_read", "region_write_late", 1, String.valueOf(currentTimeMillis));
        }
    }
}
