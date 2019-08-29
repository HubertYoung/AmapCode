package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.metoknlp.geofencing.a;
import com.xiaomi.xmpush.thrift.m;
import com.xiaomi.xmpush.thrift.o;

public class q {
    private final int a = -1;
    private final double b = 0.0d;
    private a c;
    private Context d;

    public q(Context context) {
        this.d = context;
        a();
    }

    private void a() {
        this.c = new a(this.d);
    }

    public void a(String str) {
        this.c.a(this.d, "com.xiaomi.xmsf", str);
    }

    public boolean a(m mVar) {
        if (mVar == null) {
            return false;
        }
        if (mVar.m() != null && mVar.o() > 0.0d) {
            o m = mVar.m();
            this.c.a(this.d, m.c(), m.a(), (float) mVar.o(), -1, "com.xiaomi.xmsf", mVar.a(), mVar.s().name());
        }
        return true;
    }
}
