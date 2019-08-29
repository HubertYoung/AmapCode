package com.amap.location.offline.a;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.offline.b;
import com.amap.location.offline.c;
import org.json.JSONObject;

/* compiled from: CloudWrapper */
public class a {
    private Context a;
    private c b;
    private b c;
    private com.amap.location.a.b d;
    private com.amap.location.a.b.a e = new com.amap.location.a.b.a() {
        public void a() {
        }

        public void a(com.amap.location.a.a aVar) {
            a.this.a(aVar.a());
        }
    };

    /* renamed from: com.amap.location.offline.a.a$a reason: collision with other inner class name */
    /* compiled from: CloudWrapper */
    static class C0032a implements com.amap.location.offline.a {
        private boolean a = true;
        private long b = 0;
        private boolean c = false;
        private int d = 6;
        private int e = 8;
        private String[] f;
        private int g = 10;
        private int h = 5;
        private int i = 100;
        private boolean j = false;

        C0032a(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.a = jSONObject.optBoolean("loe", true);
                this.b = jSONObject.optLong("loct", 0);
                this.c = jSONObject.optBoolean("loca", false);
                this.d = jSONObject.optInt("lott", 6);
                this.e = jSONObject.optInt("lomwn", 8);
                try {
                    this.f = jSONObject.optString("locpl", "").split(",");
                } catch (Exception e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                }
                this.g = jSONObject.optInt("lomrt", 10);
                this.h = jSONObject.optInt("lomnwrt", 5);
                this.i = jSONObject.optInt("lomnpr", 100);
                this.j = jSONObject.optBoolean("lonfd", false);
            }
        }

        public boolean a() {
            return this.a;
        }

        public long b() {
            return this.b;
        }

        public boolean c() {
            return this.c;
        }

        public int d() {
            return this.d;
        }

        public int e() {
            return this.e;
        }

        public String[] f() {
            return this.f;
        }

        public int g() {
            return this.g;
        }

        public int h() {
            return this.h;
        }

        public int i() {
            return this.i;
        }

        public boolean j() {
            return this.j;
        }
    }

    public a(Context context, c cVar, b bVar) {
        this.a = context;
        this.b = cVar;
        this.c = bVar;
    }

    public void a() {
        if (this.b.b == 4 && this.b.k && this.c.a()) {
            this.d = com.amap.location.a.b.a();
            this.d.a(this.e);
            com.amap.location.a.a.a aVar = new com.amap.location.a.a.a();
            aVar.a(this.b.b);
            aVar.a(this.b.d);
            aVar.c(this.b.i);
            aVar.b(this.b.j);
            aVar.d(this.b.h);
            aVar.e(com.amap.location.common.a.a());
            aVar.a(this.b.n);
            this.d.a(this.a, aVar);
        }
    }

    public void b() {
        if (this.b.b == 4 && this.d != null) {
            this.d.b(this.e);
            this.d.b();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.c.a = new C0032a(jSONObject);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }
}
