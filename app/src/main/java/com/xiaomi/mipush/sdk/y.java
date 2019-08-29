package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient.a.C0079a;
import com.xiaomi.xmpush.thrift.f;

class y implements Runnable {
    final /* synthetic */ f a;
    final /* synthetic */ C0079a b;

    y(C0079a aVar, f fVar) {
        this.b = aVar;
        this.a = fVar;
    }

    public void run() {
        this.b.a.add(this.a);
        this.b.a();
    }
}
