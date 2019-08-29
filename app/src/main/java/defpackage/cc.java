package defpackage;

import android.util.Base64InputStream;
import anet.channel.statist.AmdcStatistic;
import anet.channel.statist.StatObject;
import anet.channel.status.NetworkStatusHelper;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* renamed from: cc reason: default package */
/* compiled from: DispatchCore */
final class cc {
    static AtomicInteger a = new AtomicInteger(0);
    static HostnameVerifier b = new HostnameVerifier() {
        public final boolean verify(String str, SSLSession sSLSession) {
            return HttpsURLConnection.getDefaultHostnameVerifier().verify(cb.a(), sSLSession);
        }
    };
    static Random c = new Random();

    public static void a(Map map) {
        bo boVar;
        String str;
        String str2;
        if (map != null) {
            String a2 = bu.a().a(cb.a(), (String) "http");
            List<bo> list = Collections.EMPTY_LIST;
            if (!NetworkStatusHelper.i()) {
                list = bu.a().a(cb.a());
                ListIterator<bo> listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    if (!listIterator.next().e().protocol.equalsIgnoreCase(a2)) {
                        listIterator.remove();
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                HashMap hashMap = new HashMap(map);
                if (i != 2) {
                    boVar = !list.isEmpty() ? list.remove(0) : null;
                    if (boVar != null) {
                        str = a(a2, boVar.a(), boVar.d(), (Map<String, String>) hashMap, i);
                    } else {
                        str = a(a2, (String) null, 0, (Map<String, String>) hashMap, i);
                    }
                } else {
                    String[] b2 = cb.b();
                    if (b2 == null || b2.length <= 0) {
                        str2 = a(a2, (String) null, 0, (Map<String, String>) hashMap, i);
                    } else {
                        str2 = a(a2, b2[c.nextInt(b2.length)], 0, (Map<String, String>) hashMap, i);
                    }
                    String str3 = str2;
                    boVar = null;
                    str = str3;
                }
                int a3 = a(str, hashMap, i);
                if (boVar != null) {
                    bm bmVar = new bm();
                    bmVar.a = a3 == 0;
                    bu.a().a(cb.a(), boVar, bmVar);
                }
                if (a3 == 0 || a3 == 2) {
                    break;
                }
            }
        }
    }

    private static String a(String str, String str2, int i, Map<String, String> map, int i2) {
        StringBuilder sb = new StringBuilder(64);
        if (i2 == 2 && "https".equalsIgnoreCase(str) && c.nextBoolean()) {
            str = "http";
        }
        sb.append(str);
        sb.append("://");
        if (str2 != null) {
            ct.a();
            if (ci.b(str2)) {
                sb.append('[');
                sb.append(str2);
                sb.append(']');
            } else {
                sb.append(str2);
            }
            if (i == 0) {
                i = "https".equalsIgnoreCase(str) ? 443 : 80;
            }
            sb.append(":");
            sb.append(i);
        } else {
            sb.append(cb.a());
        }
        sb.append("/amdc/mobileDispatch");
        TreeMap treeMap = new TreeMap();
        treeMap.put("appkey", map.remove("appkey"));
        treeMap.put("v", map.remove("v"));
        treeMap.put("deviceId", map.remove("deviceId"));
        treeMap.put("platform", map.remove("platform"));
        sb.append('?');
        sb.append(ci.a(treeMap, "utf-8"));
        return sb.toString();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:114|115|(2:117|118)|121|122) */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        defpackage.cf.b.a.a(new defpackage.cd(0, null));
        defpackage.cl.d("awcn.DispatchCore", "resolve amdc anser failed", r4, new java.lang.Object[0]);
        a((java.lang.String) "-1004", (java.lang.String) "resolve answer failed", r8, r3, 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0274, code lost:
        if (r11 != null) goto L_0x0276;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:?, code lost:
        r11.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x027a, code lost:
        defpackage.cl.e("awcn.DispatchCore", "http disconnect failed", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0285, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x028d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x028e, code lost:
        r1 = r0;
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0291, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0292, code lost:
        r1 = r0;
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x02a3, code lost:
        r2 = r1.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:?, code lost:
        r6.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x02bc, code lost:
        defpackage.cl.e("awcn.DispatchCore", "http disconnect failed", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:114:0x0258 */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0291 A[ExcHandler: all (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x02a3 A[Catch:{ all -> 0x02c8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02b8 A[SYNTHETIC, Splitter:B:139:0x02b8] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x02cd A[SYNTHETIC, Splitter:B:148:0x02cd] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(java.lang.String r18, java.util.Map r19, int r20) {
        /*
            r1 = r18
            r2 = r19
            r3 = r20
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "AMDC"
            r4.<init>(r5)
            java.util.concurrent.atomic.AtomicInteger r5 = a
            int r5 = r5.incrementAndGet()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "awcn.DispatchCore"
            java.lang.String r6 = "send amdc request"
            r7 = 4
            java.lang.Object[] r8 = new java.lang.Object[r7]
            java.lang.String r9 = "url"
            r10 = 0
            r8[r10] = r9
            r9 = 1
            r8[r9] = r1
            java.lang.String r11 = "\nhost"
            r12 = 2
            r8[r12] = r11
            java.lang.String r11 = "domain"
            java.lang.Object r11 = r2.get(r11)
            java.lang.String r11 = r11.toString()
            r13 = 3
            r8[r13] = r11
            defpackage.cl.b(r5, r6, r4, r8)
            java.lang.String r5 = "Env"
            java.lang.Object r5 = r2.remove(r5)
            anet.channel.entity.ENV r5 = (anet.channel.entity.ENV) r5
            r6 = 0
            java.net.URL r8 = new java.net.URL     // Catch:{ Throwable -> 0x0295, all -> 0x0291 }
            r8.<init>(r1)     // Catch:{ Throwable -> 0x0295, all -> 0x0291 }
            java.net.URLConnection r11 = r8.openConnection()     // Catch:{ Throwable -> 0x028d, all -> 0x0291 }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ Throwable -> 0x028d, all -> 0x0291 }
            r14 = 20000(0x4e20, float:2.8026E-41)
            r11.setConnectTimeout(r14)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r11.setReadTimeout(r14)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r14 = "POST"
            r11.setRequestMethod(r14)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r11.setDoOutput(r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r11.setDoInput(r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r14 = "Connection"
            java.lang.String r15 = "close"
            r11.addRequestProperty(r14, r15)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r14 = "Accept-Encoding"
            java.lang.String r15 = "gzip"
            r11.addRequestProperty(r14, r15)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r11.setInstanceFollowRedirects(r10)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r14 = r8.getProtocol()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r15 = "https"
            boolean r14 = r14.equals(r15)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r14 == 0) goto L_0x008e
            r14 = r11
            javax.net.ssl.HttpsURLConnection r14 = (javax.net.ssl.HttpsURLConnection) r14     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            javax.net.ssl.HostnameVerifier r15 = b     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r14.setHostnameVerifier(r15)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
        L_0x008e:
            java.io.OutputStream r14 = r11.getOutputStream()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r15 = "utf-8"
            java.lang.String r2 = defpackage.ci.a(r2, r15)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            byte[] r2 = r2.getBytes()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r14.write(r2)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            int r14 = r11.getResponseCode()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            boolean r15 = defpackage.cl.a(r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r15 == 0) goto L_0x00c4
            java.lang.String r15 = "awcn.DispatchCore"
            java.lang.String r13 = "amdc response. code: "
            java.lang.String r7 = java.lang.String.valueOf(r14)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r7 = r13.concat(r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.Object[] r13 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r16 = "\nheaders"
            r13[r10] = r16     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.util.Map r16 = r11.getHeaderFields()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r13[r9] = r16     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            defpackage.cl.a(r15, r7, r4, r13)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
        L_0x00c4:
            r7 = 200(0xc8, float:2.8E-43)
            if (r14 == r7) goto L_0x00eb
            r1 = 302(0x12e, float:4.23E-43)
            if (r14 == r1) goto L_0x00d2
            r1 = 307(0x133, float:4.3E-43)
            if (r14 != r1) goto L_0x00d1
            goto L_0x00d2
        L_0x00d1:
            r12 = 1
        L_0x00d2:
            java.lang.String r1 = java.lang.String.valueOf(r14)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r2 = "response code not 200"
            a(r1, r2, r8, r3, r12)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x00ea
            r11.disconnect()     // Catch:{ Exception -> 0x00e1 }
            goto L_0x00ea
        L_0x00e1:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            defpackage.cl.e(r1, r2, r6, r3)
        L_0x00ea:
            return r12
        L_0x00eb:
            java.lang.String r7 = "x-am-code"
            java.lang.String r7 = r11.getHeaderField(r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r13 = "1000"
            boolean r13 = r13.equals(r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r13 != 0) goto L_0x0128
            java.lang.String r1 = "1007"
            boolean r1 = r1.equals(r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r1 != 0) goto L_0x010b
            java.lang.String r1 = "1008"
            boolean r1 = r1.equals(r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r1 == 0) goto L_0x010a
            goto L_0x010b
        L_0x010a:
            r12 = 1
        L_0x010b:
            java.lang.String r1 = "return code: "
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            a(r7, r1, r8, r3, r12)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x0127
            r11.disconnect()     // Catch:{ Exception -> 0x011e }
            goto L_0x0127
        L_0x011e:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            defpackage.cl.e(r1, r2, r6, r3)
        L_0x0127:
            return r12
        L_0x0128:
            java.lang.String r13 = "x-am-sign"
            java.lang.String r13 = r11.getHeaderField(r13)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            boolean r14 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r14 == 0) goto L_0x014b
            java.lang.String r1 = "-1001"
            java.lang.String r2 = "response sign is empty"
            a(r1, r2, r8, r3, r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x014a
            r11.disconnect()     // Catch:{ Exception -> 0x0141 }
            goto L_0x014a
        L_0x0141:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            defpackage.cl.e(r1, r2, r6, r3)
        L_0x014a:
            return r9
        L_0x014b:
            java.io.InputStream r14 = r11.getInputStream()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r15 = "gzip"
            java.lang.String r6 = r11.getContentEncoding()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            boolean r6 = r15.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r6 = a(r14, r6)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            boolean r14 = defpackage.cl.a(r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r14 == 0) goto L_0x0174
            java.lang.String r14 = "awcn.DispatchCore"
            java.lang.String r15 = "amdc response body"
            java.lang.Object[] r9 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r16 = "\nbody"
            r9[r10] = r16     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r16 = 1
            r9[r16] = r6     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            defpackage.cl.a(r14, r15, r4, r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
        L_0x0174:
            int r2 = r2.length     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            long r14 = (long) r2     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            int r2 = r11.getContentLength()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r17 = r13
            long r12 = (long) r2
            aj r2 = new aj     // Catch:{ Exception -> 0x0198 }
            r2.<init>()     // Catch:{ Exception -> 0x0198 }
            java.lang.String r9 = "amdc"
            r2.a = r9     // Catch:{ Exception -> 0x0198 }
            java.lang.String r9 = "http"
            r2.b = r9     // Catch:{ Exception -> 0x0198 }
            r2.c = r1     // Catch:{ Exception -> 0x0198 }
            r2.d = r14     // Catch:{ Exception -> 0x0198 }
            r2.e = r12     // Catch:{ Exception -> 0x0198 }
            ak r1 = defpackage.al.a()     // Catch:{ Exception -> 0x0198 }
            r1.a(r2)     // Catch:{ Exception -> 0x0198 }
            goto L_0x01a2
        L_0x0198:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "commit flow info failed!"
            java.lang.Object[] r9 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r12 = 0
            defpackage.cl.e(r1, r2, r12, r9)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
        L_0x01a2:
            boolean r1 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r1 == 0) goto L_0x01c2
            java.lang.String r1 = "-1002"
            java.lang.String r2 = "read answer error"
            r5 = 1
            a(r1, r2, r8, r3, r5)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x01c0
            r11.disconnect()     // Catch:{ Exception -> 0x01b6 }
            goto L_0x01c0
        L_0x01b6:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r4 = 0
            defpackage.cl.e(r1, r2, r4, r3)
        L_0x01c0:
            r1 = 1
            return r1
        L_0x01c2:
            cg r1 = defpackage.bz.b()     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r1 == 0) goto L_0x01d0
            java.lang.String r1 = r1.a(r6)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r2 = r1
            r1 = r17
            goto L_0x01d3
        L_0x01d0:
            r1 = r17
            r2 = 0
        L_0x01d3:
            boolean r9 = r2.equalsIgnoreCase(r1)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r9 != 0) goto L_0x020c
            java.lang.String r5 = "awcn.DispatchCore"
            java.lang.String r6 = "check ret sign failed"
            r7 = 4
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r9 = "retSign"
            r7[r10] = r9     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r9 = 1
            r7[r9] = r1     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r1 = "checkSign"
            r9 = 2
            r7[r9] = r1     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r1 = 3
            r7[r1] = r2     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            defpackage.cl.d(r5, r6, r4, r7)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r1 = "-1003"
            java.lang.String r2 = "check sign failed"
            r5 = 1
            a(r1, r2, r8, r3, r5)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x020a
            r11.disconnect()     // Catch:{ Exception -> 0x0200 }
            goto L_0x020a
        L_0x0200:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r4 = 0
            defpackage.cl.e(r1, r2, r4, r3)
        L_0x020a:
            r1 = 1
            return r1
        L_0x020c:
            org.json.JSONTokener r1 = new org.json.JSONTokener     // Catch:{ JSONException -> 0x0258 }
            r1.<init>(r6)     // Catch:{ JSONException -> 0x0258 }
            java.lang.Object r1 = r1.nextValue()     // Catch:{ JSONException -> 0x0258 }
            org.json.JSONObject r1 = (org.json.JSONObject) r1     // Catch:{ JSONException -> 0x0258 }
            anet.channel.entity.ENV r2 = defpackage.m.d()     // Catch:{ JSONException -> 0x0258 }
            if (r2 == r5) goto L_0x0237
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "env change, do not notify result"
            java.lang.Object[] r5 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x0258 }
            defpackage.cl.c(r1, r2, r4, r5)     // Catch:{ JSONException -> 0x0258 }
            if (r11 == 0) goto L_0x0236
            r11.disconnect()     // Catch:{ Exception -> 0x022c }
            goto L_0x0236
        L_0x022c:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r4 = 0
            defpackage.cl.e(r1, r2, r4, r3)
        L_0x0236:
            return r10
        L_0x0237:
            cf r2 = defpackage.cf.b.a     // Catch:{ JSONException -> 0x0258 }
            cd r5 = new cd     // Catch:{ JSONException -> 0x0258 }
            r6 = 1
            r5.<init>(r6, r1)     // Catch:{ JSONException -> 0x0258 }
            r2.a(r5)     // Catch:{ JSONException -> 0x0258 }
            java.lang.String r1 = "request success"
            a(r7, r1, r8, r3, r10)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x0257
            r11.disconnect()     // Catch:{ Exception -> 0x024d }
            goto L_0x0257
        L_0x024d:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r4 = 0
            defpackage.cl.e(r1, r2, r4, r3)
        L_0x0257:
            return r10
        L_0x0258:
            cf r1 = defpackage.cf.b.a     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            cd r2 = new cd     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r5 = 0
            r2.<init>(r10, r5)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            r1.a(r2)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "resolve amdc anser failed"
            java.lang.Object[] r5 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            defpackage.cl.d(r1, r2, r4, r5)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            java.lang.String r1 = "-1004"
            java.lang.String r2 = "resolve answer failed"
            r5 = 1
            a(r1, r2, r8, r3, r5)     // Catch:{ Throwable -> 0x0289, all -> 0x0286 }
            if (r11 == 0) goto L_0x0284
            r11.disconnect()     // Catch:{ Exception -> 0x027a }
            goto L_0x0284
        L_0x027a:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r4 = 0
            defpackage.cl.e(r1, r2, r4, r3)
        L_0x0284:
            r1 = 1
            return r1
        L_0x0286:
            r0 = move-exception
            r1 = r0
            goto L_0x02cb
        L_0x0289:
            r0 = move-exception
            r1 = r0
            r6 = r11
            goto L_0x0299
        L_0x028d:
            r0 = move-exception
            r1 = r0
            r6 = 0
            goto L_0x0299
        L_0x0291:
            r0 = move-exception
            r1 = r0
            r11 = 0
            goto L_0x02cb
        L_0x0295:
            r0 = move-exception
            r1 = r0
            r6 = 0
            r8 = 0
        L_0x0299:
            java.lang.String r2 = r1.getMessage()     // Catch:{ all -> 0x02c8 }
            boolean r5 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x02c8 }
            if (r5 == 0) goto L_0x02a7
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x02c8 }
        L_0x02a7:
            java.lang.String r1 = "-1000"
            r5 = 1
            a(r1, r2, r8, r3, r5)     // Catch:{ all -> 0x02c8 }
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "amdc request fail"
            java.lang.Object[] r3 = new java.lang.Object[r10]     // Catch:{ all -> 0x02c8 }
            defpackage.cl.e(r1, r2, r4, r3)     // Catch:{ all -> 0x02c8 }
            if (r6 == 0) goto L_0x02c6
            r6.disconnect()     // Catch:{ Exception -> 0x02bc }
            goto L_0x02c6
        L_0x02bc:
            java.lang.String r1 = "awcn.DispatchCore"
            java.lang.String r2 = "http disconnect failed"
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r4 = 0
            defpackage.cl.e(r1, r2, r4, r3)
        L_0x02c6:
            r1 = 1
            return r1
        L_0x02c8:
            r0 = move-exception
            r1 = r0
            r11 = r6
        L_0x02cb:
            if (r11 == 0) goto L_0x02db
            r11.disconnect()     // Catch:{ Exception -> 0x02d1 }
            goto L_0x02db
        L_0x02d1:
            java.lang.String r2 = "awcn.DispatchCore"
            java.lang.String r3 = "http disconnect failed"
            java.lang.Object[] r4 = new java.lang.Object[r10]
            r5 = 0
            defpackage.cl.e(r2, r3, r5, r4)
        L_0x02db:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cc.a(java.lang.String, java.util.Map, int):int");
    }

    private static String a(InputStream inputStream, boolean z) {
        InputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        if (z) {
            try {
                bufferedInputStream = new GZIPInputStream(bufferedInputStream);
            } catch (IOException unused) {
                try {
                    cl.e("awcn.DispatchCore", "", null, new Object[0]);
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused2) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused3) {
                    }
                    throw th;
                }
            }
        }
        InputStream base64InputStream = new Base64InputStream(bufferedInputStream, 0);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = base64InputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            String str = new String(byteArrayOutputStream.toByteArray(), "utf-8");
            try {
                base64InputStream.close();
            } catch (IOException unused4) {
            }
            return str;
        } catch (IOException unused5) {
            bufferedInputStream = base64InputStream;
            cl.e("awcn.DispatchCore", "", null, new Object[0]);
            bufferedInputStream.close();
            return null;
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream = base64InputStream;
            bufferedInputStream.close();
            throw th;
        }
    }

    private static void a(String str, String str2, URL url, int i, int i2) {
        if ((i2 != 1 || i == 2) && m.b()) {
            try {
                AmdcStatistic amdcStatistic = new AmdcStatistic();
                amdcStatistic.errorCode = str;
                amdcStatistic.errorMsg = str2;
                if (url != null) {
                    amdcStatistic.host = url.getHost();
                    amdcStatistic.url = url.toString();
                }
                amdcStatistic.retryTimes = i;
                x.a().a((StatObject) amdcStatistic);
            } catch (Exception unused) {
            }
        }
    }
}
