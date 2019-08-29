package defpackage;

import android.text.TextUtils;

/* renamed from: egf reason: default package */
/* compiled from: BikeStatusImpl */
public final class egf implements bde {

    /* renamed from: egf$a */
    /* compiled from: BikeStatusImpl */
    static class a {
        static egf a = new egf();
    }

    public final void a() {
        eht.e();
    }

    public final boolean b() {
        return eht.c();
    }

    public final void a(ehp ehp) {
        eht.a(ehp);
    }

    public final void a(egw egw) {
        egx.a().a(egw);
    }

    public final void b(egw egw) {
        egx.a().b(egw);
    }

    public final void a(boolean z) {
        egj.a().a(z);
    }

    public final boolean c() {
        String trim = ehs.b("share_bike_riding_status_id").trim();
        if (TextUtils.isEmpty(trim)) {
            return false;
        }
        char parseBoolean = Boolean.parseBoolean(trim);
        char c = Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id")) ? (char) 2 : 0;
        if (parseBoolean == 0 && c == 2) {
            parseBoolean = 2;
        }
        if (parseBoolean == 2 || parseBoolean == 1) {
            return true;
        }
        return false;
    }
}
