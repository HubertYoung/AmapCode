package com.amap.location.sdk.b;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.location.b.c;
import com.amap.location.e.b.d;
import com.amap.location.sdk.BuildConfig;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

/* compiled from: CollectWrapper */
public class b {
    /* access modifiers changed from: private */
    public Context a;
    private a b;
    private com.amap.location.a.b c;
    private boolean d = true;
    private volatile boolean e = false;
    private com.amap.location.a.b.a f = new com.amap.location.a.b.a() {
        public void a(com.amap.location.a.a aVar) {
            b.this.a(aVar);
        }

        public void a() {
            b.this.e();
        }
    };

    /* compiled from: CollectWrapper */
    interface a {
        void a();

        void a(JSONObject jSONObject);

        void b();

        void c();

        void d();
    }

    /* renamed from: com.amap.location.sdk.b.b$b reason: collision with other inner class name */
    /* compiled from: CollectWrapper */
    class C0037b implements a {
        c a = new c();
        com.amap.location.b.a b = e();
        com.amap.location.b.e.a c;
        com.amap.location.common.e.c d = d.a();

        /* renamed from: com.amap.location.sdk.b.b$b$a */
        /* compiled from: CollectWrapper */
        class a implements com.amap.location.e.b.b {
            a() {
            }

            @Nullable
            public byte[] a() {
                if (C0037b.this.a == null) {
                    return null;
                }
                C0037b.this.c = C0037b.this.a.c();
                if (C0037b.this.c != null) {
                    return C0037b.this.c.a;
                }
                return null;
            }

            public void a(JSONObject jSONObject) {
                if (jSONObject != null) {
                    try {
                        C0037b.this.a.a(jSONObject.optInt("suc") != 0, C0037b.this.c);
                    } catch (Exception e) {
                        com.amap.location.common.d.a.a((Throwable) e);
                    }
                }
            }

            public String b() {
                return c.b();
            }
        }

        public void d() {
        }

        public C0037b() {
        }

        private com.amap.location.b.a e() {
            com.amap.location.b.a aVar = new com.amap.location.b.a();
            aVar.a(0);
            aVar.a((String) BuildConfig.VERSION_NAME);
            aVar.b("ABKLWEH8H9LH09NLB5CCAGHK78BYZ89");
            aVar.c(com.amap.location.common.a.b(b.this.a));
            aVar.h().a(true);
            aVar.i().a(true);
            aVar.i().b(false);
            aVar.a(false);
            return aVar;
        }

        public void a(JSONObject jSONObject) {
            try {
                String optString = jSONObject.optString("tid");
                String optString2 = jSONObject.optString(LocationParams.PARA_COMMON_LOC_SCENE, "0");
                if (TextUtils.isEmpty(this.b.e()) && !TextUtils.isEmpty(optString)) {
                    this.b.c(optString);
                }
                this.b.h().a(optString2);
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }

        public void a() {
            if (this.b != null && this.a != null) {
                this.a.a(b.this.a, this.b, this.d);
            }
        }

        public void b() {
            d.a((com.amap.location.e.b.b) new a());
        }

        public void c() {
            if (this.a != null) {
                this.a.a();
            }
        }
    }

    public b(Context context) {
        this.a = context;
        this.d = com.amap.api.service.b.a();
        this.c = com.amap.location.a.b.a();
        this.c.a(this.f);
    }

    /* access modifiers changed from: private */
    public void a(com.amap.location.a.a aVar) {
        try {
            if (new JSONObject(aVar.a()).getJSONObject(SuperId.BIT_1_NEARBY_SEARCH).getInt(LogItem.MM_C15_K4_C_SIZE) == 1) {
                this.d = true;
            } else {
                this.d = false;
            }
            e();
        } catch (Exception unused) {
            e();
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.d && this.b == null && !this.e) {
            this.b = new C0037b();
            this.b.a();
            this.b.b();
        }
    }

    public void a() {
        this.e = false;
        if (this.d && this.b != null) {
            this.b.a();
        }
    }

    public void b() {
        this.e = true;
        if (this.b != null) {
            this.b.c();
        }
    }

    public void c() {
        if (this.b != null) {
            this.b.d();
        }
    }

    public void a(JSONObject jSONObject) {
        if (this.b != null) {
            this.b.a(jSONObject);
        }
    }

    public static String d() {
        return c.b();
    }
}
