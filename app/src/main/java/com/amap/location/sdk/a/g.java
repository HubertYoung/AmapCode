package com.amap.location.sdk.a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SocketServerHelper */
public class g {
    /* access modifiers changed from: private */
    public static final byte[] f = {66, 77, 66, 0, 0, 0, 0, 0, 0, 0, 62, 0, 0, 0, 40, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 116, 18, 0, 0, 116, 18, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, Byte.MIN_VALUE, 0, 0, 0};
    a a;
    List<String> b = new ArrayList();
    com.amap.location.a.b.a c = new com.amap.location.a.b.a() {
        public void a() {
        }

        public void a(com.amap.location.a.a aVar) {
            try {
                JSONArray optJSONArray = new JSONObject(aVar.a()).optJSONObject("l").optJSONArray("h5w");
                for (int i = 0; i < optJSONArray.length(); i++) {
                    String optString = optJSONArray.optString(i);
                    if (!TextUtils.isEmpty(optString)) {
                        g.this.b.add(optString);
                    }
                }
            } catch (Throwable unused) {
            }
        }
    };
    /* access modifiers changed from: private */
    public Context d;
    private b e;
    /* access modifiers changed from: private */
    public com.amap.location.a.b g;

    /* compiled from: SocketServerHelper */
    public interface a {
        JSONObject a();
    }

    /* compiled from: SocketServerHelper */
    class b extends Thread {
        private ServerSocket b;
        private boolean c = true;
        private boolean d = false;
        private String e = "com.sinber.guidedemo";

        public b(String str) {
            super(str);
        }

        public void a() {
            try {
                this.c = false;
                this.b.close();
                g.this.b.clear();
                this.b = null;
                if (g.this.g != null) {
                    g.this.g.b(g.this.c);
                }
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:73:0x00f5 A[SYNTHETIC, Splitter:B:73:0x00f5] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r8 = this;
                r0 = 0
                java.net.ServerSocket r1 = new java.net.ServerSocket     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                r1.<init>()     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                r8.b = r1     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                java.lang.String r1 = "127.0.0.1"
                java.net.InetSocketAddress r2 = new java.net.InetSocketAddress     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                r3 = 6677(0x1a15, float:9.356E-42)
                r2.<init>(r1, r3)     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                java.net.ServerSocket r1 = r8.b     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                r1.bind(r2)     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                r1 = 1
                r8.c = r1     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
            L_0x0019:
                boolean r2 = r8.c     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                if (r2 == 0) goto L_0x00d0
                java.net.ServerSocket r2 = r8.b     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                java.net.Socket r2 = r2.accept()     // Catch:{ Throwable -> 0x00e0, all -> 0x00dc }
                java.io.InputStream r0 = r2.getInputStream()     // Catch:{ Throwable -> 0x00ce }
                if (r0 != 0) goto L_0x0035
                if (r2 == 0) goto L_0x0034
                r2.close()     // Catch:{ Exception -> 0x002f }
                goto L_0x0034
            L_0x002f:
                r0 = move-exception
                com.amap.location.common.d.a.a(r0)
                return
            L_0x0034:
                return
            L_0x0035:
                java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00ce }
                r3.<init>(r0)     // Catch:{ Throwable -> 0x00ce }
                java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00ce }
                r4 = 512(0x200, float:7.175E-43)
                r0.<init>(r3, r4)     // Catch:{ Throwable -> 0x00ce }
                java.lang.String r3 = r0.readLine()     // Catch:{ Throwable -> 0x00ce }
                java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ Throwable -> 0x00ce }
                boolean r5 = r8.c     // Catch:{ Throwable -> 0x00ce }
                if (r5 == 0) goto L_0x00bb
                if (r3 == 0) goto L_0x00bb
                java.lang.String r5 = r0.readLine()     // Catch:{ Throwable -> 0x00ce }
            L_0x0053:
                r6 = 0
                if (r5 == 0) goto L_0x0079
                java.lang.String r7 = ""
                boolean r7 = r7.equals(r5)     // Catch:{ Throwable -> 0x00ce }
                if (r7 != 0) goto L_0x0079
                java.util.Locale r7 = java.util.Locale.ENGLISH     // Catch:{ Throwable -> 0x00ce }
                java.lang.String r5 = r5.toLowerCase(r7)     // Catch:{ Throwable -> 0x00ce }
                java.lang.String r7 = "referer"
                boolean r7 = r5.contains(r7)     // Catch:{ Throwable -> 0x00ce }
                if (r7 == 0) goto L_0x0074
                boolean r5 = r8.a(r5)     // Catch:{ Throwable -> 0x00ce }
                if (r5 == 0) goto L_0x0079
                r6 = 1
                goto L_0x0079
            L_0x0074:
                java.lang.String r5 = r0.readLine()     // Catch:{ Throwable -> 0x00ce }
                goto L_0x0053
            L_0x0079:
                java.lang.String r5 = r8.a(r3, r6)     // Catch:{ Throwable -> 0x00ce }
                java.lang.String r6 = "isHttps=true"
                boolean r3 = r3.contains(r6)     // Catch:{ Throwable -> 0x00ce }
                if (r3 == 0) goto L_0x00a3
                boolean r3 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x00ce }
                if (r3 != 0) goto L_0x00bb
                java.lang.String r3 = "\"error_code\":0"
                boolean r3 = r5.contains(r3)     // Catch:{ Throwable -> 0x00ce }
                if (r3 == 0) goto L_0x00bb
                java.io.PrintStream r3 = new java.io.PrintStream     // Catch:{ Throwable -> 0x00ce }
                r3.<init>(r4, r1)     // Catch:{ Throwable -> 0x00ce }
                byte[] r5 = com.amap.location.sdk.a.g.f     // Catch:{ Throwable -> 0x00ce }
                r3.write(r5)     // Catch:{ Throwable -> 0x00ce }
                r3.close()     // Catch:{ Throwable -> 0x00ce }
                goto L_0x00bb
            L_0x00a3:
                java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x00ce }
                r3.<init>(r4)     // Catch:{ Throwable -> 0x00ce }
                java.lang.String r6 = "HTTP/1.1 200 OK"
                r3.println(r6)     // Catch:{ Throwable -> 0x00ce }
                java.lang.String r6 = "Content-Type:text/javascript;charset=UTF-8"
                r3.println(r6)     // Catch:{ Throwable -> 0x00ce }
                r3.println()     // Catch:{ Throwable -> 0x00ce }
                r3.println(r5)     // Catch:{ Throwable -> 0x00ce }
                r3.close()     // Catch:{ Throwable -> 0x00ce }
            L_0x00bb:
                r4.close()     // Catch:{ Exception -> 0x00bf }
                goto L_0x00c3
            L_0x00bf:
                r3 = move-exception
                com.amap.location.common.d.a.a(r3)     // Catch:{ Throwable -> 0x00ce }
            L_0x00c3:
                r0.close()     // Catch:{ Exception -> 0x00c7 }
                goto L_0x00cb
            L_0x00c7:
                r0 = move-exception
                com.amap.location.common.d.a.a(r0)     // Catch:{ Throwable -> 0x00ce }
            L_0x00cb:
                r0 = r2
                goto L_0x0019
            L_0x00ce:
                r0 = move-exception
                goto L_0x00e3
            L_0x00d0:
                if (r0 == 0) goto L_0x00db
                r0.close()     // Catch:{ Exception -> 0x00d6 }
                goto L_0x00db
            L_0x00d6:
                r0 = move-exception
                com.amap.location.common.d.a.a(r0)
                return
            L_0x00db:
                return
            L_0x00dc:
                r1 = move-exception
                r2 = r0
                r0 = r1
                goto L_0x00f3
            L_0x00e0:
                r1 = move-exception
                r2 = r0
                r0 = r1
            L_0x00e3:
                com.amap.location.common.d.a.a(r0)     // Catch:{ all -> 0x00f2 }
                if (r2 == 0) goto L_0x00f1
                r2.close()     // Catch:{ Exception -> 0x00ec }
                goto L_0x00f1
            L_0x00ec:
                r0 = move-exception
                com.amap.location.common.d.a.a(r0)
                return
            L_0x00f1:
                return
            L_0x00f2:
                r0 = move-exception
            L_0x00f3:
                if (r2 == 0) goto L_0x00fd
                r2.close()     // Catch:{ Exception -> 0x00f9 }
                goto L_0x00fd
            L_0x00f9:
                r1 = move-exception
                com.amap.location.common.d.a.a(r1)
            L_0x00fd:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.g.b.run():void");
        }

        public boolean a(String str) {
            for (String contains : g.this.b) {
                if (str.contains(contains)) {
                    return true;
                }
            }
            return false;
        }

        private String a(String str, boolean z) throws Exception {
            String str2;
            if (com.amap.location.common.a.d() > 28) {
                return a(460, (String) ">= Android Q");
            }
            if (str == null || !z) {
                return a(500, (String) "Bad Request");
            }
            Properties properties = new Properties();
            StringTokenizer stringTokenizer = new StringTokenizer(str);
            String nextToken = stringTokenizer.nextToken();
            String nextToken2 = stringTokenizer.nextToken();
            int indexOf = nextToken2.indexOf(63);
            if (indexOf <= 0) {
                return a(402, (String) "Bad Request");
            }
            String substring = nextToken2.substring(0, indexOf);
            if (substring.startsWith("/")) {
                substring = substring.substring(1, substring.length());
            }
            String substring2 = nextToken2.substring(indexOf + 1);
            g.this.a(substring2, properties);
            String property = properties.getProperty("token");
            if (!"GET".equalsIgnoreCase(nextToken)) {
                str2 = a(401, (String) "Bad Request!");
            } else if (substring != null) {
                String str3 = null;
                if (str.contains("uri=")) {
                    str3 = URLDecoder.decode(str.substring(str.indexOf("uri=") + 4, str.indexOf("&")), "GBK");
                }
                if (!this.d || this.e.equals(property)) {
                    str2 = a(substring, properties, substring2, str3);
                } else {
                    str2 = a(403, (String) "Forbidden!");
                }
            } else {
                str2 = a(403, (String) "Forbidden!");
            }
            String property2 = properties.getProperty("callback");
            if (TextUtils.isEmpty(property2)) {
                return str2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(property2);
            sb.append("(");
            sb.append(str2);
            sb.append(")");
            return sb.toString();
        }

        private String a(int i, String str) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("error_code", i);
                jSONObject.put("error", str);
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
            return jSONObject.toString();
        }

        private String a(String str, Properties properties, String str2, String str3) {
            JSONObject jSONObject = new JSONObject();
            String packageName = g.this.d.getPackageName();
            try {
                a(jSONObject, packageName);
            } catch (Exception unused) {
                try {
                    jSONObject.put("package", packageName);
                    jSONObject.put("version", "");
                    jSONObject.put("version_code", -1);
                    jSONObject.put("error_code", -1);
                    jSONObject.put("error", "unknown packagename: ".concat(String.valueOf(packageName)));
                } catch (Exception e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                }
            }
            if (!"getpackageinfo".equals(str)) {
                if ("androidamap".equals(str)) {
                    g.this.a(properties.getProperty("action"), str, str2);
                } else if (!TextUtils.isEmpty(str3)) {
                    g.this.b(str3);
                } else if ("geolocation".equals(str)) {
                    jSONObject.put("location", g.this.a.a());
                } else {
                    jSONObject.put("error_code", -1);
                    jSONObject.put("error", "unknown method: ".concat(String.valueOf(str)));
                }
            }
            return jSONObject.toString();
        }

        private void a(JSONObject jSONObject, String str) throws NameNotFoundException, JSONException {
            PackageInfo packageInfo = g.this.d.getPackageManager().getPackageInfo(str, 0);
            String str2 = packageInfo.versionName;
            int i = packageInfo.versionCode;
            jSONObject.put("package", str);
            jSONObject.put("version", str2);
            jSONObject.put("version_code", i);
            jSONObject.put("error_code", 0);
            jSONObject.put("error", "");
        }
    }

    public g(Context context, a aVar) {
        this.d = context;
        this.a = aVar;
        this.g = com.amap.location.a.b.a();
        d();
    }

    private void d() {
        this.b.add("http://wb.testing.amap.com");
        this.b.add("http://group.myamap.com/");
        this.b.add("amap.com");
        this.b.add("http://m.map.so.com");
        this.b.add("http://114.247.50.32");
        this.b.add("http://180.96.64.225/mo");
    }

    public void a() {
        if (this.e == null) {
            this.e = new b("ServerSocketThread");
            this.e.start();
            if (this.g != null) {
                this.g.a(this.c);
            }
        }
    }

    public void b() {
        if (this.e != null) {
            this.e.a();
            this.e = null;
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, Properties properties) throws InterruptedException {
        if (str != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                if (!TextUtils.isEmpty(nextToken)) {
                    int indexOf = nextToken.indexOf(61);
                    if (indexOf >= 0) {
                        String a2 = a(nextToken.substring(0, indexOf));
                        if (!TextUtils.isEmpty(a2)) {
                            properties.put(a2.trim(), a(nextToken.substring(indexOf + 1)));
                        }
                    } else {
                        String a3 = a(nextToken);
                        if (!TextUtils.isEmpty(a3)) {
                            properties.put(a3.trim(), "");
                        }
                    }
                }
            }
        }
    }

    private String a(String str) throws InterruptedException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if (charAt == '%') {
                    byteArrayOutputStream.write(Integer.parseInt(str.substring(i + 1, i + 3), 16));
                    i += 2;
                } else if (charAt != '+') {
                    byteArrayOutputStream.write(charAt);
                } else {
                    byteArrayOutputStream.write(32);
                }
                i++;
            }
            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2, String str3) {
        Intent intent = new Intent("com.autonavi.minimap.Intent.Action");
        intent.putExtra("method", str2);
        intent.putExtra("action", str);
        intent.putExtra("params", str3);
        intent.setPackage("com.autonavi.minimap");
        this.d.sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setPackage(this.d.getPackageName());
        intent.setData(Uri.parse(str));
        intent.setComponent(null);
        intent.setFlags(268435456);
        this.d.startActivity(intent);
    }
}
