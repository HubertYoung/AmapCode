package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.push.service.an;
import com.xiaomi.xmpush.thrift.g;

final class b implements Runnable {
    final /* synthetic */ Context a;

    b(Context context) {
        this.a = context;
    }

    public final void run() {
        if (a.d(this.a)) {
            String a2 = a.b(an.a(this.a).a(g.AggregationSdkMonitorDepth.a(), 4));
            if (!TextUtils.isEmpty(a2)) {
                MiTinyDataClient.upload(this.a, "monitor_upload", "call_stack", 1, a2);
                a.e(this.a);
            }
        }
    }
}
