package com.xiaomi.metoknlp;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.HandlerThread;
import com.xiaomi.metoknlp.a.d;
import com.xiaomi.metoknlp.b.c;
import java.util.ArrayList;
import java.util.List;

public class a extends ContextWrapper {
    private static a b;
    List a = new ArrayList();
    private HandlerThread c = new HandlerThread("metoknlp_app");
    private Handler d;
    private boolean e = false;
    private boolean f = false;
    private int g = 0;
    private com.xiaomi.metoknlp.b.a h = new i(this);

    private a(Context context) {
        super(context);
        this.c.start();
        this.d = new h(this, this.c.getLooper());
        d.a(context);
        this.d.sendEmptyMessageDelayed(101, 1000);
    }

    public static a a() {
        if (b == null) {
            return null;
        }
        return b;
    }

    public static a a(Context context) {
        if (b == null) {
            b = new a(context);
        }
        return b;
    }

    /* access modifiers changed from: private */
    public void g() {
        if (!this.e) {
            this.e = true;
        }
        d.a().a((Context) this);
    }

    /* access modifiers changed from: private */
    public void h() {
        this.d.sendEmptyMessageDelayed(102, 10000);
    }

    public void a(com.xiaomi.metoknlp.devicediscover.a aVar, int i) {
        for (com.xiaomi.metoknlp.devicediscover.a aVar2 : this.a) {
            if (aVar2 == aVar) {
                return;
            }
        }
        this.g = i;
        this.a.add(aVar);
    }

    public void a(String str) {
        for (com.xiaomi.metoknlp.devicediscover.a aVar : this.a) {
            if (aVar != null) {
                aVar.a(str);
            }
        }
    }

    public int b() {
        return this.g;
    }

    public void c() {
        d.a().b();
    }

    public Handler d() {
        return this.d;
    }

    public boolean e() {
        return this.e;
    }

    public void f() {
        b.a((Context) b);
        c.a((Context) b);
        c.a().a(this.h);
    }
}
