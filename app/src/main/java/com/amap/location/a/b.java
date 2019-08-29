package com.amap.location.a;

import android.content.Context;
import com.amap.location.a.a.a;

/* compiled from: LocationCloudManager */
public class b {
    private static volatile b a;
    private c b = new c();
    private volatile boolean c = false;

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                try {
                    if (a == null) {
                        a = new b();
                    }
                }
            }
        }
        return a;
    }

    public synchronized void a(Context context, a aVar) {
        if (!this.c) {
            this.b.a(context, aVar);
            this.c = true;
        }
    }

    public void a(com.amap.location.a.b.a aVar) {
        this.b.a(aVar);
    }

    public void b(com.amap.location.a.b.a aVar) {
        this.b.b(aVar);
    }

    public synchronized void b() {
        if (this.c) {
            this.b.a();
            this.c = false;
        }
    }

    private b() {
    }
}
