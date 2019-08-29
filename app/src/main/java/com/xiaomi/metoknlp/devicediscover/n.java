package com.xiaomi.metoknlp.devicediscover;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import com.xiaomi.metoknlp.a;
import com.xiaomi.metoknlp.a.d;
import com.xiaomi.metoknlp.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class n {
    private static String a;
    private static n i;
    /* access modifiers changed from: private */
    public Object b = new Object();
    private g c;
    /* access modifiers changed from: private */
    public int d = 0;
    /* access modifiers changed from: private */
    public d e = new d();
    private h f;
    /* access modifiers changed from: private */
    public k g;
    private Context h;
    /* access modifiers changed from: private */
    public Handler j = new b(this);

    public static n a() {
        if (i == null) {
            i = new n();
        }
        return i;
    }

    /* access modifiers changed from: private */
    public void a(HashMap hashMap) {
        if (hashMap != null) {
            String c2 = j.c(this.h);
            if (!(this.e == null || c2 == null)) {
                this.e.g(c2);
                if (hashMap.containsKey(c2)) {
                    this.e.h((String) hashMap.get(c2));
                }
            }
            String b2 = j.b(this.h);
            if (b2 != null && hashMap.containsKey(b2)) {
                hashMap.remove(b2);
            }
            if (this.e != null && hashMap.size() > 0) {
                this.e.a((List) new ArrayList(hashMap.values()));
                c();
            }
        }
    }

    private void e() {
        m.a(this.h, this.j, 0);
    }

    private void f() {
        String d2 = j.d(this.h);
        String a2 = j.a(this.h, 2);
        String a3 = j.a(this.h, 1);
        if (!(d2 == null || a2 == null || a3 == null || this.e == null)) {
            this.e.a(Build.MODEL).b(d.b()).c(d2).f(a2).e(a3).a(this.f.a()).b(this.f.b());
        }
    }

    private void g() {
        c();
    }

    private void h() {
        if (this.d == 4 && this.e != null) {
            ((a) this.h).a(this.e.a().a().toString());
        }
    }

    private void i() {
        this.g = new k(this, null);
        a = b.a().e();
        StringBuffer stringBuffer = new StringBuffer(a);
        stringBuffer.append("/api/v2/realip");
        String stringBuffer2 = stringBuffer.toString();
        this.g.execute(new String[]{stringBuffer2});
    }

    public void a(Context context) {
        this.h = context;
        this.f = new h(this, null);
        this.c = new g(context);
        this.c.b();
        this.c.a((p) this.f);
    }

    public void b() {
        if (this.c != null) {
            this.c.c();
            this.c.d();
            this.c = null;
        }
        this.f = null;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (b.a().f()) {
            switch (this.d) {
                case 0:
                    this.d = 1;
                    f();
                    if (this.e != null) {
                        e();
                    }
                    return;
                case 1:
                    this.d = 2;
                    g();
                    return;
                case 2:
                    this.d = 3;
                    i();
                    return;
                case 3:
                    this.d = 4;
                    this.g.cancel(true);
                    this.g = null;
                    h();
                    return;
                default:
                    return;
            }
        }
    }

    public void d() {
        if (this.c != null) {
            this.c.a();
        }
    }
}
