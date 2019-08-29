package com.amap.location.b.c;

import android.content.Context;
import android.os.Looper;
import com.amap.location.common.a.a.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DataManager */
public class e {
    private Looper a;
    private com.amap.location.common.a.a<d> b = new com.amap.location.common.a.a<>();
    private a c = new a();
    /* access modifiers changed from: private */
    public f d;

    /* compiled from: DataManager */
    class a implements b<d> {
        public void a() {
        }

        public long c() {
            return 10240;
        }

        public long d() {
            return 60000;
        }

        private a() {
        }

        public void b() {
            e.this.d.a();
        }

        public boolean a(long j) {
            return e.this.d.a(j);
        }

        public void a(ArrayList<d> arrayList) {
            if (arrayList != null && arrayList.size() != 0) {
                e.this.d.a((List<d>) arrayList);
            }
        }
    }

    public e(Context context, Looper looper) {
        this.a = looper;
        this.d = new f(context);
    }

    public void a() {
        this.b.a((b<Item>) this.c, this.a);
    }

    public void b() {
        try {
            this.b.a();
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    public void a(int i, byte[] bArr) {
        this.b.a(new d(i, bArr));
    }

    public com.amap.location.b.e.b a(boolean z, int i, long j) {
        return this.d.a(z, i, j);
    }

    public void a(com.amap.location.b.e.b bVar) {
        this.d.a(bVar);
    }

    public int c() {
        return this.d.b();
    }

    public int d() {
        return this.d.c();
    }
}
