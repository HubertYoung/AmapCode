package com.alibaba.analytics.core.sync;

import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;

public class UrlWrapper {
    private static final int ENVIRONMENT_BETA = 1;
    private static final int ENVIRONMENT_DAILY = 3;
    private static final int ENVIRONMENT_ONLINE = 0;
    private static final int ENVIRONMENT_PRE = 2;
    private static final int HTTP_ENVIRONMENT = 0;
    private static final int MAX_CONNECTION_TIME_OUT = 10000;
    private static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 60000;
    public static int mErrorCode;
    public static final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    private static UtHostnameVerifier mUtHostnameVerifier;
    private static UtSslSocketFactory mUtSslSocketFactory;

    static {
        System.setProperty("http.keepAlive", "true");
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0222 A[SYNTHETIC, Splitter:B:105:0x0222] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0282 A[SYNTHETIC, Splitter:B:124:0x0282] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01d2 A[SYNTHETIC, Splitter:B:84:0x01d2] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01fd A[SYNTHETIC, Splitter:B:94:0x01fd] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.analytics.core.sync.BizResponse sendRequest(byte[] r10) {
        /*
            com.alibaba.analytics.utils.Logger.d()
            com.alibaba.analytics.core.sync.BizResponse r0 = new com.alibaba.analytics.core.sync.BizResponse
            r0.<init>()
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.core.sync.HttpsHostPortMgr r3 = com.alibaba.analytics.core.sync.HttpsHostPortMgr.getInstance()     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.lang.String r3 = r3.getHttpsUrl()     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            r2.<init>(r3)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.net.URLConnection r3 = r2.openConnection()     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            boolean r4 = r3 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            r5 = 1
            if (r4 == 0) goto L_0x0080
            java.lang.String r2 = r2.getHost()     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            if (r4 == 0) goto L_0x002c
            return r0
        L_0x002c:
            com.alibaba.analytics.core.sync.UtSslSocketFactory r4 = mUtSslSocketFactory     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            if (r4 == 0) goto L_0x003c
            com.alibaba.analytics.core.sync.UtSslSocketFactory r4 = mUtSslSocketFactory     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.lang.String r4 = r4.getHost()     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            boolean r4 = r2.equals(r4)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            if (r4 != 0) goto L_0x004e
        L_0x003c:
            java.lang.String r4 = "UrlWrapper"
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.lang.String r7 = "new SslSocketFactory"
            r6[r1] = r7     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.utils.Logger.d(r4, r6)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.core.sync.UtSslSocketFactory r4 = new com.alibaba.analytics.core.sync.UtSslSocketFactory     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            r4.<init>(r2)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            mUtSslSocketFactory = r4     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
        L_0x004e:
            r4 = r3
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.core.sync.UtSslSocketFactory r6 = mUtSslSocketFactory     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            r4.setSSLSocketFactory(r6)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.core.sync.UtHostnameVerifier r4 = mUtHostnameVerifier     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            if (r4 == 0) goto L_0x0066
            com.alibaba.analytics.core.sync.UtHostnameVerifier r4 = mUtHostnameVerifier     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.lang.String r4 = r4.getHost()     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            boolean r4 = r2.equals(r4)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            if (r4 != 0) goto L_0x0078
        L_0x0066:
            java.lang.String r4 = "UrlWrapper"
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            java.lang.String r7 = "new HostnameVerifier"
            r6[r1] = r7     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.utils.Logger.d(r4, r6)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.core.sync.UtHostnameVerifier r4 = new com.alibaba.analytics.core.sync.UtHostnameVerifier     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            r4.<init>(r2)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            mUtHostnameVerifier = r4     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
        L_0x0078:
            r2 = r3
            javax.net.ssl.HttpsURLConnection r2 = (javax.net.ssl.HttpsURLConnection) r2     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            com.alibaba.analytics.core.sync.UtHostnameVerifier r4 = mUtHostnameVerifier     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
            r2.setHostnameVerifier(r4)     // Catch:{ MalformedURLException -> 0x02a4, IOException -> 0x029b }
        L_0x0080:
            if (r3 == 0) goto L_0x029a
            r3.setDoOutput(r5)
            r3.setDoInput(r5)
            java.lang.String r2 = "POST"
            r3.setRequestMethod(r2)     // Catch:{ ProtocolException -> 0x0291 }
            r3.setUseCaches(r1)
            r2 = 10000(0x2710, float:1.4013E-41)
            r3.setConnectTimeout(r2)
            r2 = 60000(0xea60, float:8.4078E-41)
            r3.setReadTimeout(r2)
            r3.setInstanceFollowRedirects(r5)
            java.lang.String r2 = "Content-Type"
            java.lang.String r4 = "application/x-www-form-urlencoded"
            r3.setRequestProperty(r2, r4)
            java.lang.String r2 = "Charset"
            java.lang.String r4 = "UTF-8"
            r3.setRequestProperty(r2, r4)
            com.alibaba.analytics.core.Variables r2 = com.alibaba.analytics.core.Variables.getInstance()
            java.lang.String r2 = r2.getAppkey()
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x00c0
            java.lang.String r4 = "x-k"
            r3.setRequestProperty(r4, r2)
        L_0x00c0:
            com.alibaba.analytics.core.Variables r2 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Throwable -> 0x014d }
            com.ut.mini.core.sign.IUTRequestAuthentication r2 = r2.getRequestAuthenticationInstance()     // Catch:{ Throwable -> 0x014d }
            if (r2 == 0) goto L_0x0155
            java.lang.String r4 = com.alibaba.analytics.utils.MD5Utils.getMd5Hex(r10)     // Catch:{ Throwable -> 0x014d }
            java.lang.String r4 = r2.getSign(r4)     // Catch:{ Throwable -> 0x014d }
            java.lang.String r6 = ""
            r7 = 2
            java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x014d }
            java.lang.String r9 = "signValue"
            r8[r1] = r9     // Catch:{ Throwable -> 0x014d }
            r8[r5] = r4     // Catch:{ Throwable -> 0x014d }
            com.alibaba.analytics.utils.Logger.d(r6, r8)     // Catch:{ Throwable -> 0x014d }
            java.lang.String r6 = "x-s"
            r3.setRequestProperty(r6, r4)     // Catch:{ Throwable -> 0x014d }
            boolean r4 = r2 instanceof com.ut.mini.core.sign.UTBaseRequestAuthentication     // Catch:{ Throwable -> 0x014d }
            if (r4 == 0) goto L_0x012a
            com.ut.mini.core.sign.UTBaseRequestAuthentication r2 = (com.ut.mini.core.sign.UTBaseRequestAuthentication) r2     // Catch:{ Throwable -> 0x014d }
            boolean r2 = r2.isEncode()     // Catch:{ Throwable -> 0x014d }
            if (r2 == 0) goto L_0x010e
            java.lang.String r2 = "x-t"
            java.lang.String r4 = "2"
            r3.setRequestProperty(r2, r4)     // Catch:{ Throwable -> 0x014d }
            java.lang.String r2 = ""
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x014d }
            java.lang.String r6 = "x-t"
            r4[r1] = r6     // Catch:{ Throwable -> 0x014d }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x014d }
            r4[r5] = r6     // Catch:{ Throwable -> 0x014d }
            com.alibaba.analytics.utils.Logger.d(r2, r4)     // Catch:{ Throwable -> 0x014d }
            goto L_0x0155
        L_0x010e:
            java.lang.String r2 = "x-t"
            java.lang.String r4 = "3"
            r3.setRequestProperty(r2, r4)     // Catch:{ Throwable -> 0x014d }
            java.lang.String r2 = ""
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x014d }
            java.lang.String r6 = "x-t"
            r4[r1] = r6     // Catch:{ Throwable -> 0x014d }
            r6 = 3
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x014d }
            r4[r5] = r6     // Catch:{ Throwable -> 0x014d }
            com.alibaba.analytics.utils.Logger.d(r2, r4)     // Catch:{ Throwable -> 0x014d }
            goto L_0x0155
        L_0x012a:
            boolean r4 = r2 instanceof com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication     // Catch:{ Throwable -> 0x014d }
            if (r4 != 0) goto L_0x0132
            boolean r2 = r2 instanceof com.ut.mini.core.sign.UTSecurityThridRequestAuthentication     // Catch:{ Throwable -> 0x014d }
            if (r2 == 0) goto L_0x0155
        L_0x0132:
            java.lang.String r2 = "x-t"
            java.lang.String r4 = "1"
            r3.setRequestProperty(r2, r4)     // Catch:{ Throwable -> 0x014d }
            java.lang.String r2 = ""
            java.lang.Object[] r4 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x014d }
            java.lang.String r6 = "x-t"
            r4[r1] = r6     // Catch:{ Throwable -> 0x014d }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x014d }
            r4[r5] = r6     // Catch:{ Throwable -> 0x014d }
            com.alibaba.analytics.utils.Logger.d(r2, r4)     // Catch:{ Throwable -> 0x014d }
            goto L_0x0155
        L_0x014d:
            r2 = move-exception
            java.lang.String r4 = ""
            java.lang.Object[] r6 = new java.lang.Object[r1]
            com.alibaba.analytics.utils.Logger.e(r4, r2, r6)
        L_0x0155:
            long r6 = java.lang.System.currentTimeMillis()
            r2 = 0
            r3.connect()     // Catch:{ SSLHandshakeException -> 0x0231, Exception -> 0x020e }
            if (r10 == 0) goto L_0x017e
            int r4 = r10.length     // Catch:{ SSLHandshakeException -> 0x0231, Exception -> 0x020e }
            if (r4 <= 0) goto L_0x017e
            java.io.DataOutputStream r4 = new java.io.DataOutputStream     // Catch:{ SSLHandshakeException -> 0x0231, Exception -> 0x020e }
            java.io.OutputStream r8 = r3.getOutputStream()     // Catch:{ SSLHandshakeException -> 0x0231, Exception -> 0x020e }
            r4.<init>(r8)     // Catch:{ SSLHandshakeException -> 0x0231, Exception -> 0x020e }
            r4.write(r10)     // Catch:{ SSLHandshakeException -> 0x017a, Exception -> 0x0176, all -> 0x0172 }
            r4.flush()     // Catch:{ SSLHandshakeException -> 0x017a, Exception -> 0x0176, all -> 0x0172 }
            goto L_0x017f
        L_0x0172:
            r10 = move-exception
            r2 = r4
            goto L_0x0280
        L_0x0176:
            r10 = move-exception
            r2 = r4
            goto L_0x020f
        L_0x017a:
            r10 = move-exception
            r2 = r4
            goto L_0x0232
        L_0x017e:
            r4 = r2
        L_0x017f:
            if (r4 == 0) goto L_0x018f
            r4.close()     // Catch:{ IOException -> 0x0185 }
            goto L_0x018f
        L_0x0185:
            r10 = move-exception
            java.lang.String r4 = ""
            java.lang.Object[] r8 = new java.lang.Object[r5]
            r8[r1] = r10
            com.alibaba.analytics.utils.Logger.d(r4, r8)
        L_0x018f:
            long r8 = java.lang.System.currentTimeMillis()
            long r8 = r8 - r6
            r0.rt = r8
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream
            r10.<init>()
            java.io.DataInputStream r4 = new java.io.DataInputStream     // Catch:{ IOException -> 0x01c4, all -> 0x01c1 }
            java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x01c4, all -> 0x01c1 }
            r4.<init>(r3)     // Catch:{ IOException -> 0x01c4, all -> 0x01c1 }
            r2 = 2048(0x800, float:2.87E-42)
            byte[] r3 = new byte[r2]     // Catch:{ IOException -> 0x01bf }
        L_0x01a8:
            int r6 = r4.read(r3, r1, r2)     // Catch:{ IOException -> 0x01bf }
            r7 = -1
            if (r6 == r7) goto L_0x01b3
            r10.write(r3, r1, r6)     // Catch:{ IOException -> 0x01bf }
            goto L_0x01a8
        L_0x01b3:
            r4.close()     // Catch:{ Exception -> 0x01b7 }
            goto L_0x01e0
        L_0x01b7:
            r2 = move-exception
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r5]
            r4[r1] = r2
            goto L_0x01dd
        L_0x01bf:
            r2 = move-exception
            goto L_0x01c7
        L_0x01c1:
            r10 = move-exception
            r4 = r2
            goto L_0x01fb
        L_0x01c4:
            r3 = move-exception
            r4 = r2
            r2 = r3
        L_0x01c7:
            java.lang.String r3 = ""
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ all -> 0x01fa }
            r6[r1] = r2     // Catch:{ all -> 0x01fa }
            com.alibaba.analytics.utils.Logger.d(r3, r6)     // Catch:{ all -> 0x01fa }
            if (r4 == 0) goto L_0x01e0
            r4.close()     // Catch:{ Exception -> 0x01d6 }
            goto L_0x01e0
        L_0x01d6:
            r2 = move-exception
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r5]
            r4[r1] = r2
        L_0x01dd:
            com.alibaba.analytics.utils.Logger.d(r3, r4)
        L_0x01e0:
            int r1 = r10.size()
            if (r1 <= 0) goto L_0x029a
            byte[] r10 = r10.toByteArray()
            int r10 = com.alibaba.analytics.core.sync.BizRequest.parseResult(r10)
            mErrorCode = r10
            int r10 = mErrorCode
            r0.errCode = r10
            java.lang.String r10 = com.alibaba.analytics.core.sync.BizRequest.mResponseAdditionalData
            r0.data = r10
            goto L_0x029a
        L_0x01fa:
            r10 = move-exception
        L_0x01fb:
            if (r4 == 0) goto L_0x020b
            r4.close()     // Catch:{ Exception -> 0x0201 }
            goto L_0x020b
        L_0x0201:
            r0 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r5]
            r3[r1] = r0
            com.alibaba.analytics.utils.Logger.d(r2, r3)
        L_0x020b:
            throw r10
        L_0x020c:
            r10 = move-exception
            goto L_0x0280
        L_0x020e:
            r10 = move-exception
        L_0x020f:
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ all -> 0x020c }
            r4[r1] = r10     // Catch:{ all -> 0x020c }
            com.alibaba.analytics.utils.Logger.d(r3, r4)     // Catch:{ all -> 0x020c }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x020c }
            r10 = 0
            long r3 = r3 - r6
            r0.rt = r3     // Catch:{ all -> 0x020c }
            if (r2 == 0) goto L_0x0230
            r2.close()     // Catch:{ IOException -> 0x0226 }
            goto L_0x0230
        L_0x0226:
            r10 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r5]
            r3[r1] = r10
            com.alibaba.analytics.utils.Logger.d(r2, r3)
        L_0x0230:
            return r0
        L_0x0231:
            r10 = move-exception
        L_0x0232:
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ all -> 0x020c }
            r4[r1] = r10     // Catch:{ all -> 0x020c }
            com.alibaba.analytics.utils.Logger.d(r3, r4)     // Catch:{ all -> 0x020c }
            com.alibaba.analytics.core.Variables r10 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ all -> 0x020c }
            boolean r10 = r10.isSelfMonitorTurnOn()     // Catch:{ all -> 0x020c }
            if (r10 == 0) goto L_0x0267
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ all -> 0x020c }
            r10.<init>()     // Catch:{ all -> 0x020c }
            java.lang.String r3 = "type"
            java.lang.String r4 = "3"
            r10.put(r3, r4)     // Catch:{ all -> 0x020c }
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather r3 = mMonitor     // Catch:{ all -> 0x020c }
            int r4 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.UPLOAD_FAILED     // Catch:{ all -> 0x020c }
            java.lang.String r10 = com.alibaba.fastjson.JSON.toJSONString(r10)     // Catch:{ all -> 0x020c }
            r8 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            java.lang.Double r8 = java.lang.Double.valueOf(r8)     // Catch:{ all -> 0x020c }
            com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent r10 = com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent.buildCountEvent(r4, r10, r8)     // Catch:{ all -> 0x020c }
            r3.onEvent(r10)     // Catch:{ all -> 0x020c }
        L_0x0267:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x020c }
            r10 = 0
            long r3 = r3 - r6
            r0.rt = r3     // Catch:{ all -> 0x020c }
            if (r2 == 0) goto L_0x027f
            r2.close()     // Catch:{ IOException -> 0x0275 }
            goto L_0x027f
        L_0x0275:
            r10 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r5]
            r3[r1] = r10
            com.alibaba.analytics.utils.Logger.d(r2, r3)
        L_0x027f:
            return r0
        L_0x0280:
            if (r2 == 0) goto L_0x0290
            r2.close()     // Catch:{ IOException -> 0x0286 }
            goto L_0x0290
        L_0x0286:
            r0 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r5]
            r3[r1] = r0
            com.alibaba.analytics.utils.Logger.d(r2, r3)
        L_0x0290:
            throw r10
        L_0x0291:
            r10 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.alibaba.analytics.utils.Logger.e(r2, r10, r1)
            return r0
        L_0x029a:
            return r0
        L_0x029b:
            r10 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.alibaba.analytics.utils.Logger.e(r2, r10, r1)
            return r0
        L_0x02a4:
            r10 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.alibaba.analytics.utils.Logger.e(r2, r10, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.sync.UrlWrapper.sendRequest(byte[]):com.alibaba.analytics.core.sync.BizResponse");
    }
}
