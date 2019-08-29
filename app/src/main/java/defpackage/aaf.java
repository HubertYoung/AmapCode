package defpackage;

import android.content.Context;
import com.autonavi.amap.app.AMapAppGlobal;
import org.json.JSONObject;

/* renamed from: aaf reason: default package */
/* compiled from: NetworkContext */
public final class aaf {
    public static aae a;

    public static Context a() {
        return a == null ? AMapAppGlobal.getApplication() : a.a();
    }

    public static d b() {
        if (a == null) {
            return null;
        }
        return a.c();
    }

    public static b c() {
        if (a == null) {
            return null;
        }
        return a.g();
    }

    public static c d() {
        if (a == null) {
            return null;
        }
        return a.h();
    }

    public static k e() {
        if (a == null) {
            return null;
        }
        return a.d();
    }

    public static f f() {
        if (a == null) {
            return null;
        }
        return a.e();
    }

    public static e g() {
        if (a == null) {
            return null;
        }
        return a.i();
    }

    public static h h() {
        if (a == null) {
            return null;
        }
        return a.j();
    }

    public static g i() {
        if (a == null) {
            return null;
        }
        return a.l();
    }

    public static void a(String str) {
        l f = a == null ? null : a.f();
        if (f != null) {
            f.a(str);
        }
    }

    public static String b(String str) {
        aad m = a == null ? null : a.m();
        if (m == null) {
            return "";
        }
        return m.a(str);
    }

    public static void a(String str, String str2, JSONObject jSONObject) {
        k e = e();
        if (e != null) {
            e.a(str, str2, jSONObject);
        }
    }

    public static void a(String str, long j, JSONObject jSONObject) {
        k e = e();
        if (e != null) {
            e.a(str, j, jSONObject);
        }
    }
}
