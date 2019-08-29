package com.amap.location.protocol.d;

import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.amap.location.common.model.AmapLoc;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/* compiled from: AmapLocationResponse */
public class a extends bpk<AmapLoc> {
    private String a;

    public InputStream getBodyInputStream() {
        String header = getHeader(TransportConstants.KEY_X_CONTENT_ENCODING);
        if (header != null && header.contains("gzip")) {
            try {
                return new GZIPInputStream(super.getBodyInputStream());
            } catch (IOException unused) {
            }
        }
        return super.getBodyInputStream();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0070 A[RETURN] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amap.location.common.model.AmapLoc parseResult() {
        /*
            r5 = this;
            byte[] r0 = r5.getResponseBodyData()
            r1 = 0
            if (r0 == 0) goto L_0x001d
            int r2 = r0.length
            if (r2 > 0) goto L_0x000b
            goto L_0x001d
        L_0x000b:
            r2 = -1
            byte[] r0 = com.amap.location.security.Core.xxt(r0, r2)
            if (r0 == 0) goto L_0x0025
            int r2 = r0.length
            if (r2 <= 0) goto L_0x0025
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0025 }
            java.lang.String r3 = "UTF-8"
            r2.<init>(r0, r3)     // Catch:{ Exception -> 0x0025 }
            goto L_0x0026
        L_0x001d:
            java.lang.String r0 = "amaplocres"
            java.lang.String r2 = "response is null"
            com.amap.location.common.d.a.c(r0, r2)
        L_0x0025:
            r2 = r1
        L_0x0026:
            boolean r0 = android.text.TextUtils.isEmpty(r2)
            if (r0 != 0) goto L_0x0066
            java.lang.String r0 = "{"
            boolean r0 = r2.startsWith(r0)
            if (r0 == 0) goto L_0x005d
            java.lang.String r0 = "amaplocres"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "aos para error:"
            r3.<init>(r4)
            bph r4 = r5.getRequest()
            java.lang.String r4 = r4.getUrl()
            r3.append(r4)
            java.lang.String r4 = ",response:"
            r3.append(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.lang.String r2 = com.amap.location.common.d.a.a(r2)
            com.amap.location.common.d.a.c(r0, r2)
            goto L_0x0066
        L_0x005d:
            com.amap.location.protocol.e.d r0 = new com.amap.location.protocol.e.d
            r0.<init>()
            com.amap.location.common.model.AmapLoc r1 = com.amap.location.protocol.e.d.a(r2)
        L_0x0066:
            if (r1 != 0) goto L_0x0070
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "error response"
            r0.<init>(r1)
            throw r0
        L_0x0070:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.protocol.d.a.parseResult():com.amap.location.common.model.AmapLoc");
    }

    public String b() {
        return this.a;
    }

    public void a(String str) {
        this.a = str;
    }
}
