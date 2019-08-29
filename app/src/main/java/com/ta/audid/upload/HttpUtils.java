package com.ta.audid.upload;

public class HttpUtils {
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    private static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 10000;
    private static final long TIME_SCOPE = 1800000;
    private static UtdidHostnameVerifier mUtdidHostnameVerifier;
    private static UtdidSslSocketFactory mUtdidSslSocketFactory;

    static {
        System.setProperty("http.keepAlive", "true");
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0248 A[SYNTHETIC, Splitter:B:114:0x0248] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x025c A[SYNTHETIC, Splitter:B:122:0x025c] */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x0294 A[SYNTHETIC, Splitter:B:141:0x0294] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0219 A[Catch:{ Exception -> 0x0237 }, LOOP:1: B:96:0x0213->B:98:0x0219, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x021d A[EDGE_INSN: B:99:0x021d->B:100:? ?: BREAK  
    EDGE_INSN: B:99:0x021d->B:100:? ?: BREAK  , SYNTHETIC, Splitter:B:99:0x021d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.ta.audid.upload.HttpResponse sendRequest(java.lang.String r17, java.lang.String r18, boolean r19) {
        /*
            r1 = r18
            com.ta.audid.upload.HttpResponse r2 = new com.ta.audid.upload.HttpResponse
            r2.<init>()
            boolean r3 = android.text.TextUtils.isEmpty(r17)
            if (r3 == 0) goto L_0x000e
            return r2
        L_0x000e:
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            r5 = r17
            r4.<init>(r5)     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            java.lang.String r5 = r4.getHost()     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            if (r5 == 0) goto L_0x0021
            return r2
        L_0x0021:
            java.net.URLConnection r5 = r4.openConnection()     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            boolean r6 = r5 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            if (r6 == 0) goto L_0x0059
            com.ta.audid.upload.UtdidSslSocketFactory r6 = mUtdidSslSocketFactory     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            if (r6 != 0) goto L_0x003a
            com.ta.audid.upload.UtdidSslSocketFactory r6 = new com.ta.audid.upload.UtdidSslSocketFactory     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            java.lang.String r7 = r4.getHost()     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            r6.<init>(r7)     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            mUtdidSslSocketFactory = r6     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
        L_0x003a:
            com.ta.audid.upload.UtdidHostnameVerifier r6 = mUtdidHostnameVerifier     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            if (r6 != 0) goto L_0x0049
            com.ta.audid.upload.UtdidHostnameVerifier r6 = new com.ta.audid.upload.UtdidHostnameVerifier     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            java.lang.String r4 = r4.getHost()     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            r6.<init>(r4)     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            mUtdidHostnameVerifier = r6     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
        L_0x0049:
            r4 = r5
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            com.ta.audid.upload.UtdidSslSocketFactory r6 = mUtdidSslSocketFactory     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            r4.setSSLSocketFactory(r6)     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            r4 = r5
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            com.ta.audid.upload.UtdidHostnameVerifier r6 = mUtdidHostnameVerifier     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
            r4.setHostnameVerifier(r6)     // Catch:{ MalformedURLException -> 0x02c2, IOException -> 0x02b8, Throwable -> 0x02ae }
        L_0x0059:
            if (r5 == 0) goto L_0x02ad
            r4 = 1
            r5.setDoInput(r4)
            if (r19 == 0) goto L_0x0074
            r5.setDoOutput(r4)
            java.lang.String r6 = "POST"
            r5.setRequestMethod(r6)     // Catch:{ ProtocolException -> 0x006a }
            goto L_0x0079
        L_0x006a:
            r0 = move-exception
            r1 = r0
            java.lang.String r4 = ""
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.ta.audid.utils.UtdidLogger.e(r4, r1, r3)
            return r2
        L_0x0074:
            java.lang.String r6 = "GET"
            r5.setRequestMethod(r6)     // Catch:{ ProtocolException -> 0x02a3 }
        L_0x0079:
            r5.setUseCaches(r3)
            r6 = 10000(0x2710, float:1.4013E-41)
            r5.setConnectTimeout(r6)
            r5.setReadTimeout(r6)
            r5.setInstanceFollowRedirects(r4)
            java.lang.String r6 = "Content-Type"
            java.lang.String r7 = "application/x-www-form-urlencoded"
            r5.setRequestProperty(r6, r7)
            java.lang.String r6 = "Charset"
            java.lang.String r7 = "UTF-8"
            r5.setRequestProperty(r6, r7)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            com.ta.audid.Variables r7 = com.ta.audid.Variables.getInstance()
            java.lang.String r7 = r7.getAppkey()
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x00b1
            java.lang.String r8 = "x-audid-appkey"
            r5.setRequestProperty(r8, r7)
            r6.append(r7)
        L_0x00b1:
            com.ta.audid.Variables r7 = com.ta.audid.Variables.getInstance()
            android.content.Context r7 = r7.getContext()
            java.lang.String r7 = r7.getPackageName()
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x00d2
            java.lang.String r8 = "x-audid-appname"
            java.lang.String r9 = "UTF-8"
            java.lang.String r9 = java.net.URLEncoder.encode(r7, r9)     // Catch:{ Exception -> 0x00d2 }
            r5.setRequestProperty(r8, r9)     // Catch:{ Exception -> 0x00d2 }
            r6.append(r7)     // Catch:{ Exception -> 0x00d2 }
        L_0x00d2:
            java.lang.String r7 = "x-audid-sdk"
            java.lang.String r8 = "2.0.6.7"
            r5.setRequestProperty(r7, r8)
            java.lang.String r7 = "2.0.6.7"
            r6.append(r7)
            com.ta.audid.Variables r7 = com.ta.audid.Variables.getInstance()
            java.lang.String r7 = r7.getCurrentTimeMillisString()
            java.lang.String r8 = "x-audid-timestamp"
            r5.setRequestProperty(r8, r7)
            java.lang.String r8 = ""
            java.lang.Object[] r9 = new java.lang.Object[r4]
            java.lang.String r10 = "timestamp:"
            java.lang.String r11 = java.lang.String.valueOf(r7)
            java.lang.String r10 = r10.concat(r11)
            r9[r3] = r10
            com.ta.audid.utils.UtdidLogger.d(r8, r9)
            r6.append(r7)
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            java.lang.String r6 = com.ta.audid.utils.MD5Utils.getHmacMd5Hex(r6)
            java.lang.String r7 = "signature"
            byte[] r6 = r6.getBytes()
            r8 = 2
            java.lang.String r6 = com.ta.utdid2.android.utils.Base64.encodeToString(r6, r8)
            r5.setRequestProperty(r7, r6)
            long r6 = java.lang.System.currentTimeMillis()
            r8 = 0
            r5.connect()     // Catch:{ Throwable -> 0x026e }
            if (r1 == 0) goto L_0x0147
            int r9 = r18.length()     // Catch:{ Throwable -> 0x026e }
            if (r9 <= 0) goto L_0x0147
            java.io.DataOutputStream r9 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x026e }
            java.io.OutputStream r10 = r5.getOutputStream()     // Catch:{ Throwable -> 0x026e }
            r9.<init>(r10)     // Catch:{ Throwable -> 0x026e }
            r9.writeBytes(r1)     // Catch:{ Throwable -> 0x0142, all -> 0x013d }
            r9.flush()     // Catch:{ Throwable -> 0x0142, all -> 0x013d }
            goto L_0x0148
        L_0x013d:
            r0 = move-exception
            r1 = r0
            r8 = r9
            goto L_0x0292
        L_0x0142:
            r0 = move-exception
            r1 = r0
            r8 = r9
            goto L_0x0270
        L_0x0147:
            r9 = r8
        L_0x0148:
            if (r9 == 0) goto L_0x0158
            r9.close()     // Catch:{ IOException -> 0x014e }
            goto L_0x0158
        L_0x014e:
            r0 = move-exception
            java.lang.String r1 = ""
            java.lang.Object[] r9 = new java.lang.Object[r4]
            r9[r3] = r0
            com.ta.audid.utils.UtdidLogger.d(r1, r9)
        L_0x0158:
            int r1 = r5.getResponseCode()     // Catch:{ Exception -> 0x0167 }
            r2.httpResponseCode = r1     // Catch:{ Exception -> 0x0167 }
            java.lang.String r1 = "signature"
            java.lang.String r1 = r5.getHeaderField(r1)     // Catch:{ Exception -> 0x0167 }
            r2.signature = r1     // Catch:{ Exception -> 0x0167 }
            goto L_0x0172
        L_0x0167:
            r0 = move-exception
            r1 = r0
            java.lang.String r9 = ""
            java.lang.Object[] r10 = new java.lang.Object[r4]
            r10[r3] = r1
            com.ta.audid.utils.UtdidLogger.d(r9, r10)
        L_0x0172:
            java.lang.String r1 = "x-audid-timestamp"
            java.lang.String r1 = r5.getHeaderField(r1)     // Catch:{ Exception -> 0x01c4 }
            long r9 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x01c4 }
            r2.timestamp = r9     // Catch:{ Exception -> 0x01c4 }
            java.lang.String r1 = ""
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x01c4 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c4 }
            java.lang.String r11 = "repsonse.timestamp:"
            r10.<init>(r11)     // Catch:{ Exception -> 0x01c4 }
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01c4 }
            r10.append(r11)     // Catch:{ Exception -> 0x01c4 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x01c4 }
            r9[r3] = r10     // Catch:{ Exception -> 0x01c4 }
            com.ta.audid.utils.UtdidLogger.d(r1, r9)     // Catch:{ Exception -> 0x01c4 }
            com.ta.audid.Variables r1 = com.ta.audid.Variables.getInstance()     // Catch:{ Exception -> 0x01c4 }
            long r9 = r1.getCurrentTimeMillis()     // Catch:{ Exception -> 0x01c4 }
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01c4 }
            r13 = 0
            int r1 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r1 <= 0) goto L_0x01c4
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01c4 }
            r13 = 1800000(0x1b7740, double:8.89318E-318)
            long r15 = r9 + r13
            int r1 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r1 > 0) goto L_0x01bb
            long r11 = r2.timestamp     // Catch:{ Exception -> 0x01c4 }
            r1 = 0
            long r9 = r9 - r13
            int r1 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x01c4
        L_0x01bb:
            com.ta.audid.Variables r1 = com.ta.audid.Variables.getInstance()     // Catch:{ Exception -> 0x01c4 }
            long r9 = r2.timestamp     // Catch:{ Exception -> 0x01c4 }
            r1.setSystemTime(r9)     // Catch:{ Exception -> 0x01c4 }
        L_0x01c4:
            long r9 = java.lang.System.currentTimeMillis()
            long r9 = r9 - r6
            r2.rt = r9
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            r6 = -1
            r7 = 2048(0x800, float:2.87E-42)
            java.io.DataInputStream r9 = new java.io.DataInputStream     // Catch:{ IOException -> 0x01fc }
            java.io.InputStream r10 = r5.getInputStream()     // Catch:{ IOException -> 0x01fc }
            r9.<init>(r10)     // Catch:{ IOException -> 0x01fc }
            byte[] r8 = new byte[r7]     // Catch:{ IOException -> 0x01f7 }
        L_0x01de:
            int r10 = r9.read(r8, r3, r7)     // Catch:{ IOException -> 0x01f7 }
            if (r10 == r6) goto L_0x01e8
            r1.write(r8, r3, r10)     // Catch:{ IOException -> 0x01f7 }
            goto L_0x01de
        L_0x01e8:
            r9.close()     // Catch:{ Exception -> 0x01ec }
            goto L_0x0229
        L_0x01ec:
            r0 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r0
        L_0x01f3:
            com.ta.audid.utils.UtdidLogger.d(r5, r4)
            goto L_0x0229
        L_0x01f7:
            r0 = move-exception
            goto L_0x01fe
        L_0x01f9:
            r0 = move-exception
            r1 = r0
            goto L_0x025a
        L_0x01fc:
            r0 = move-exception
            r9 = r8
        L_0x01fe:
            r8 = r0
            java.lang.String r10 = ""
            java.lang.Object[] r11 = new java.lang.Object[r4]     // Catch:{ all -> 0x0257 }
            r11[r3] = r8     // Catch:{ all -> 0x0257 }
            com.ta.audid.utils.UtdidLogger.d(r10, r11)     // Catch:{ all -> 0x0257 }
            java.io.DataInputStream r8 = new java.io.DataInputStream     // Catch:{ Exception -> 0x023a }
            java.io.InputStream r5 = r5.getErrorStream()     // Catch:{ Exception -> 0x023a }
            r8.<init>(r5)     // Catch:{ Exception -> 0x023a }
            byte[] r5 = new byte[r7]     // Catch:{ Exception -> 0x0237 }
        L_0x0213:
            int r9 = r8.read(r5, r3, r7)     // Catch:{ Exception -> 0x0237 }
            if (r9 == r6) goto L_0x021d
            r1.write(r5, r3, r9)     // Catch:{ Exception -> 0x0237 }
            goto L_0x0213
        L_0x021d:
            r8.close()     // Catch:{ Exception -> 0x0221 }
            goto L_0x0229
        L_0x0221:
            r0 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r0
            goto L_0x01f3
        L_0x0229:
            int r3 = r1.size()
            if (r3 <= 0) goto L_0x02ad
            byte[] r1 = r1.toByteArray()
            r2.data = r1
            goto L_0x02ad
        L_0x0237:
            r0 = move-exception
            r1 = r0
            goto L_0x023d
        L_0x023a:
            r0 = move-exception
            r1 = r0
            r8 = r9
        L_0x023d:
            java.lang.String r5 = ""
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x01f9 }
            r6[r3] = r1     // Catch:{ all -> 0x01f9 }
            com.ta.audid.utils.UtdidLogger.d(r5, r6)     // Catch:{ all -> 0x01f9 }
            if (r8 == 0) goto L_0x0256
            r8.close()     // Catch:{ Exception -> 0x024c }
            goto L_0x0256
        L_0x024c:
            r0 = move-exception
            java.lang.String r1 = ""
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r0
            com.ta.audid.utils.UtdidLogger.d(r1, r4)
        L_0x0256:
            return r2
        L_0x0257:
            r0 = move-exception
            r1 = r0
            r8 = r9
        L_0x025a:
            if (r8 == 0) goto L_0x026a
            r8.close()     // Catch:{ Exception -> 0x0260 }
            goto L_0x026a
        L_0x0260:
            r0 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r0
            com.ta.audid.utils.UtdidLogger.d(r2, r4)
        L_0x026a:
            throw r1
        L_0x026b:
            r0 = move-exception
            r1 = r0
            goto L_0x0292
        L_0x026e:
            r0 = move-exception
            r1 = r0
        L_0x0270:
            java.lang.String r5 = ""
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ all -> 0x026b }
            r9[r3] = r1     // Catch:{ all -> 0x026b }
            com.ta.audid.utils.UtdidLogger.d(r5, r9)     // Catch:{ all -> 0x026b }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x026b }
            r1 = 0
            long r9 = r9 - r6
            r2.rt = r9     // Catch:{ all -> 0x026b }
            if (r8 == 0) goto L_0x0291
            r8.close()     // Catch:{ IOException -> 0x0287 }
            goto L_0x0291
        L_0x0287:
            r0 = move-exception
            java.lang.String r1 = ""
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r0
            com.ta.audid.utils.UtdidLogger.d(r1, r4)
        L_0x0291:
            return r2
        L_0x0292:
            if (r8 == 0) goto L_0x02a2
            r8.close()     // Catch:{ IOException -> 0x0298 }
            goto L_0x02a2
        L_0x0298:
            r0 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r3] = r0
            com.ta.audid.utils.UtdidLogger.d(r2, r4)
        L_0x02a2:
            throw r1
        L_0x02a3:
            r0 = move-exception
            r1 = r0
            java.lang.String r4 = ""
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.ta.audid.utils.UtdidLogger.e(r4, r1, r3)
            return r2
        L_0x02ad:
            return r2
        L_0x02ae:
            r0 = move-exception
            r1 = r0
            java.lang.String r4 = ""
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.ta.audid.utils.UtdidLogger.e(r4, r1, r3)
            return r2
        L_0x02b8:
            r0 = move-exception
            r1 = r0
            java.lang.String r4 = ""
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.ta.audid.utils.UtdidLogger.e(r4, r1, r3)
            return r2
        L_0x02c2:
            r0 = move-exception
            r1 = r0
            java.lang.String r4 = ""
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.ta.audid.utils.UtdidLogger.e(r4, r1, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.upload.HttpUtils.sendRequest(java.lang.String, java.lang.String, boolean):com.ta.audid.upload.HttpResponse");
    }
}
