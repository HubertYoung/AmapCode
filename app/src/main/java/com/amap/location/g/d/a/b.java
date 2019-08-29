package com.amap.location.g.d.a;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.amap.location.g.d.a.C0029a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: WifiScanListener */
public class b implements C0029a {
    private final List<a> a = new ArrayList();
    private com.amap.location.g.d.a.a.a b;

    /* compiled from: WifiScanListener */
    static class a {
        C0029a a;
        private Handler b;

        /* renamed from: com.amap.location.g.d.a.b$a$a reason: collision with other inner class name */
        /* compiled from: WifiScanListener */
        static class C0030a extends Handler {
            private C0029a a;

            C0030a(C0029a aVar, Looper looper) {
                super(looper);
                this.a = aVar;
            }

            public void handleMessage(Message message) {
                this.a.a();
            }
        }

        public a(C0029a aVar, Looper looper) {
            looper = looper == null ? Looper.getMainLooper() : looper;
            this.a = aVar;
            this.b = new C0030a(aVar, looper);
        }

        public void a() {
            this.b.sendEmptyMessage(0);
        }
    }

    public b(Context context, com.amap.location.g.d.a.a.a aVar) {
        this.b = aVar;
        this.b.a(context, this);
    }

    public void a() {
        synchronized (this.a) {
            for (a a2 : this.a) {
                a2.a();
            }
        }
    }

    public void a(@NonNull C0029a aVar) {
        synchronized (this.a) {
            for (a next : this.a) {
                if (next.a == aVar) {
                    next.a();
                    return;
                }
            }
            aVar.a();
        }
    }

    public void a(C0029a aVar, Looper looper) {
        if (aVar != null) {
            synchronized (this.a) {
                for (a aVar2 : this.a) {
                    if (aVar2.a == aVar) {
                        return;
                    }
                }
                this.a.add(new a(aVar, looper));
            }
        }
    }

    public void b(C0029a aVar) {
        if (aVar != null) {
            synchronized (this.a) {
                for (a next : this.a) {
                    if (next.a == aVar) {
                        this.a.remove(next);
                        return;
                    }
                }
            }
        }
    }
}
