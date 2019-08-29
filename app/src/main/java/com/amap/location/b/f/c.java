package com.amap.location.b.f;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.a.b;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

/* compiled from: CloudWrapper */
public class c {
    private Context a;
    private com.amap.location.b.a b;
    private b c;
    private com.amap.location.common.e.c d;
    private a e;
    private com.amap.location.a.b.a f = new com.amap.location.a.b.a() {
        public void a() {
        }

        public void a(com.amap.location.a.a aVar) {
            c.this.a(aVar.a());
        }
    };

    /* compiled from: CloudWrapper */
    public interface a {
        void a();
    }

    public c(Context context, com.amap.location.b.a aVar, com.amap.location.common.e.c cVar, a aVar2) {
        this.a = context;
        this.b = aVar;
        this.d = cVar;
        this.e = aVar2;
    }

    public void a() {
        this.c = b.a();
        this.c.a(this.f);
        if (this.b.a() == 4) {
            com.amap.location.a.a.a aVar = new com.amap.location.a.a.a();
            aVar.a(this.b.a());
            aVar.a(this.b.b());
            aVar.c(this.b.c());
            aVar.b(this.b.d());
            aVar.d(this.b.e());
            aVar.e(com.amap.location.common.a.a());
            aVar.a(this.d);
            this.c.a(this.a, aVar);
        }
    }

    public void b() {
        this.c.b(this.f);
        if (this.b.a() == 4) {
            this.c.b();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (a(new JSONObject(str)) && this.e != null) {
                    this.e.a();
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    private boolean a(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject(SuperId.BIT_1_NEARBY_SEARCH);
        boolean z = false;
        if (optJSONObject == null) {
            return false;
        }
        boolean optBoolean = optJSONObject.optBoolean("cls", this.b.g().a());
        if (optBoolean != this.b.g().a()) {
            this.b.g().a(optBoolean);
            z = true;
        }
        boolean optBoolean2 = optJSONObject.optBoolean("cts", this.b.h().b());
        if (optBoolean2 != this.b.h().b()) {
            this.b.h().a(optBoolean2);
            z = true;
        }
        boolean a2 = a(optJSONObject, "cnwuss", this.b.i().a());
        if (a2 != this.b.i().a()) {
            this.b.i().b(a2);
            z = true;
        }
        boolean optBoolean3 = optJSONObject.optBoolean("cfup", this.b.g().d());
        if (optBoolean3 != this.b.g().d()) {
            this.b.g().b(optBoolean3);
            z = true;
        }
        return z;
    }

    private boolean a(JSONObject jSONObject, String str, boolean z) {
        return jSONObject.optInt(str, z ? 1 : 0) == 1;
    }
}
