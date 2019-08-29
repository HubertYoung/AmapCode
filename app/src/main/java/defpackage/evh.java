package defpackage;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.conn.ssl.SSLSocketFactory;

/* renamed from: evh reason: default package */
/* compiled from: NetworkUitlity */
public class evh {
    private static final String a = "evh";

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b2 A[Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6, AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6, AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ba A[Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6, AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6, AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.evw a(java.lang.String r9) {
        /*
            java.lang.String r0 = "Send data by Get!"
            defpackage.euw.a(r0)
            evw r0 = new evw
            r0.<init>()
            java.lang.String r1 = ""
            r2 = 0
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch:{ all -> 0x00ae }
            r4.<init>(r9)     // Catch:{ all -> 0x00ae }
            java.lang.String r5 = "https"
            boolean r9 = r9.startsWith(r5)     // Catch:{ all -> 0x00ae }
            r5 = 15000(0x3a98, float:2.102E-41)
            if (r9 == 0) goto L_0x0047
            java.net.URLConnection r9 = r4.openConnection()     // Catch:{ all -> 0x00ae }
            javax.net.ssl.HttpsURLConnection r9 = (javax.net.ssl.HttpsURLConnection) r9     // Catch:{ all -> 0x00ae }
            org.apache.http.conn.ssl.X509HostnameVerifier r4 = org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER     // Catch:{ all -> 0x0044 }
            r9.setHostnameVerifier(r4)     // Catch:{ all -> 0x0044 }
            r9.setReadTimeout(r5)     // Catch:{ all -> 0x0044 }
            r9.setConnectTimeout(r5)     // Catch:{ all -> 0x0044 }
            r9.connect()     // Catch:{ all -> 0x0044 }
            int r4 = r9.getResponseCode()     // Catch:{ all -> 0x0044 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ all -> 0x0044 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ all -> 0x0044 }
            java.io.InputStream r7 = r9.getInputStream()     // Catch:{ all -> 0x0044 }
            r6.<init>(r7)     // Catch:{ all -> 0x0044 }
            r5.<init>(r6)     // Catch:{ all -> 0x0044 }
            goto L_0x006b
        L_0x0044:
            r1 = move-exception
            goto L_0x00b0
        L_0x0047:
            java.net.URLConnection r9 = r4.openConnection()     // Catch:{ all -> 0x00ae }
            java.net.HttpURLConnection r9 = (java.net.HttpURLConnection) r9     // Catch:{ all -> 0x00ae }
            r9.setReadTimeout(r5)     // Catch:{ all -> 0x00a9 }
            r9.setConnectTimeout(r5)     // Catch:{ all -> 0x00a9 }
            r9.connect()     // Catch:{ all -> 0x00a9 }
            int r4 = r9.getResponseCode()     // Catch:{ all -> 0x00a9 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ all -> 0x00a9 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ all -> 0x00a9 }
            java.io.InputStream r7 = r9.getInputStream()     // Catch:{ all -> 0x00a9 }
            r6.<init>(r7)     // Catch:{ all -> 0x00a9 }
            r5.<init>(r6)     // Catch:{ all -> 0x00a9 }
            r8 = r3
            r3 = r9
            r9 = r8
        L_0x006b:
            java.lang.String r6 = r5.readLine()     // Catch:{ all -> 0x0044 }
            if (r6 == 0) goto L_0x0081
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0044 }
            r7.<init>()     // Catch:{ all -> 0x0044 }
            r7.append(r1)     // Catch:{ all -> 0x0044 }
            r7.append(r6)     // Catch:{ all -> 0x0044 }
            java.lang.String r1 = r7.toString()     // Catch:{ all -> 0x0044 }
            goto L_0x006b
        L_0x0081:
            if (r3 == 0) goto L_0x0086
            r3.disconnect()     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
        L_0x0086:
            if (r9 == 0) goto L_0x008b
            r9.disconnect()     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
        L_0x008b:
            java.lang.String r9 = "Status from get is "
            java.lang.String r3 = java.lang.String.valueOf(r4)     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            java.lang.String r9 = r9.concat(r3)     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            defpackage.euw.a(r9)     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            r9 = 200(0xc8, float:2.8E-43)
            if (r4 == r9) goto L_0x00a1
            r0.a = r2     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            r0.b = r1     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            goto L_0x00a6
        L_0x00a1:
            r9 = 1
            r0.a = r9     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            r0.b = r1     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
        L_0x00a6:
            r0.b = r1     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            goto L_0x0100
        L_0x00a9:
            r1 = move-exception
            r8 = r3
            r3 = r9
            r9 = r8
            goto L_0x00b0
        L_0x00ae:
            r1 = move-exception
            r9 = r3
        L_0x00b0:
            if (r3 == 0) goto L_0x00b8
            r3.disconnect()     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
            goto L_0x00b8
        L_0x00b6:
            r9 = move-exception
            goto L_0x00be
        L_0x00b8:
            if (r9 == 0) goto L_0x00bd
            r9.disconnect()     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
        L_0x00bd:
            throw r1     // Catch:{ AssertionError | Exception | UnsatisfiedLinkError -> 0x00b6 }
        L_0x00be:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "Collected:"
            r1.<init>(r3)
            java.lang.String r3 = r9.getMessage()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            defpackage.euw.a(r1)
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r3 = "err"
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x00ea }
            r1.put(r3, r9)     // Catch:{ JSONException -> 0x00ea }
            java.lang.String r9 = r1.toString()     // Catch:{ JSONException -> 0x00ea }
            r0.a = r2     // Catch:{ JSONException -> 0x00ea }
            r0.b = r9     // Catch:{ JSONException -> 0x00ea }
            goto L_0x0100
        L_0x00ea:
            r9 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
            java.lang.String r9 = r9.getMessage()
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            defpackage.euw.a(r9)
        L_0x0100:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evh.a(java.lang.String):evw");
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x015d A[SYNTHETIC, Splitter:B:65:0x015d] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0169 A[Catch:{ Exception -> 0x0165, Error -> 0x0163, all -> 0x0161 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11) {
        /*
            java.lang.String r0 = "Send data by Post!"
            defpackage.euw.a(r0)
            defpackage.eve.a(r10)
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ all -> 0x0158 }
            r1.<init>(r8)     // Catch:{ all -> 0x0158 }
            java.lang.String r2 = "https"
            boolean r2 = r8.startsWith(r2)     // Catch:{ all -> 0x0158 }
            r3 = 1
            r4 = 15000(0x3a98, float:2.102E-41)
            if (r2 == 0) goto L_0x0093
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ all -> 0x0158 }
            javax.net.ssl.HttpsURLConnection r1 = (javax.net.ssl.HttpsURLConnection) r1     // Catch:{ all -> 0x0158 }
            org.apache.http.conn.ssl.X509HostnameVerifier r2 = org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER     // Catch:{ all -> 0x008f }
            r1.setHostnameVerifier(r2)     // Catch:{ all -> 0x008f }
            java.lang.String r2 = "POST"
            r1.setRequestMethod(r2)     // Catch:{ all -> 0x008f }
            r1.setDoOutput(r3)     // Catch:{ all -> 0x008f }
            r1.setReadTimeout(r4)     // Catch:{ all -> 0x008f }
            r1.setConnectTimeout(r4)     // Catch:{ all -> 0x008f }
            java.lang.String r2 = "X_FORWARDED_FOR"
            java.lang.String r4 = ""
            java.lang.Object r2 = defpackage.ewp.b(r7, r2, r4)     // Catch:{ all -> 0x008f }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x008f }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x008f }
            if (r4 != 0) goto L_0x0047
            java.lang.String r4 = "X-Forwarded-For"
            r1.addRequestProperty(r4, r2)     // Catch:{ all -> 0x008f }
        L_0x0047:
            r1.connect()     // Catch:{ all -> 0x008f }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ all -> 0x008f }
            java.io.OutputStream r4 = r1.getOutputStream()     // Catch:{ all -> 0x008f }
            r2.<init>(r4)     // Catch:{ all -> 0x008f }
            if (r9 == 0) goto L_0x0060
            java.lang.String r9 = a(r9, r11)     // Catch:{ all -> 0x005d }
            r2.writeBytes(r9)     // Catch:{ all -> 0x005d }
            goto L_0x0060
        L_0x005d:
            r7 = move-exception
            goto L_0x015b
        L_0x0060:
            r2.flush()     // Catch:{ all -> 0x005d }
            int r9 = r1.getResponseCode()     // Catch:{ all -> 0x005d }
            boolean r11 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x005d }
            if (r11 != 0) goto L_0x0108
            java.lang.String r11 = "/hmt?_z=m&jsonp=hmt"
            boolean r8 = r8.contains(r11)     // Catch:{ all -> 0x005d }
            if (r8 == 0) goto L_0x0108
            java.io.BufferedReader r8 = new java.io.BufferedReader     // Catch:{ all -> 0x005d }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ all -> 0x005d }
            java.io.InputStream r4 = r1.getInputStream()     // Catch:{ all -> 0x005d }
            java.lang.String r5 = "utf-8"
            r11.<init>(r4, r5)     // Catch:{ all -> 0x005d }
            r8.<init>(r11)     // Catch:{ all -> 0x005d }
            android.content.Context r7 = r7.getApplicationContext()     // Catch:{ all -> 0x005d }
            a(r7, r8)     // Catch:{ all -> 0x005d }
            goto L_0x0108
        L_0x008f:
            r7 = move-exception
            r2 = r0
            goto L_0x015b
        L_0x0093:
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ all -> 0x0158 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ all -> 0x0158 }
            java.lang.String r2 = "POST"
            r1.setRequestMethod(r2)     // Catch:{ all -> 0x0153 }
            r1.setDoOutput(r3)     // Catch:{ all -> 0x0153 }
            r1.setReadTimeout(r4)     // Catch:{ all -> 0x0153 }
            r1.setConnectTimeout(r4)     // Catch:{ all -> 0x0153 }
            java.lang.String r2 = "X_FORWARDED_FOR"
            java.lang.String r4 = ""
            java.lang.Object r2 = defpackage.ewp.b(r7, r2, r4)     // Catch:{ all -> 0x0153 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0153 }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0153 }
            if (r4 != 0) goto L_0x00bc
            java.lang.String r4 = "X-Forwarded-For"
            r1.addRequestProperty(r4, r2)     // Catch:{ all -> 0x0153 }
        L_0x00bc:
            r1.connect()     // Catch:{ all -> 0x0153 }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ all -> 0x0153 }
            java.io.OutputStream r4 = r1.getOutputStream()     // Catch:{ all -> 0x0153 }
            r2.<init>(r4)     // Catch:{ all -> 0x0153 }
            if (r9 == 0) goto L_0x00d8
            java.lang.String r9 = a(r9, r11)     // Catch:{ all -> 0x00d2 }
            r2.writeBytes(r9)     // Catch:{ all -> 0x00d2 }
            goto L_0x00d8
        L_0x00d2:
            r7 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x015b
        L_0x00d8:
            r2.flush()     // Catch:{ all -> 0x00d2 }
            int r9 = r1.getResponseCode()     // Catch:{ all -> 0x00d2 }
            boolean r11 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x00d2 }
            if (r11 != 0) goto L_0x0105
            java.lang.String r11 = "/hmt?_z=m&jsonp=hmt"
            boolean r8 = r8.contains(r11)     // Catch:{ all -> 0x00d2 }
            if (r8 == 0) goto L_0x0105
            java.io.BufferedReader r8 = new java.io.BufferedReader     // Catch:{ all -> 0x00d2 }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ all -> 0x00d2 }
            java.io.InputStream r4 = r1.getInputStream()     // Catch:{ all -> 0x00d2 }
            java.lang.String r5 = "utf-8"
            r11.<init>(r4, r5)     // Catch:{ all -> 0x00d2 }
            r8.<init>(r11)     // Catch:{ all -> 0x00d2 }
            android.content.Context r7 = r7.getApplicationContext()     // Catch:{ all -> 0x00d2 }
            a(r7, r8)     // Catch:{ all -> 0x00d2 }
        L_0x0105:
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x0108:
            if (r0 == 0) goto L_0x010d
            r0.disconnect()     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
        L_0x010d:
            if (r1 == 0) goto L_0x0112
            r1.disconnect()     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
        L_0x0112:
            java.lang.String r7 = "Status from post is "
            java.lang.String r8 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
            defpackage.euw.a(r7)     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
            r7 = 200(0xc8, float:2.8E-43)
            if (r7 != r9) goto L_0x0141
            defpackage.eve.b(r10)     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
            r2.close()     // Catch:{ IOException -> 0x012a }
            goto L_0x0140
        L_0x012a:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Collected:"
            r8.<init>(r9)
            java.lang.String r7 = r7.getMessage()
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            defpackage.euw.a(r7)
        L_0x0140:
            return r3
        L_0x0141:
            defpackage.eve.c(r10)     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
            r2.close()     // Catch:{ IOException -> 0x0149 }
            goto L_0x01c8
        L_0x0149:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Collected:"
            r8.<init>(r9)
            goto L_0x01ba
        L_0x0153:
            r7 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
            goto L_0x015b
        L_0x0158:
            r7 = move-exception
            r1 = r0
            r2 = r1
        L_0x015b:
            if (r0 == 0) goto L_0x0167
            r0.disconnect()     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
            goto L_0x0167
        L_0x0161:
            r7 = move-exception
            goto L_0x01ca
        L_0x0163:
            r7 = move-exception
            goto L_0x016d
        L_0x0165:
            r7 = move-exception
            goto L_0x0194
        L_0x0167:
            if (r1 == 0) goto L_0x016c
            r1.disconnect()     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
        L_0x016c:
            throw r7     // Catch:{ Exception -> 0x0165, Error -> 0x0163 }
        L_0x016d:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0161 }
            java.lang.String r9 = "Collected:"
            r8.<init>(r9)     // Catch:{ all -> 0x0161 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0161 }
            r8.append(r7)     // Catch:{ all -> 0x0161 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0161 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0161 }
            defpackage.eve.c(r10)     // Catch:{ all -> 0x0161 }
            if (r2 == 0) goto L_0x01c8
            r2.close()     // Catch:{ IOException -> 0x018b }
            goto L_0x01c8
        L_0x018b:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Collected:"
            r8.<init>(r9)
            goto L_0x01ba
        L_0x0194:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0161 }
            java.lang.String r9 = "Collected:"
            r8.<init>(r9)     // Catch:{ all -> 0x0161 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0161 }
            r8.append(r7)     // Catch:{ all -> 0x0161 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x0161 }
            defpackage.euw.a(r7)     // Catch:{ all -> 0x0161 }
            defpackage.eve.c(r10)     // Catch:{ all -> 0x0161 }
            if (r2 == 0) goto L_0x01c8
            r2.close()     // Catch:{ IOException -> 0x01b2 }
            goto L_0x01c8
        L_0x01b2:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Collected:"
            r8.<init>(r9)
        L_0x01ba:
            java.lang.String r7 = r7.getMessage()
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            defpackage.euw.a(r7)
        L_0x01c8:
            r7 = 0
            return r7
        L_0x01ca:
            if (r2 == 0) goto L_0x01e6
            r2.close()     // Catch:{ IOException -> 0x01d0 }
            goto L_0x01e6
        L_0x01d0:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Collected:"
            r9.<init>(r10)
            java.lang.String r8 = r8.getMessage()
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            defpackage.euw.a(r8)
        L_0x01e6:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.evh.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    private static void a(Context context, BufferedReader bufferedReader) {
        StringBuilder sb;
        try {
            String[] split = bufferedReader.readLine().split(",");
            if (split == null || split.length <= 1) {
                ewp.a(context, (String) "hmt_is_in_location", (Object) "0");
            } else {
                String substring = split[1].substring(0, 1);
                if (!TextUtils.isEmpty(substring)) {
                    euw.a("isFit = ".concat(String.valueOf(substring)));
                    ewp.a(context, (String) "hmt_is_in_location", (Object) substring);
                }
            }
            try {
            } catch (IOException e) {
                e = e;
                sb = new StringBuilder("Collected:");
                sb.append(e.getMessage());
                euw.a(sb.toString());
            }
        } catch (IOException e2) {
            StringBuilder sb2 = new StringBuilder("Collected:");
            sb2.append(e2.getMessage());
            euw.a(sb2.toString());
            try {
            } catch (IOException e3) {
                e = e3;
                sb = new StringBuilder("Collected:");
                sb.append(e.getMessage());
                euw.a(sb.toString());
            }
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e4) {
                StringBuilder sb3 = new StringBuilder("Collected:");
                sb3.append(e4.getMessage());
                euw.a(sb3.toString());
            }
        }
    }

    private static String a(String str, String str2) throws IOException {
        String a2 = evk.a(evk.b(str));
        String d = eve.d((String) "3266326432643430353239616363393831323131646435303261343662393333");
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        sb.append(d);
        sb.append(str2);
        String a3 = ewn.a(sb.toString());
        String concat = "contents=".concat(String.valueOf(a2));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(concat);
        sb2.append("&sign=");
        sb2.append(a3);
        return sb2.toString();
    }

    public static boolean a(Context context, evr evr) {
        boolean z = false;
        if (TextUtils.isEmpty(evr.b) || TextUtils.isEmpty(evr.d)) {
            return false;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(evr.b);
            sb.append("?imei=");
            sb.append(Uri.encode(euw.w(context)));
            sb.append("&os_version=");
            sb.append(Uri.encode(euw.b()));
            sb.append("&model=");
            sb.append(Uri.encode(Build.MODEL));
            sb.append("&network=");
            sb.append(Uri.encode(euw.m(context) ? "WIFI" : "OTHERS"));
            sb.append("&pkgname=");
            sb.append(Uri.encode(euw.f(context)));
            sb.append("&appver=");
            sb.append(Uri.encode(euw.n(context)));
            sb.append("&is64bit=");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(euw.L(context));
            sb.append(Uri.encode(sb2.toString()));
            URL url = new URL(sb.toString());
            if (evr.b.startsWith("https")) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.connect();
                if (httpsURLConnection.getResponseCode() == 200) {
                    a(context, evr.a, evr.d, httpsURLConnection);
                    ewp.a(context, (String) "local_so_version", (Object) evr.c);
                }
                return z;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                a(context, evr.a, evr.d, httpURLConnection);
                ewp.a(context, (String) "local_so_version", (Object) evr.c);
            }
            return z;
            z = true;
        } catch (Throwable th) {
            StringBuilder sb3 = new StringBuilder("Collected:");
            sb3.append(th.getMessage());
            euw.a(sb3.toString());
        }
        return z;
    }

    private static void a(Context context, String str, String str2, HttpURLConnection httpURLConnection) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append(str);
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file);
        sb2.append(File.separator);
        sb2.append(str2);
        File file2 = new File(sb2.toString());
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = bufferedInputStream.read(bArr);
            if (read != -1) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                bufferedInputStream.close();
                fileOutputStream.close();
                return;
            }
        }
    }
}
