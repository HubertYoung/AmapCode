package defpackage;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* renamed from: bog reason: default package */
/* compiled from: HurlResponseImpl */
public final class bog implements bpa {
    private int a;
    private Map<String, List<String>> b;
    private bpq c;

    public bog(bov bov) {
        this.a = bov.c;
        this.b = bov.b;
        this.c = new bpq(bov.a);
    }

    public final int getStatusCode() {
        return this.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long getContentLength() {
        /*
            r4 = this;
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r0 = r4.b
            java.lang.String r1 = "Content-Length"
            java.lang.String r0 = defpackage.bps.b(r0, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = -1
            if (r1 != 0) goto L_0x0015
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x0015 }
            goto L_0x0016
        L_0x0015:
            r0 = r2
        L_0x0016:
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x001e
            bpq r0 = r4.c
            long r0 = r0.a
        L_0x001e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bog.getContentLength():long");
    }

    public final String getHeader(String str) {
        return bps.b(this.b, str);
    }

    public final Map<String, List<String>> getHeaders() {
        return this.b;
    }

    public final InputStream getBodyInputStream() {
        return this.c;
    }
}
