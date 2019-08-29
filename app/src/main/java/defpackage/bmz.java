package defpackage;

/* renamed from: bmz reason: default package */
/* compiled from: HttpEngine */
public final class bmz {
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0081 A[Catch:{ all -> 0x0078 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086 A[SYNTHETIC, Splitter:B:33:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008d A[SYNTHETIC, Splitter:B:39:0x008d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r4, java.io.File r5, defpackage.bmy r6) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x0091
            if (r5 == 0) goto L_0x0091
            boolean r0 = r5.exists()
            if (r0 != 0) goto L_0x0010
            goto L_0x0091
        L_0x0010:
            r0 = 500(0x1f4, float:7.0E-43)
            byte[] r0 = new byte[r0]
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x007b }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x007b }
            java.net.URLConnection r4 = r2.openConnection()     // Catch:{ Throwable -> 0x007b }
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ Throwable -> 0x007b }
            java.lang.String r1 = "POST"
            r4.setRequestMethod(r1)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            java.lang.String r1 = "Content-Type"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            java.lang.String r3 = "multipart/form-data;file="
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            java.lang.String r3 = r5.getName()     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r2.append(r3)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r4.setRequestProperty(r1, r2)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r1 = 1
            r4.setDoInput(r1)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r4.setDoOutput(r1)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r1 = 5000(0x1388, float:7.006E-42)
            r4.setConnectTimeout(r1)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r4.setReadTimeout(r1)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            java.io.OutputStream r5 = r4.getOutputStream()     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
        L_0x0054:
            int r2 = r1.read(r0)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            if (r2 < 0) goto L_0x005f
            r3 = 0
            r5.write(r0, r3, r2)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            goto L_0x0054
        L_0x005f:
            r5.flush()     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r1.close()     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            r5.close()     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
            if (r6 == 0) goto L_0x006d
            r6.a(r4)     // Catch:{ Throwable -> 0x0075, all -> 0x0073 }
        L_0x006d:
            if (r4 == 0) goto L_0x008a
            r4.disconnect()     // Catch:{ Throwable -> 0x0072 }
        L_0x0072:
            return
        L_0x0073:
            r5 = move-exception
            goto L_0x008b
        L_0x0075:
            r5 = move-exception
            r1 = r4
            goto L_0x007c
        L_0x0078:
            r5 = move-exception
            r4 = r1
            goto L_0x008b
        L_0x007b:
            r5 = move-exception
        L_0x007c:
            r5.printStackTrace()     // Catch:{ all -> 0x0078 }
            if (r6 == 0) goto L_0x0084
            r6.a(r5)     // Catch:{ all -> 0x0078 }
        L_0x0084:
            if (r1 == 0) goto L_0x008a
            r1.disconnect()     // Catch:{ Throwable -> 0x0089 }
        L_0x0089:
            return
        L_0x008a:
            return
        L_0x008b:
            if (r4 == 0) goto L_0x0090
            r4.disconnect()     // Catch:{ Throwable -> 0x0090 }
        L_0x0090:
            throw r5
        L_0x0091:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bmz.a(java.lang.String, java.io.File, bmy):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0071 A[SYNTHETIC, Splitter:B:25:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0078 A[SYNTHETIC, Splitter:B:31:0x0078] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(java.lang.String r3, java.io.File r4, defpackage.bmy r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x007c
            if (r4 == 0) goto L_0x007c
            boolean r0 = r4.exists()
            if (r0 != 0) goto L_0x000f
            goto L_0x007c
        L_0x000f:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            bna r1 = new bna
            java.lang.String r2 = "file"
            r1.<init>(r2, r4)
            r0.add(r1)
            bnb r4 = new bnb
            java.lang.String r1 = "UTF-8"
            r4.<init>(r0, r1)
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ Throwable -> 0x006b }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x006b }
            java.net.URLConnection r3 = r1.openConnection()     // Catch:{ Throwable -> 0x006b }
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3     // Catch:{ Throwable -> 0x006b }
            r0 = 1
            r3.setDoOutput(r0)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            r0 = 5000(0x1388, float:7.006E-42)
            r3.setConnectTimeout(r0)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            r3.setReadTimeout(r0)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            java.lang.String r0 = "POST"
            r3.setRequestMethod(r0)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            java.lang.String r0 = "Charset"
            java.lang.String r1 = "UTF-8"
            r3.setRequestProperty(r0, r1)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            java.lang.String r0 = "Content-Type"
            java.lang.String r1 = r4.a     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            r3.setRequestProperty(r0, r1)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            java.io.OutputStream r0 = r3.getOutputStream()     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            r4.a(r0)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            r0.close()     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            r5.a(r3)     // Catch:{ Throwable -> 0x0065, all -> 0x0063 }
            if (r3 == 0) goto L_0x0075
            r3.disconnect()     // Catch:{ Throwable -> 0x0062 }
        L_0x0062:
            return
        L_0x0063:
            r4 = move-exception
            goto L_0x0076
        L_0x0065:
            r4 = move-exception
            r0 = r3
            goto L_0x006c
        L_0x0068:
            r4 = move-exception
            r3 = r0
            goto L_0x0076
        L_0x006b:
            r4 = move-exception
        L_0x006c:
            r5.a(r4)     // Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x0075
            r0.disconnect()     // Catch:{ Throwable -> 0x0074 }
        L_0x0074:
            return
        L_0x0075:
            return
        L_0x0076:
            if (r3 == 0) goto L_0x007b
            r3.disconnect()     // Catch:{ Throwable -> 0x007b }
        L_0x007b:
            throw r4
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bmz.b(java.lang.String, java.io.File, bmy):void");
    }
}
