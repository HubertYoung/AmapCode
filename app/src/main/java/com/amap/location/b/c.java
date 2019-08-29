package com.amap.location.b;

import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.location.b.f.c.a;

/* compiled from: CollectionManagerProxy */
public class c {
    private Context a;
    private a b;
    private com.amap.location.common.e.c c;
    private b d;
    private com.amap.location.b.f.c e;
    private boolean f;

    public static String b() {
        return "v74";
    }

    public synchronized void a(@NonNull Context context, @NonNull a aVar, @NonNull com.amap.location.common.e.c cVar) {
        if (!this.f) {
            this.f = true;
            this.a = context.getApplicationContext();
            this.b = aVar;
            this.c = cVar;
            this.e = new com.amap.location.b.f.c(this.a, this.b, this.c, new a() {
                public void a() {
                    c.this.d();
                }
            });
            this.e.a();
            this.d = new b(this.a, this.b, cVar);
            this.d.a();
        }
    }

    public synchronized void a() {
        if (this.f) {
            this.e.b();
            this.d.b();
            this.f = false;
        }
    }

    /* access modifiers changed from: private */
    public synchronized void d() {
        if (this.f) {
            this.d.b();
            this.d = new b(this.a, this.b, this.c);
            this.d.a();
        }
    }

    public synchronized com.amap.location.b.e.a c() {
        com.amap.location.b.e.a aVar;
        aVar = null;
        try {
            if (this.f) {
                aVar = this.d.c();
            }
        }
        return aVar;
    }

    public synchronized void a(boolean z, com.amap.location.b.e.a aVar) {
        if (this.f) {
            this.d.a(z, aVar);
        }
    }
}
