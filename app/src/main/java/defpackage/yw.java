package defpackage;

import android.text.TextUtils;

/* renamed from: yw reason: default package */
/* compiled from: AosErrorFilter */
public final class yw implements bpd {
    public final bph a(bph bph) {
        return bph;
    }

    public final bpk a(bpk bpk) {
        Object obj;
        a aVar;
        if (bpk == null) {
            return bpk;
        }
        yx a = yx.a();
        String header = bpk.getHeader("aos-errorcode");
        if (!TextUtils.isEmpty(header)) {
            String str = null;
            try {
                obj = Integer.valueOf(Integer.parseInt(header.trim()));
            } catch (Exception unused) {
                bpv.e("network.AosErrorProcessor", "parse error code fail, code:".concat(String.valueOf(header)));
                obj = null;
            }
            if (obj != null) {
                bph request = bpk.getRequest();
                if (request != null) {
                    str = request.getUrl();
                }
                StringBuilder sb = new StringBuilder("process error, code:");
                sb.append(obj);
                sb.append(", requestUrl: ");
                if (str == null) {
                    str = "null";
                }
                sb.append(str);
                bpv.c("network.AosErrorProcessor", sb.toString());
                if (a.a != null) {
                    synchronized (yx.class) {
                        aVar = a.a.get(obj);
                    }
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            }
        }
        return bpk;
    }
}
