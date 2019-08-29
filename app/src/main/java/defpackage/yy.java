package defpackage;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: yy reason: default package */
/* compiled from: CookieFilter */
public final class yy implements bpd {
    public final bph a(bph bph) {
        if (bph != null && !bph.getHeaders().containsKey("Cookie")) {
            bph.addHeader("Cookie", abj.a().b());
        }
        return bph;
    }

    public final bpk a(bpk bpk) {
        if (bpk == null) {
            return bpk;
        }
        Map<String, List<String>> headers = bpk.getHeaders();
        if (headers == null || headers.isEmpty()) {
            return bpk;
        }
        for (Entry next : headers.entrySet()) {
            if (!(next == null || next.getKey() == null || !"Set-Cookie".equalsIgnoreCase((String) next.getKey()))) {
                List<String> list = (List) next.getValue();
                if (list != null && !list.isEmpty()) {
                    for (String a : list) {
                        abj.a().a(a);
                    }
                }
            }
        }
        return bpk;
    }
}
