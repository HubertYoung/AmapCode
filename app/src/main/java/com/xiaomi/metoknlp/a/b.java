package com.xiaomi.metoknlp.a;

public class b {
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0097, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0098, code lost:
        r5 = r7;
        r7 = r6;
        r6 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a3, code lost:
        if (r1 != null) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a5, code lost:
        r1.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a8, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00b4, code lost:
        r1.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00be, code lost:
        if (r1 == null) goto L_0x00c1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0097 A[ExcHandler: all (r6v6 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:24:0x007c] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00af A[SYNTHETIC, Splitter:B:49:0x00af] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b4 A[Catch:{ Exception -> 0x00b7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00bb A[SYNTHETIC, Splitter:B:59:0x00bb] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r6, java.util.Map r7) {
        /*
            java.lang.String r0 = ""
            boolean r1 = android.text.TextUtils.isEmpty(r6)
            if (r1 == 0) goto L_0x0009
            return r0
        L_0x0009:
            java.net.URL r1 = new java.net.URL     // Catch:{ MalformedURLException -> 0x00c1 }
            r1.<init>(r6)     // Catch:{ MalformedURLException -> 0x00c1 }
            r6 = 0
            java.lang.String r2 = r1.getProtocol()     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
            java.lang.String r3 = "https"
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
            if (r2 == 0) goto L_0x0026
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
            javax.net.ssl.HttpsURLConnection r1 = (javax.net.ssl.HttpsURLConnection) r1     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
            goto L_0x002c
        L_0x0026:
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Exception -> 0x00b8, all -> 0x00ab }
        L_0x002c:
            r2 = 30000(0x7530, float:4.2039E-41)
            r1.setConnectTimeout(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            r1.setReadTimeout(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.lang.String r2 = "GET"
            r1.setRequestMethod(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            r2 = 0
            r1.setDoOutput(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            if (r7 == 0) goto L_0x0063
            int r2 = r7.size()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            if (r2 <= 0) goto L_0x0063
            java.util.Set r2 = r7.keySet()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
        L_0x004d:
            boolean r3 = r2.hasNext()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            if (r3 == 0) goto L_0x0063
            java.lang.Object r3 = r2.next()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.lang.Object r4 = r7.get(r3)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            r1.addRequestProperty(r3, r4)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            goto L_0x004d
        L_0x0063:
            r1.connect()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            int r7 = r1.getResponseCode()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r7 != r2) goto L_0x009e
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.io.InputStream r3 = r1.getInputStream()     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            r7.<init>(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00a9 }
            java.lang.StringBuffer r6 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x009c, all -> 0x0097 }
            r6.<init>()     // Catch:{ Exception -> 0x009c, all -> 0x0097 }
        L_0x0081:
            java.lang.String r2 = r7.readLine()     // Catch:{ Exception -> 0x009c, all -> 0x0097 }
            if (r2 == 0) goto L_0x008b
            r6.append(r2)     // Catch:{ Exception -> 0x009c, all -> 0x0097 }
            goto L_0x0081
        L_0x008b:
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x009c, all -> 0x0097 }
            r7.close()     // Catch:{ Exception -> 0x0095, all -> 0x0097 }
            r0 = r6
            r6 = r7
            goto L_0x009e
        L_0x0095:
            r0 = r6
            goto L_0x009c
        L_0x0097:
            r6 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x00ad
        L_0x009c:
            r6 = r7
            goto L_0x00b9
        L_0x009e:
            if (r6 == 0) goto L_0x00a3
            r6.close()     // Catch:{  }
        L_0x00a3:
            if (r1 == 0) goto L_0x00c1
        L_0x00a5:
            r1.disconnect()     // Catch:{  }
            return r0
        L_0x00a9:
            r7 = move-exception
            goto L_0x00ad
        L_0x00ab:
            r7 = move-exception
            r1 = r6
        L_0x00ad:
            if (r6 == 0) goto L_0x00b2
            r6.close()     // Catch:{ Exception -> 0x00b7 }
        L_0x00b2:
            if (r1 == 0) goto L_0x00b7
            r1.disconnect()     // Catch:{ Exception -> 0x00b7 }
        L_0x00b7:
            throw r7
        L_0x00b8:
            r1 = r6
        L_0x00b9:
            if (r6 == 0) goto L_0x00be
            r6.close()     // Catch:{  }
        L_0x00be:
            if (r1 == 0) goto L_0x00c1
            goto L_0x00a5
        L_0x00c1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.metoknlp.a.b.a(java.lang.String, java.util.Map):java.lang.String");
    }
}
