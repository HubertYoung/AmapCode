package com.amap.location.e.d;

import android.support.annotation.Nullable;
import com.amap.location.e.b.a;
import com.amap.location.e.b.b;
import com.amap.location.e.b.d;
import com.amap.location.protocol.e;
import org.json.JSONObject;

/* compiled from: LocationRequestManagerHelper */
public class c {
    public static e a(final com.amap.location.e.c cVar) {
        return new e() {
            public final String a() {
                return cVar.a;
            }

            public final String b() {
                return cVar.b;
            }

            public final String c() {
                return cVar.c;
            }

            public final String d() {
                return c.a();
            }

            public final String e() {
                return cVar.e;
            }

            public final String f() {
                return cVar.f;
            }

            public final boolean g() {
                return cVar.h;
            }

            public final boolean h() {
                return cVar.i;
            }
        };
    }

    @Nullable
    public static byte[] a(boolean z) {
        if (!z) {
            b a = d.a();
            if (a != null) {
                return a.a();
            }
        }
        return null;
    }

    public static void a(JSONObject jSONObject) {
        b a = d.a();
        if (a != null) {
            a.a(jSONObject);
        }
    }

    public static String a() {
        b a = d.a();
        if (a != null) {
            return a.b();
        }
        return null;
    }

    public static void a(byte[] bArr) {
        com.amap.location.e.b.c d = d.d();
        if (d != null) {
            d.a(bArr);
        }
    }

    public static int b() {
        a b = d.b();
        if (b != null) {
            return b.k();
        }
        return 3;
    }
}
