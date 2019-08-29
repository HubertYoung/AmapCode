package com.alipay.deviceid;

import com.alipay.deviceid.DeviceTokenClient.InitResultListener;
import com.alipay.deviceid.module.x.a;
import java.util.Map;

final class b implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ InitResultListener b;
    final /* synthetic */ String c;
    final /* synthetic */ DeviceTokenClient d;

    b(DeviceTokenClient deviceTokenClient, Map map, InitResultListener initResultListener, String str) {
        this.d = deviceTokenClient;
        this.a = map;
        this.b = initResultListener;
        this.c = str;
    }

    public final void run() {
        int a2 = new a(this.d.context).a(this.a);
        if (this.b != null) {
            if (a2 == 0) {
                this.b.onResult(a.a(this.d.context, this.c), 0);
                return;
            }
            this.b.onResult("", a2);
        }
    }
}
