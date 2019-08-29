package com.xiaomi.metoknlp;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.xiaomi.metoknlp.devicediscover.n;

public class d {
    private static d g;
    private Context a;
    private Handler b;
    private HandlerThread c;
    private g d;
    private boolean e;
    private boolean f;

    public static d a() {
        if (g == null) {
            g = new d();
        }
        return g;
    }

    /* access modifiers changed from: private */
    public void d() {
        n.a().a(this.a);
        this.f = true;
    }

    /* access modifiers changed from: private */
    public void e() {
        n.a().b();
        this.f = false;
    }

    public void a(Context context) {
        this.a = context;
        b.a(this.a);
        if (!this.e) {
            this.e = true;
            this.c = new HandlerThread("metoknlp_cl");
            this.c.start();
            this.b = new Handler(this.c.getLooper());
            this.d = new f(this, null);
            b.a().a(this.d);
            if (a.a().e()) {
                c();
            }
        }
    }

    public void b() {
        n.a().d();
    }

    public void c() {
        if (this.b != null) {
            this.b.post(new e(this));
        }
    }
}
