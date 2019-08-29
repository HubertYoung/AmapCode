package com.taobao.applink.h;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class d {
    public CharSequence a = "unkown";
    private CharSequence b = "https://wgo.mmstat.com/ire.2.1";
    private List c = new ArrayList(30);
    private CharSequence d;

    public d(Context context, CharSequence charSequence) {
        try {
            this.d = charSequence;
            this.a = com.taobao.applink.util.d.a(context);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c9  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.String r3 = "logtype=2&wappkey="
            r0.append(r3)
            java.lang.CharSequence r3 = r5.d
            r0.append(r3)
            java.lang.String r3 = "&packagename="
            r0.append(r3)
            android.app.Application r3 = com.taobao.applink.util.TBAppLinkUtil.getApplication()
            java.lang.String r3 = com.taobao.applink.b.a.a(r3)
            r0.append(r3)
            java.lang.String r3 = "&os=android&t="
            r0.append(r3)
            r0.append(r1)
            java.lang.String r1 = "&type="
            r0.append(r1)
            java.util.List r1 = r5.c
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x0044
            java.util.List r1 = r5.c
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            r0.append(r1)
        L_0x0044:
            java.util.List r1 = r5.c
            r1.clear()
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x00c3 }
            java.lang.CharSequence r3 = r5.b     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x00c3 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00c3 }
            java.net.URLConnection r2 = r2.openConnection()     // Catch:{ Throwable -> 0x00c3 }
            java.net.HttpURLConnection r2 = (java.net.HttpURLConnection) r2     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r1 = "POST"
            r2.setRequestMethod(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r1 = 1
            r2.setDoOutput(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r2.setDoInput(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r1 = 5000(0x1388, float:7.006E-42)
            r2.setConnectTimeout(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r2.setReadTimeout(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.io.OutputStream r1 = r2.getOutputStream()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.lang.String r3 = "UTF-8"
            java.lang.String r0 = java.net.URLEncoder.encode(r0, r3)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            byte[] r0 = r0.getBytes()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r1.write(r0)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r1.flush()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r1.close()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.io.InputStream r3 = r2.getInputStream()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.lang.String r1 = ""
        L_0x009a:
            java.lang.String r3 = r0.readLine()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            if (r3 == 0) goto L_0x00b5
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r4.<init>()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r4.append(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.lang.String r1 = "\n"
            r4.append(r1)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            r4.append(r3)     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            java.lang.String r1 = r4.toString()     // Catch:{ Throwable -> 0x00bd, all -> 0x00bb }
            goto L_0x009a
        L_0x00b5:
            if (r2 == 0) goto L_0x00cc
            r2.disconnect()
            return
        L_0x00bb:
            r0 = move-exception
            goto L_0x00cd
        L_0x00bd:
            r0 = move-exception
            r1 = r2
            goto L_0x00c4
        L_0x00c0:
            r0 = move-exception
            r2 = r1
            goto L_0x00cd
        L_0x00c3:
            r0 = move-exception
        L_0x00c4:
            r0.getStackTrace()     // Catch:{ all -> 0x00c0 }
            if (r1 == 0) goto L_0x00cc
            r1.disconnect()
        L_0x00cc:
            return
        L_0x00cd:
            if (r2 == 0) goto L_0x00d2
            r2.disconnect()
        L_0x00d2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.applink.h.d.a():void");
    }

    public void a(CharSequence charSequence) {
        synchronized (this) {
            this.c.add(charSequence);
        }
    }

    public void b(CharSequence charSequence) {
        synchronized (this) {
            a(charSequence);
            try {
                new Thread(new e(this)).start();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}
