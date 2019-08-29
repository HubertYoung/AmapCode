package com.alipay.mobile.nebula.appcenter.openapi;

import java.util.HashMap;

public class H5AppHttpRequest {
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_UA = "User-Agent";
    private static final String TAG = "H5AppHttpRequest";
    /* access modifiers changed from: private */
    public final String body;
    /* access modifiers changed from: private */
    public final HashMap<String, String> headers;
    /* access modifiers changed from: private */
    public final String method;
    /* access modifiers changed from: private */
    public final String url;

    public static class Builder {
        /* access modifiers changed from: private */
        public String body;
        /* access modifiers changed from: private */
        public HashMap<String, String> headers;
        /* access modifiers changed from: private */
        public String method;
        /* access modifiers changed from: private */
        public String url;

        public Builder() {
            this.headers = null;
            this.method = "POST";
            this.headers = new HashMap<>();
        }

        Builder(H5AppHttpRequest request) {
            this.headers = null;
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.headers = request.headers;
        }

        public Builder url(String url2) {
            this.url = url2;
            return this;
        }

        public Builder body(String body2) {
            this.body = body2;
            return this;
        }

        public Builder method(String method2) {
            this.method = method2;
            return this;
        }

        public Builder addHeader(String key, String value) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(key, value);
            return this;
        }

        public H5AppHttpRequest build() {
            return new H5AppHttpRequest(this);
        }
    }

    private H5AppHttpRequest(Builder builder) {
        this.url = builder.url;
        this.body = builder.body;
        this.method = builder.method;
        this.headers = builder.headers;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0083 A[SYNTHETIC, Splitter:B:13:0x0083] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0088 A[Catch:{ Throwable -> 0x0129 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0143 A[SYNTHETIC, Splitter:B:47:0x0143] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0148 A[Catch:{ Throwable -> 0x014c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String execute() {
        /*
            r17 = this;
            r7 = 0
            r3 = 0
            java.lang.String r10 = ""
            java.net.URL r9 = new java.net.URL     // Catch:{ Throwable -> 0x0059 }
            r0 = r17
            java.lang.String r13 = r0.url     // Catch:{ Throwable -> 0x0059 }
            r9.<init>(r13)     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r13 = "H5AppHttpRequest"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r15 = "sendPost "
            r14.<init>(r15)     // Catch:{ Throwable -> 0x0059 }
            r0 = r17
            java.lang.String r15 = r0.url     // Catch:{ Throwable -> 0x0059 }
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x0059 }
            com.alipay.mobile.nebula.util.H5Log.d(r13, r14)     // Catch:{ Throwable -> 0x0059 }
            java.net.URLConnection r1 = r9.openConnection()     // Catch:{ Throwable -> 0x0059 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Throwable -> 0x0059 }
            r0 = r17
            java.util.HashMap<java.lang.String, java.lang.String> r13 = r0.headers     // Catch:{ Throwable -> 0x0059 }
            if (r13 == 0) goto L_0x008c
            r0 = r17
            java.util.HashMap<java.lang.String, java.lang.String> r13 = r0.headers     // Catch:{ Throwable -> 0x0059 }
            java.util.Set r13 = r13.entrySet()     // Catch:{ Throwable -> 0x0059 }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ Throwable -> 0x0059 }
        L_0x003d:
            boolean r14 = r13.hasNext()     // Catch:{ Throwable -> 0x0059 }
            if (r14 == 0) goto L_0x008c
            java.lang.Object r2 = r13.next()     // Catch:{ Throwable -> 0x0059 }
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2     // Catch:{ Throwable -> 0x0059 }
            java.lang.Object r5 = r2.getKey()     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Throwable -> 0x0059 }
            java.lang.Object r12 = r2.getValue()     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0059 }
            r1.setRequestProperty(r5, r12)     // Catch:{ Throwable -> 0x0059 }
            goto L_0x003d
        L_0x0059:
            r11 = move-exception
        L_0x005a:
            java.lang.String r13 = "H5AppHttpRequest"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0140 }
            java.lang.String r15 = "request post error"
            r14.<init>(r15)     // Catch:{ all -> 0x0140 }
            java.lang.StringBuilder r14 = r14.append(r11)     // Catch:{ all -> 0x0140 }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x0140 }
            com.alipay.mobile.nebula.util.H5Log.e(r13, r14)     // Catch:{ all -> 0x0140 }
            java.lang.String r13 = "H5AppHttpRequest"
            com.alipay.mobile.nebula.log.H5LogData r13 = com.alipay.mobile.nebula.log.H5LogData.seedId(r13)     // Catch:{ all -> 0x0140 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param4()     // Catch:{ all -> 0x0140 }
            java.lang.String r14 = "sendPostException"
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r14, r11)     // Catch:{ all -> 0x0140 }
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r13)     // Catch:{ all -> 0x0140 }
            if (r7 == 0) goto L_0x0086
            r7.close()     // Catch:{ Throwable -> 0x0129 }
        L_0x0086:
            if (r3 == 0) goto L_0x008b
            r3.close()     // Catch:{ Throwable -> 0x0129 }
        L_0x008b:
            return r10
        L_0x008c:
            r0 = r17
            java.util.HashMap<java.lang.String, java.lang.String> r13 = r0.headers     // Catch:{ Throwable -> 0x0059 }
            if (r13 == 0) goto L_0x009e
            r0 = r17
            java.util.HashMap<java.lang.String, java.lang.String> r13 = r0.headers     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r14 = "Accept"
            boolean r13 = r13.containsKey(r14)     // Catch:{ Throwable -> 0x0059 }
            if (r13 != 0) goto L_0x00a5
        L_0x009e:
            java.lang.String r13 = "Accept"
            java.lang.String r14 = "*/*"
            r1.setRequestProperty(r13, r14)     // Catch:{ Throwable -> 0x0059 }
        L_0x00a5:
            r0 = r17
            java.util.HashMap<java.lang.String, java.lang.String> r13 = r0.headers     // Catch:{ Throwable -> 0x0059 }
            if (r13 == 0) goto L_0x00b7
            r0 = r17
            java.util.HashMap<java.lang.String, java.lang.String> r13 = r0.headers     // Catch:{ Throwable -> 0x0059 }
            java.lang.String r14 = "Connection"
            boolean r13 = r13.containsKey(r14)     // Catch:{ Throwable -> 0x0059 }
            if (r13 != 0) goto L_0x00be
        L_0x00b7:
            java.lang.String r13 = "Connection"
            java.lang.String r14 = "Keep-Alive"
            r1.setRequestProperty(r13, r14)     // Catch:{ Throwable -> 0x0059 }
        L_0x00be:
            r0 = r17
            java.lang.String r13 = r0.method     // Catch:{ Throwable -> 0x0059 }
            r1.setRequestMethod(r13)     // Catch:{ Throwable -> 0x0059 }
            r13 = 1
            r1.setDoOutput(r13)     // Catch:{ Throwable -> 0x0059 }
            r13 = 1
            r1.setDoInput(r13)     // Catch:{ Throwable -> 0x0059 }
            java.io.PrintWriter r8 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x0059 }
            java.io.OutputStream r13 = r1.getOutputStream()     // Catch:{ Throwable -> 0x0059 }
            r8.<init>(r13)     // Catch:{ Throwable -> 0x0059 }
            r0 = r17
            java.lang.String r13 = r0.body     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            r8.print(r13)     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            r8.flush()     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            java.io.InputStreamReader r13 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            java.io.InputStream r14 = r1.getInputStream()     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            r13.<init>(r14)     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
            r4.<init>(r13)     // Catch:{ Throwable -> 0x0169, all -> 0x0162 }
        L_0x00ee:
            java.lang.String r6 = r4.readLine()     // Catch:{ Throwable -> 0x016d, all -> 0x0165 }
            if (r6 == 0) goto L_0x0106
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x016d, all -> 0x0165 }
            r13.<init>()     // Catch:{ Throwable -> 0x016d, all -> 0x0165 }
            java.lang.StringBuilder r13 = r13.append(r10)     // Catch:{ Throwable -> 0x016d, all -> 0x0165 }
            java.lang.StringBuilder r13 = r13.append(r6)     // Catch:{ Throwable -> 0x016d, all -> 0x0165 }
            java.lang.String r10 = r13.toString()     // Catch:{ Throwable -> 0x016d, all -> 0x0165 }
            goto L_0x00ee
        L_0x0106:
            r8.close()     // Catch:{ Throwable -> 0x0110 }
            r4.close()     // Catch:{ Throwable -> 0x0110 }
            r3 = r4
            r7 = r8
            goto L_0x008b
        L_0x0110:
            r11 = move-exception
            java.lang.String r13 = "H5AppHttpRequest"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "request post error in finally "
            r14.<init>(r15)
            java.lang.StringBuilder r14 = r14.append(r11)
            java.lang.String r14 = r14.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r13, r14)
            r3 = r4
            r7 = r8
            goto L_0x008b
        L_0x0129:
            r11 = move-exception
            java.lang.String r13 = "H5AppHttpRequest"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "request post error in finally "
            r14.<init>(r15)
            java.lang.StringBuilder r14 = r14.append(r11)
            java.lang.String r14 = r14.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r13, r14)
            goto L_0x008b
        L_0x0140:
            r13 = move-exception
        L_0x0141:
            if (r7 == 0) goto L_0x0146
            r7.close()     // Catch:{ Throwable -> 0x014c }
        L_0x0146:
            if (r3 == 0) goto L_0x014b
            r3.close()     // Catch:{ Throwable -> 0x014c }
        L_0x014b:
            throw r13
        L_0x014c:
            r11 = move-exception
            java.lang.String r14 = "H5AppHttpRequest"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            java.lang.String r16 = "request post error in finally "
            r15.<init>(r16)
            java.lang.StringBuilder r15 = r15.append(r11)
            java.lang.String r15 = r15.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r14, r15)
            goto L_0x014b
        L_0x0162:
            r13 = move-exception
            r7 = r8
            goto L_0x0141
        L_0x0165:
            r13 = move-exception
            r3 = r4
            r7 = r8
            goto L_0x0141
        L_0x0169:
            r11 = move-exception
            r7 = r8
            goto L_0x005a
        L_0x016d:
            r11 = move-exception
            r3 = r4
            r7 = r8
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest.execute():java.lang.String");
    }

    public Builder newBuilder() {
        return new Builder(this);
    }
}
