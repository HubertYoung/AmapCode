package com.amap.location.e;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.location.common.d.a;
import com.amap.location.e.c.b;
import com.amap.location.offline.c;
import org.json.JSONObject;

/* compiled from: LocationClient */
public class f {
    private c a;
    private volatile i b;

    public f(@NonNull c cVar) {
        this.a = cVar;
    }

    public synchronized void a(@NonNull b bVar) {
        this.a.k = bVar;
        if (this.b != null) {
            this.b.b();
        }
    }

    public synchronized void a(@NonNull c cVar) {
        this.a.m = cVar;
        if (this.b != null) {
            this.b.c();
        }
    }

    public synchronized void a(@NonNull Context context) {
        a.b("nlclient", "nl init");
        if (this.b == null) {
            this.b = new i(context, this.a);
            this.b.d();
        }
    }

    public synchronized void a() {
        boolean z = false;
        if (this.b != null) {
            this.b.e();
            this.b = null;
            z = true;
        }
        a.b("nlclient", "nl destroy:".concat(String.valueOf(z)));
    }

    public void a(int i, a aVar, boolean z) {
        i iVar = this.b;
        StringBuilder sb = new StringBuilder("request:");
        sb.append(i);
        sb.append(Token.SEPARATOR);
        sb.append(z);
        a.b("nlclient", sb.toString());
        if (iVar != null) {
            iVar.a(i, aVar, z);
        }
    }

    public void b() {
        i iVar = this.b;
        a.b("nlclient", "remove:".concat(String.valueOf(iVar)));
        if (iVar != null) {
            iVar.f();
        }
    }

    public String c() {
        i iVar = this.b;
        if (iVar != null) {
            return iVar.g();
        }
        return null;
    }

    public static void a(JSONObject jSONObject) {
        b.a(jSONObject);
    }
}
