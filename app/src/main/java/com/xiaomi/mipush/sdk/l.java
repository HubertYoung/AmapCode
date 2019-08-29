package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.mpcd.b;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.u;

public class l implements b {
    private Context a;

    public l(Context context) {
        this.a = context;
    }

    public String a() {
        return c.a(this.a).f();
    }

    public void a(ai aiVar, a aVar, u uVar) {
        aj.a(this.a).a(aiVar, aVar, uVar);
    }
}
