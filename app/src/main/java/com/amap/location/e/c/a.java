package com.amap.location.e.c;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.amap.location.common.f.e;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: AgeEstimator */
public abstract class a<T> {
    public String a;
    Map<String, C0017a> b = new ConcurrentHashMap();
    private File c;
    /* access modifiers changed from: private */
    public boolean d = false;
    /* access modifiers changed from: private */
    public Handler e;
    /* access modifiers changed from: private */
    public boolean f;
    /* access modifiers changed from: private */
    public Runnable g = new Runnable() {
        public void run() {
            if (a.this.d) {
                if (a.this.f) {
                    a.this.e();
                    a.this.f = false;
                }
                if (a.this.e != null) {
                    a.this.e.postDelayed(a.this.g, 60000);
                }
            }
        }
    };

    /* renamed from: com.amap.location.e.c.a$a reason: collision with other inner class name */
    /* compiled from: AgeEstimator */
    class C0017a {
        int a;
        long b;

        public C0017a(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a(T t, long j);

    public abstract String b(T t);

    /* access modifiers changed from: 0000 */
    public abstract int c(T t);

    /* access modifiers changed from: 0000 */
    public abstract long d(T t);

    public a(Context context, String str, Handler handler) {
        this.e = handler;
        this.a = TextUtils.isEmpty(str) ? "unknow" : str;
        if (context != null) {
            this.c = new File(context.getFilesDir().getAbsolutePath(), this.a);
        }
        d();
    }

    public void a() {
        if (!this.d && this.e != null) {
            this.e.removeCallbacks(this.g);
            this.e.postDelayed(this.g, 60000);
        }
        this.d = true;
    }

    public void b() {
        if (this.e != null) {
            this.e.removeCallbacks(this.g);
        }
        this.g.run();
        this.d = false;
    }

    public void c() {
        b();
        this.b.clear();
    }

    public void a(T t) {
        b(t, System.currentTimeMillis());
    }

    public void a(List<T> list) {
        long currentTimeMillis = System.currentTimeMillis();
        if (list != null) {
            for (T b2 : list) {
                b(b2, currentTimeMillis);
            }
            if (this.b.size() >= list.size()) {
                this.f = true;
            }
            this.b.clear();
            for (T next : list) {
                this.b.put(b(next), new C0017a(c(next), d(next)));
            }
        }
    }

    private void b(T t, long j) {
        if (t != null && d(t) >= 0) {
            String b2 = b(t);
            C0017a aVar = this.b.get(b2);
            if (aVar == null) {
                a(t, j);
                this.b.put(b2, new C0017a(c(t), d(t)));
                this.f = true;
            } else if (aVar.a != c(t)) {
                a(t, j);
                aVar.a = c(t);
                aVar.b = d(t);
                this.f = true;
            } else {
                a(t, aVar.b);
            }
        }
    }

    private void d() {
        for (String split : e.c(this.c)) {
            try {
                String[] split2 = split.split(",");
                this.b.put(split2[0], new C0017a(Integer.parseInt(split2[1]), Long.parseLong(split2[2])));
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        StringBuilder sb = new StringBuilder();
        for (Entry next : this.b.entrySet()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append((String) next.getKey());
            sb2.append(",");
            sb2.append(((C0017a) next.getValue()).a);
            sb2.append(",");
            sb2.append(((C0017a) next.getValue()).b);
            sb2.append("\n");
            sb.append(sb2.toString());
        }
        String sb3 = sb.toString();
        if (!TextUtils.isEmpty(sb)) {
            e.a(sb3, this.c, false);
        }
    }
}
