package com.amap.location.sdk.b;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.a.b;
import com.amap.location.common.e.c;
import com.amap.location.e.b.d;
import com.amap.location.e.f;
import com.amap.location.sdk.BuildConfig;
import org.json.JSONObject;

/* compiled from: CloudWrapper */
public class a {
    private b a;
    private Context b;
    private com.amap.location.a.b.a c = new com.amap.location.a.b.a() {
        public void a(com.amap.location.a.a aVar) {
            a.this.a(aVar);
        }

        public void a() {
            d.a((com.amap.location.e.b.a) new C0036a(null));
        }
    };

    /* renamed from: com.amap.location.sdk.b.a$a reason: collision with other inner class name */
    /* compiled from: CloudWrapper */
    static class C0036a implements com.amap.location.e.b.a {
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
        private int k = 3;

        C0036a(JSONObject jSONObject) {
            if (jSONObject != null) {
                this.a = jSONObject.optBoolean("loe", true);
                this.b = jSONObject.optLong("loct", 0);
                this.c = jSONObject.optBoolean("loca", false);
                this.d = jSONObject.optInt("lott", 6);
                this.e = jSONObject.optInt("lomwn", 8);
                try {
                    String optString = jSONObject.optString("locpl", "");
                    if (!TextUtils.isEmpty(optString)) {
                        this.f = optString.split(",");
                    }
                } catch (Exception e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                }
                this.g = jSONObject.optInt("lomrt", 10);
                this.h = jSONObject.optInt("lomnwrt", 5);
                this.i = jSONObject.optInt("lomnpr", 100);
                this.j = jSONObject.optBoolean("lonfd", false);
                this.k = jSONObject.optInt("louq", 3);
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

        public int k() {
            return this.k;
        }
    }

    public a(Context context) {
        this.b = context;
        this.a = b.a();
        com.amap.location.a.a.a aVar = new com.amap.location.a.a.a();
        aVar.a((c) d.a());
        if ("amap_auto".equalsIgnoreCase(com.amap.api.service.b.c())) {
            aVar.a(3);
        } else {
            aVar.a(0);
        }
        aVar.a((String) BuildConfig.VERSION_NAME);
        this.a.a(context, aVar);
        this.a.a(this.c);
    }

    public void a() {
        b.a().b();
    }

    /* access modifiers changed from: private */
    public void a(com.amap.location.a.a aVar) {
        try {
            JSONObject jSONObject = new JSONObject(aVar.a());
            JSONObject optJSONObject = jSONObject.optJSONObject("l");
            if ("1".equals(optJSONObject.optString("lts"))) {
                com.amap.location.sdk.e.d.a(this.b, true);
                com.amap.location.sdk.b.a.d.a(com.amap.location.uptunnel.a.c.a(3));
                com.amap.location.sdk.b.a.d.a(com.amap.location.uptunnel.a.c.a(4));
            } else {
                com.amap.location.sdk.e.d.a(this.b, false);
            }
            com.amap.location.sdk.e.d.b(this.b, optJSONObject.optBoolean("lkls", false));
            d.a((com.amap.location.e.b.a) new C0036a(optJSONObject));
            if (jSONObject.has("nl")) {
                JSONObject optJSONObject2 = jSONObject.optJSONObject("nl");
                if (optJSONObject2 != null) {
                    f.a(optJSONObject2);
                }
            }
        } catch (Exception unused) {
            d.a((com.amap.location.e.b.a) new C0036a(null));
        }
    }
}
