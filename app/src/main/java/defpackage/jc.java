package defpackage;

import android.text.TextUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: jc reason: default package */
/* compiled from: URIBuilder */
public final class jc {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public int g;
    public String h;
    public String i;
    public String j;
    public List<ja> k;
    public String l;
    public String m;

    public jc() {
        this.g = -1;
    }

    public jc(String str) {
        List<ja> list;
        try {
            URI uri = new URI(str);
            this.a = uri.getScheme();
            this.b = uri.getRawSchemeSpecificPart();
            this.c = uri.getRawAuthority();
            this.f = uri.getHost();
            this.g = uri.getPort();
            this.e = uri.getRawUserInfo();
            this.d = uri.getUserInfo();
            this.i = uri.getRawPath();
            this.h = uri.getPath();
            this.j = uri.getRawQuery();
            String rawQuery = uri.getRawQuery();
            if (!TextUtils.isEmpty(rawQuery)) {
                list = jd.a(rawQuery);
            } else {
                list = null;
            }
            this.k = list;
            this.m = uri.getRawFragment();
            this.l = uri.getFragment();
        } catch (URISyntaxException unused) {
        }
    }

    public final String a(String str) {
        String str2;
        if (this.k != null && this.k.size() > 0) {
            Iterator<ja> it = this.k.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ja next = it.next();
                if (next.a.equals(str)) {
                    str2 = next.b;
                    it.remove();
                    break;
                }
            }
        }
        str2 = null;
        if (!TextUtils.isEmpty(str2)) {
            this.j = null;
            this.b = null;
        }
        return str2;
    }

    public final jc a(String str, String str2) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        this.k.add(new ja(str, str2));
        this.j = null;
        this.b = null;
        return this;
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        int i2 = 0;
        while (i2 < str.length() && str.charAt(i2) == '/') {
            i2++;
        }
        if (i2 > 1) {
            str = str.substring(i2 - 1);
        }
        return str;
    }
}
