package com.autonavi.jni.ae.gmap.maploader;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.jni.ae.gmap.GLMapEngine;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class MapLoader {
    private static final int CIFA_UPLOAD_TIMELIMIT = 60000;
    private static final int CONNECTION_TIMEOUT = 20000;
    private static final int GET_METHOD = 0;
    private static String mDiu;
    private static long mLastRecordTime;
    private volatile boolean isCanceled = false;
    ADataRequestParam mDataRequestParam;
    private int mEngineID = 0;
    GLMapEngine mGLMapEngine;
    private HttpURLConnection mURLConnection = null;

    public static class ADataRequestParam {
        public long Handler;
        public String RequestBaseUrl;
        public String RequestUrl;
        public byte[] enCodeString;
        public int nCompress;
        public int nRequestType;
    }

    public String GetDeviceId(Context context) {
        return null;
    }

    public MapLoader(int i, GLMapEngine gLMapEngine, ADataRequestParam aDataRequestParam) {
        this.mDataRequestParam = aDataRequestParam;
        this.mEngineID = i;
        this.mGLMapEngine = gLMapEngine;
        this.mGLMapEngine.setMapLoaderToTask(i, this.mDataRequestParam.Handler, this);
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:70:0x01a8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:87:0x01c6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:95:0x01da */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x01f3 A[SYNTHETIC, Splitter:B:105:0x01f3] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01fd A[SYNTHETIC, Splitter:B:114:0x01fd] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0207 A[SYNTHETIC, Splitter:B:123:0x0207] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0211 A[SYNTHETIC, Splitter:B:132:0x0211] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01a4 A[SYNTHETIC, Splitter:B:66:0x01a4] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01b8 A[SYNTHETIC, Splitter:B:74:0x01b8] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01c2 A[SYNTHETIC, Splitter:B:83:0x01c2] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01d6 A[SYNTHETIC, Splitter:B:91:0x01d6] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x01ea A[SYNTHETIC, Splitter:B:99:0x01ea] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:87:0x01c6=Splitter:B:87:0x01c6, B:70:0x01a8=Splitter:B:70:0x01a8, B:95:0x01da=Splitter:B:95:0x01da} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void DoRequest() {
        /*
            r12 = this;
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r12.mGLMapEngine
            int r1 = r12.mEngineID
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r2 = r12.mDataRequestParam
            long r2 = r2.Handler
            boolean r0 = r0.getMapDataTaskIsCancel(r1, r2)
            if (r0 == 0) goto L_0x000f
            return
        L_0x000f:
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r0 = r12.mDataRequestParam
            java.lang.String r0 = r0.RequestBaseUrl
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r1 = r12.mDataRequestParam
            java.lang.String r1 = r1.RequestUrl
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = "?"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            alq r2 = defpackage.akq.a()
            if (r2 == 0) goto L_0x003f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            java.lang.String r0 = "ent=2&"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
        L_0x003f:
            java.lang.String r2 = ";"
            java.lang.String r3 = ";"
            java.lang.String r3 = r12.getEncodeRequestParams(r3)
            java.lang.String r3 = r3.toString()
            java.lang.String r1 = r1.replaceAll(r2, r3)
            r2 = 1
            java.lang.String r1 = r12.getRequestParams(r0, r1, r2)
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r3 = r12.mDataRequestParam
            int r3 = r3.nRequestType
            if (r3 != 0) goto L_0x0086
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            java.lang.String r0 = "&csid="
            r3.append(r0)
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            goto L_0x00a2
        L_0x0086:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            java.lang.String r0 = "csid="
            r3.append(r0)
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
        L_0x00a2:
            r3 = 0
            r4 = -1
            java.net.URL r5 = new java.net.URL     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r5.<init>(r0)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            alq r0 = defpackage.akq.a()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            if (r0 == 0) goto L_0x00bd
            alq r0 = defpackage.akq.a()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            com.autonavi.jni.ae.gmap.GLMapEngine r6 = r12.mGLMapEngine     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r6.getContext()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.Proxy r0 = r0.a()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            goto L_0x00be
        L_0x00bd:
            r0 = r3
        L_0x00be:
            if (r0 == 0) goto L_0x00c8
            java.net.URLConnection r0 = r5.openConnection(r0)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r12.mURLConnection = r0     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
        L_0x00c8:
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            if (r0 != 0) goto L_0x00d4
            java.net.URLConnection r0 = r5.openConnection()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r12.mURLConnection = r0     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
        L_0x00d4:
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r5 = 20000(0x4e20, float:2.8026E-41)
            r0.setConnectTimeout(r5)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.setReadTimeout(r5)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r0 = r12.mDataRequestParam     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            int r0 = r0.nRequestType     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r5 = 0
            if (r0 != 0) goto L_0x00ef
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.lang.String r1 = "GET"
            r0.setRequestMethod(r1)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            goto L_0x0125
        L_0x00ef:
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.lang.String r6 = "POST"
            r0.setRequestMethod(r6)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.setDoInput(r2)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.setDoOutput(r2)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.setUseCaches(r5)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.lang.String r6 = "User-Agent"
            com.autonavi.jni.ae.gmap.GLMapEngine r7 = r12.mGLMapEngine     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.lang.String r7 = r7.getUserAgent()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.setRequestProperty(r6, r7)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            byte[] r0 = r1.getBytes()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r1 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.io.OutputStream r1 = r1.getOutputStream()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r1.write(r0)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r1.flush()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r1.close()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
        L_0x0125:
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.connect()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            int r0 = r0.getResponseCode()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.net.HttpURLConnection r1 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            boolean r1 = r12.SetRequestResponseInfo(r1, r0)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            if (r1 != 0) goto L_0x013e
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0.<init>()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            throw r0     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
        L_0x013e:
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 != r1) goto L_0x0187
            java.net.HttpURLConnection r0 = r12.mURLConnection     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r1 = 512(0x200, float:7.175E-43)
            byte[] r1 = new byte[r1]     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
        L_0x014c:
            int r11 = r0.read(r1)     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            if (r11 < 0) goto L_0x016d
            com.autonavi.jni.ae.gmap.GLMapEngine r6 = r12.mGLMapEngine     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            int r7 = r12.mEngineID     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r3 = r12.mDataRequestParam     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            long r8 = r3.Handler     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            r10 = r1
            r6.receiveNetData(r7, r8, r10, r11)     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            com.autonavi.jni.ae.gmap.GLMapEngine r3 = r12.mGLMapEngine     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            int r6 = r12.mEngineID     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r7 = r12.mDataRequestParam     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            long r7 = r7.Handler     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            boolean r3 = r3.getMapDataTaskIsCancel(r6, r7)     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            if (r3 == 0) goto L_0x014c
            goto L_0x016e
        L_0x016d:
            r2 = 0
        L_0x016e:
            if (r2 != 0) goto L_0x0193
            com.autonavi.jni.ae.gmap.GLMapEngine r1 = r12.mGLMapEngine     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            int r2 = r12.mEngineID     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r3 = r12.mDataRequestParam     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            long r5 = r3.Handler     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            r1.finishDownLoad(r2, r5)     // Catch:{ OutOfMemoryError -> 0x020c, IllegalStateException -> 0x0202, IndexOutOfBoundsException -> 0x01f8, SocketTimeoutException -> 0x0185, IOException -> 0x0183, NullPointerException -> 0x01bd, NumberFormatException -> 0x0181, Exception -> 0x019f, all -> 0x017c }
            goto L_0x0193
        L_0x017c:
            r1 = move-exception
            r3 = r0
            r0 = r1
            goto L_0x01ee
        L_0x0181:
            r3 = r0
            goto L_0x01a8
        L_0x0183:
            r3 = r0
            goto L_0x01c6
        L_0x0185:
            r3 = r0
            goto L_0x01da
        L_0x0187:
            com.autonavi.jni.ae.gmap.GLMapEngine r1 = r12.mGLMapEngine     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            int r2 = r12.mEngineID     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r5 = r12.mDataRequestParam     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            long r5 = r5.Handler     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r1.netError(r2, r5, r0)     // Catch:{ OutOfMemoryError -> 0x020b, IllegalStateException -> 0x0201, IndexOutOfBoundsException -> 0x01f7, SocketTimeoutException -> 0x01da, IOException -> 0x01c6, NullPointerException -> 0x01bc, NumberFormatException -> 0x01a8, Exception -> 0x019e }
            r0 = r3
        L_0x0193:
            r12.doCancel()
            if (r0 == 0) goto L_0x0215
            r0.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x019b }
        L_0x019b:
            return
        L_0x019c:
            r0 = move-exception
            goto L_0x01ee
        L_0x019e:
            r0 = r3
        L_0x019f:
            r12.doCancel()
            if (r0 == 0) goto L_0x0215
            r0.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x01a7 }
        L_0x01a7:
            return
        L_0x01a8:
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r12.mGLMapEngine     // Catch:{ all -> 0x019c }
            int r1 = r12.mEngineID     // Catch:{ all -> 0x019c }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r2 = r12.mDataRequestParam     // Catch:{ all -> 0x019c }
            long r5 = r2.Handler     // Catch:{ all -> 0x019c }
            r0.netError(r1, r5, r4)     // Catch:{ all -> 0x019c }
            r12.doCancel()
            if (r3 == 0) goto L_0x0215
            r3.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x01bb }
        L_0x01bb:
            return
        L_0x01bc:
            r0 = r3
        L_0x01bd:
            r12.doCancel()
            if (r0 == 0) goto L_0x0215
            r0.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x01c5 }
        L_0x01c5:
            return
        L_0x01c6:
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r12.mGLMapEngine     // Catch:{ all -> 0x019c }
            int r1 = r12.mEngineID     // Catch:{ all -> 0x019c }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r2 = r12.mDataRequestParam     // Catch:{ all -> 0x019c }
            long r5 = r2.Handler     // Catch:{ all -> 0x019c }
            r0.netError(r1, r5, r4)     // Catch:{ all -> 0x019c }
            r12.doCancel()
            if (r3 == 0) goto L_0x0215
            r3.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x01d9 }
        L_0x01d9:
            return
        L_0x01da:
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r12.mGLMapEngine     // Catch:{ all -> 0x019c }
            int r1 = r12.mEngineID     // Catch:{ all -> 0x019c }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r2 = r12.mDataRequestParam     // Catch:{ all -> 0x019c }
            long r5 = r2.Handler     // Catch:{ all -> 0x019c }
            r0.netError(r1, r5, r4)     // Catch:{ all -> 0x019c }
            r12.doCancel()
            if (r3 == 0) goto L_0x0215
            r3.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x01ed }
        L_0x01ed:
            return
        L_0x01ee:
            r12.doCancel()
            if (r3 == 0) goto L_0x01f6
            r3.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x01f6 }
        L_0x01f6:
            throw r0
        L_0x01f7:
            r0 = r3
        L_0x01f8:
            r12.doCancel()
            if (r0 == 0) goto L_0x0215
            r0.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x0200 }
        L_0x0200:
            return
        L_0x0201:
            r0 = r3
        L_0x0202:
            r12.doCancel()
            if (r0 == 0) goto L_0x0215
            r0.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x020a }
        L_0x020a:
            return
        L_0x020b:
            r0 = r3
        L_0x020c:
            r12.doCancel()
            if (r0 == 0) goto L_0x0215
            r0.close()     // Catch:{ IOException | ArrayIndexOutOfBoundsException | NullPointerException -> 0x0214 }
        L_0x0214:
            return
        L_0x0215:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.gmap.maploader.MapLoader.DoRequest():void");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0022 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doCancel() {
        /*
            r6 = this;
            java.net.HttpURLConnection r0 = r6.mURLConnection
            if (r0 == 0) goto L_0x0026
            boolean r0 = r6.isCanceled
            if (r0 != 0) goto L_0x0026
            java.net.HttpURLConnection r0 = r6.mURLConnection
            monitor-enter(r0)
            r1 = 1
            r6.isCanceled = r1     // Catch:{ Exception -> 0x0022 }
            java.net.HttpURLConnection r1 = r6.mURLConnection     // Catch:{ Exception -> 0x0022 }
            r1.disconnect()     // Catch:{ Exception -> 0x0022 }
            com.autonavi.jni.ae.gmap.GLMapEngine r1 = r6.mGLMapEngine     // Catch:{ Exception -> 0x0022 }
            int r2 = r6.mEngineID     // Catch:{ Exception -> 0x0022 }
            com.autonavi.jni.ae.gmap.maploader.MapLoader$ADataRequestParam r3 = r6.mDataRequestParam     // Catch:{ Exception -> 0x0022 }
            long r3 = r3.Handler     // Catch:{ Exception -> 0x0022 }
            r5 = 0
            r1.setMapLoaderToTask(r2, r3, r5)     // Catch:{ Exception -> 0x0022 }
            goto L_0x0022
        L_0x0020:
            r1 = move-exception
            goto L_0x0024
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            return
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r1
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.gmap.maploader.MapLoader.doCancel():void");
    }

    private String getEncodeRequestParams(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public String getRequestParams(String str, String str2, boolean z) {
        String str3;
        if (akq.a() != null) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - mLastRecordTime) >= 60000) {
                mLastRecordTime = currentTimeMillis;
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(akq.a().b());
                str3 = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(akq.a().c());
                str3 = sb2.toString();
            }
            String a = akq.a().a(str3);
            if (!z) {
                try {
                    a = URLEncoder.encode(a, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (!z) {
                return "ent=2&in=".concat(String.valueOf(a));
            }
            return a;
        }
        if (mDiu == null) {
            mDiu = GetDeviceId(this.mGLMapEngine.getContext());
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append("&channel=amap7&div=GNaviMap&diu=");
        sb3.append(mDiu);
        sb3.append("&test_id=1_a");
        return sb3.toString();
    }

    /* access modifiers changed from: protected */
    public boolean SetRequestResponseInfo(HttpURLConnection httpURLConnection, int i) {
        if (this.mGLMapEngine == null) {
            return false;
        }
        String contentType = httpURLConnection.getContentType();
        if (TextUtils.isEmpty(contentType)) {
            return false;
        }
        int i2 = contentType.contains("application/json") ? 1 : contentType.contains("application/xml") ? 2 : 0;
        this.mGLMapEngine.SetRequestResponseInfo(this.mEngineID, this.mDataRequestParam.Handler, i2, i);
        if (this.mGLMapEngine.getMapDataTaskIsCancel(this.mEngineID, this.mDataRequestParam.Handler)) {
            return false;
        }
        return true;
    }

    public void OnCancelRequestConnected() {
        doCancel();
    }
}
