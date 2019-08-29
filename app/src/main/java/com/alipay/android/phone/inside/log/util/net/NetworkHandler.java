package com.alipay.android.phone.inside.log.util.net;

import org.apache.http.client.HttpClient;

public class NetworkHandler {
    private static HttpClient a;

    /* JADX WARNING: Can't wrap try/catch for region: R(10:1|2|3|4|(3:5|6|(1:8)(1:39))|9|(2:11|12)|13|14|15) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0027 */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0040 A[SYNTHETIC, Splitter:B:31:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0045 A[SYNTHETIC, Splitter:B:35:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] a(org.apache.http.HttpResponse r5) throws java.lang.IllegalStateException, java.io.IOException {
        /*
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            r1 = 0
            org.apache.http.HttpEntity r5 = r5.getEntity()     // Catch:{ Exception -> 0x0037, all -> 0x0033 }
            java.io.InputStream r5 = r5.getContent()     // Catch:{ Exception -> 0x0037, all -> 0x0033 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            r2.<init>()     // Catch:{ Exception -> 0x0030, all -> 0x002d }
        L_0x0012:
            int r3 = r5.read(r0)     // Catch:{ Exception -> 0x002b }
            r4 = -1
            if (r3 == r4) goto L_0x001e
            r4 = 0
            r2.write(r0, r4, r3)     // Catch:{ Exception -> 0x002b }
            goto L_0x0012
        L_0x001e:
            byte[] r0 = r2.toByteArray()     // Catch:{ Exception -> 0x002b }
            if (r5 == 0) goto L_0x0027
            r5.close()     // Catch:{ Exception -> 0x0027 }
        L_0x0027:
            r2.close()     // Catch:{ Exception -> 0x002a }
        L_0x002a:
            return r0
        L_0x002b:
            r0 = move-exception
            goto L_0x003a
        L_0x002d:
            r0 = move-exception
            r2 = r1
            goto L_0x003e
        L_0x0030:
            r0 = move-exception
            r2 = r1
            goto L_0x003a
        L_0x0033:
            r0 = move-exception
            r5 = r1
            r2 = r5
            goto L_0x003e
        L_0x0037:
            r0 = move-exception
            r5 = r1
            r2 = r5
        L_0x003a:
            a = r1     // Catch:{ all -> 0x003d }
            throw r0     // Catch:{ all -> 0x003d }
        L_0x003d:
            r0 = move-exception
        L_0x003e:
            if (r5 == 0) goto L_0x0043
            r5.close()     // Catch:{ Exception -> 0x0043 }
        L_0x0043:
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0048:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.log.util.net.NetworkHandler.a(org.apache.http.HttpResponse):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002b A[Catch:{ Exception -> 0x0022 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(java.lang.String r1, byte[] r2, java.lang.String r3, java.util.List<org.apache.http.Header> r4) throws org.apache.http.client.ClientProtocolException, java.io.IOException {
        /*
            if (r2 == 0) goto L_0x0024
            int r0 = r2.length     // Catch:{ Exception -> 0x0022 }
            if (r0 != 0) goto L_0x0006
            goto L_0x0024
        L_0x0006:
            org.apache.http.client.methods.HttpPost r0 = new org.apache.http.client.methods.HttpPost     // Catch:{ Exception -> 0x0022 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0022 }
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0022 }
            if (r1 == 0) goto L_0x0013
            java.lang.String r3 = "application/octet-stream;binary/octet-stream"
        L_0x0013:
            org.apache.http.entity.ByteArrayEntity r1 = new org.apache.http.entity.ByteArrayEntity     // Catch:{ Exception -> 0x0022 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0022 }
            r1.setContentType(r3)     // Catch:{ Exception -> 0x0022 }
            r2 = r0
            org.apache.http.client.methods.HttpPost r2 = (org.apache.http.client.methods.HttpPost) r2     // Catch:{ Exception -> 0x0022 }
            r2.setEntity(r1)     // Catch:{ Exception -> 0x0022 }
            goto L_0x0029
        L_0x0022:
            r1 = move-exception
            goto L_0x004f
        L_0x0024:
            org.apache.http.client.methods.HttpGet r0 = new org.apache.http.client.methods.HttpGet     // Catch:{ Exception -> 0x0022 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0022 }
        L_0x0029:
            if (r4 == 0) goto L_0x003f
            java.util.Iterator r1 = r4.iterator()     // Catch:{ Exception -> 0x0022 }
        L_0x002f:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x0022 }
            if (r2 == 0) goto L_0x003f
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x0022 }
            org.apache.http.Header r2 = (org.apache.http.Header) r2     // Catch:{ Exception -> 0x0022 }
            r0.addHeader(r2)     // Catch:{ Exception -> 0x0022 }
            goto L_0x002f
        L_0x003f:
            org.apache.http.impl.client.DefaultHttpClient r1 = new org.apache.http.impl.client.DefaultHttpClient     // Catch:{ Exception -> 0x0022 }
            r1.<init>()     // Catch:{ Exception -> 0x0022 }
            a = r1     // Catch:{ Exception -> 0x0022 }
            org.apache.http.HttpResponse r1 = r1.execute(r0)     // Catch:{ Exception -> 0x0022 }
            byte[] r1 = a(r1)     // Catch:{ Exception -> 0x0022 }
            return r1
        L_0x004f:
            r2 = 0
            a = r2
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.log.util.net.NetworkHandler.a(java.lang.String, byte[], java.lang.String, java.util.List):byte[]");
    }
}
