package defpackage;

import anet.channel.statist.RequestStatistic;
import anetwork.channel.aidl.ParcelableRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: dy reason: default package */
/* compiled from: RequestConfig */
public final class dy {
    public ParcelableRequest a;
    public ay b = null;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public RequestStatistic f;
    public final int g;
    public final int h;
    public final String i;
    public final int j;
    final boolean k;

    public dy(ParcelableRequest parcelableRequest, int i2, boolean z) {
        if (parcelableRequest == null) {
            throw new IllegalArgumentException("request is null");
        }
        this.a = parcelableRequest;
        this.j = i2;
        this.k = z;
        this.i = eo.a(parcelableRequest.m, this.j == 0 ? "HTTP" : "DGRD");
        this.g = parcelableRequest.j <= 0 ? (int) (db.b() * 12000.0f) : parcelableRequest.j;
        this.h = parcelableRequest.k <= 0 ? (int) (db.b() * 12000.0f) : parcelableRequest.k;
        this.d = (parcelableRequest.c < 0 || parcelableRequest.c > 3) ? 2 : parcelableRequest.c;
        cs a2 = cs.a(this.a.d);
        if (a2 == null) {
            StringBuilder sb = new StringBuilder("url is invalid. url=");
            sb.append(this.a.d);
            throw new IllegalArgumentException(sb.toString());
        }
        if (!ds.c()) {
            a2.g = true;
            if (!"http".equals(a2.a)) {
                a2.a = "http";
                a2.e = cz.a(a2.a, ":", a2.e.substring(a2.e.indexOf("//")));
            }
        } else if ("false".equalsIgnoreCase(this.a.a((String) "EnableSchemeReplace"))) {
            a2.g = true;
        }
        this.f = new RequestStatistic(a2.b, String.valueOf(parcelableRequest.l));
        this.f.url = a2.e;
        this.b = a(a2);
    }

    public final ay a(cs csVar) {
        a b2 = new a().a(csVar).b(this.a.g);
        b2.g = this.a.b;
        a b3 = b2.a(this.h).b(this.g);
        b3.h = this.a.f;
        b3.i = this.c;
        b3.l = this.a.l;
        b3.m = this.i;
        b3.p = this.f;
        b3.b(this.a.i);
        if (this.a.e != null) {
            b3.c(this.a.e);
        }
        b3.a(b(csVar));
        return b3.a();
    }

    public final int a() {
        return this.h * (this.d + 1);
    }

    public final String a(String str) {
        return this.a.a(str);
    }

    public final boolean b() {
        return ds.g() && !"false".equalsIgnoreCase(this.a.a((String) "EnableHttpDns")) && (ds.h() || this.e == 0);
    }

    public final Map<String, String> c() {
        return Collections.unmodifiableMap(this.b.c);
    }

    public final boolean d() {
        return !"false".equalsIgnoreCase(this.a.a((String) "EnableCookie"));
    }

    private Map<String, String> b(cs csVar) {
        String str = csVar.b;
        boolean z = !ci.a(str);
        if (str.length() > 2 && str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']' && ci.b(str.substring(1, str.length() - 1))) {
            z = false;
        }
        HashMap hashMap = new HashMap();
        if (this.a.h != null) {
            for (Entry next : this.a.h.entrySet()) {
                String str2 = (String) next.getKey();
                if (!"Host".equalsIgnoreCase(str2) && !":host".equalsIgnoreCase(str2)) {
                    boolean equalsIgnoreCase = "true".equalsIgnoreCase(this.a.a((String) "KeepCustomCookie"));
                    if (!"Cookie".equalsIgnoreCase(str2) || equalsIgnoreCase) {
                        hashMap.put(str2, next.getValue());
                    }
                } else if (!z) {
                    hashMap.put("Host", next.getValue());
                }
            }
        }
        return hashMap;
    }
}
