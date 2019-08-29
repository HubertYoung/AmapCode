package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.h;
import com.xiaomi.tinyData.e;
import com.xiaomi.xmpush.thrift.f;
import java.util.List;

public class n implements e {
    /* access modifiers changed from: private */
    public final XMPushService a;

    public n(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    /* access modifiers changed from: private */
    public String a(String str) {
        return "com.xiaomi.xmsf".equals(str) ? "1000271" : this.a.getSharedPreferences("pref_registered_pkg_names", 0).getString(str, null);
    }

    public void a(List<f> list, String str, String str2) {
        XMPushService xMPushService = this.a;
        o oVar = new o(this, 4, str, list, str2);
        xMPushService.a((h) oVar);
    }
}
