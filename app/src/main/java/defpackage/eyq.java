package defpackage;

import java.util.ArrayList;
import java.util.List;

/* renamed from: eyq reason: default package */
/* compiled from: OnDelTagsReceiveTask */
public final class eyq extends eya {
    public eyq(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        ext ext = (ext) fbh;
        ArrayList<String> arrayList = ext.a;
        ArrayList<String> arrayList2 = ext.b;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        int i = ext.e;
        String str = ext.d;
        if (arrayList != null) {
            for (String next : arrayList) {
                if (next.startsWith("ali/")) {
                    arrayList4.add(next.replace("ali/", ""));
                } else if (next.startsWith("tag/")) {
                    arrayList3.add(next.replace("tag/", ""));
                }
            }
        }
        if (arrayList2 != null) {
            for (String next2 : arrayList2) {
                if (next2.startsWith("ali/")) {
                    arrayList6.add(next2.replace("ali/", ""));
                } else if (next2.startsWith("tag/")) {
                    arrayList5.add(next2.replace("tag/", ""));
                }
            }
        }
        if (arrayList3.size() > 0 || arrayList5.size() > 0) {
            if (arrayList3.size() > 0) {
                ezv.a().b((List<String>) arrayList3);
            }
            ezv.a().a(ext.d, arrayList5.size() > 0 ? 10000 : i);
            eyr eyr = new eyr(this, i, arrayList3, arrayList5, str);
            fbf.b(eyr);
        }
        if (arrayList4.size() > 0 || arrayList6.size() > 0) {
            if (arrayList4.size() > 0) {
                ezv a = ezv.a();
                if (arrayList4.contains(a.f)) {
                    a.f = null;
                    a.d.c("APP_ALIAS");
                }
            }
            ezv.a().a(ext.d, i);
            eys eys = new eys(this, i, arrayList4, arrayList6, str);
            fbf.b(eys);
        }
    }
}
