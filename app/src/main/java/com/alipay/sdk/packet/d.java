package com.alipay.sdk.packet;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.net.a;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.tid.c;
import com.alipay.sdk.util.k;
import com.alipay.sdk.util.l;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class d {
    public static final String a = "msp-gzip";
    public static final String b = "Msp-Param";
    public static final String c = "Operation-Type";
    public static final String d = "content-type";
    public static final String e = "Version";
    public static final String f = "AppId";
    public static final String g = "des-mode";
    public static final String h = "namespace";
    public static final String i = "api_name";
    public static final String j = "api_version";
    public static final String k = "data";
    public static final String l = "params";
    public static final String m = "public_key";
    public static final String n = "device";
    public static final String o = "action";
    public static final String p = "type";
    public static final String q = "method";
    private static a t;
    protected boolean r = true;
    protected boolean s = true;

    public abstract JSONObject a() throws JSONException;

    public String b() {
        return "4.9.0";
    }

    public List<Header> a(boolean z, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(a, String.valueOf(z)));
        arrayList.add(new BasicHeader("Operation-Type", "alipay.msp.cashier.dispatch.bytes"));
        arrayList.add(new BasicHeader("content-type", FilePart.DEFAULT_CONTENT_TYPE));
        arrayList.add(new BasicHeader("Version", "2.0"));
        arrayList.add(new BasicHeader("AppId", "TAOBAO"));
        arrayList.add(new BasicHeader(b, a.a(str)));
        arrayList.add(new BasicHeader(g, "CBC"));
        return arrayList;
    }

    public String c() throws JSONException {
        HashMap hashMap = new HashMap();
        hashMap.put(n, Build.MODEL);
        hashMap.put("namespace", "com.alipay.mobilecashier");
        hashMap.put(i, "com.alipay.mcpay");
        hashMap.put(j, b());
        return a(hashMap, new HashMap<>());
    }

    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("type", str);
        jSONObject2.put("method", str2);
        jSONObject.put("action", jSONObject2);
        return jSONObject;
    }

    public String a(String str, JSONObject jSONObject) {
        JSONObject jSONObject2;
        String str2;
        b a2 = b.a();
        c a3 = c.a(a2.a);
        JSONObject a4 = com.alipay.sdk.util.b.a(new JSONObject(), jSONObject);
        try {
            a4.put("tid", a3.a());
            com.alipay.sdk.data.c a5 = com.alipay.sdk.data.c.a();
            Context context = b.a().a;
            com.alipay.sdk.util.a a6 = com.alipay.sdk.util.a.a(context);
            if (TextUtils.isEmpty(a5.a)) {
                String b2 = l.b();
                String c2 = l.c();
                String f2 = l.f(context);
                String a7 = k.a(context);
                String substring = a7.substring(0, a7.indexOf("://"));
                String g2 = l.g(context);
                String f3 = Float.toString(new TextView(context).getTextSize());
                StringBuilder sb = new StringBuilder();
                sb.append("Msp/15.5.3");
                sb.append(" (");
                sb.append(b2);
                sb.append(";");
                sb.append(c2);
                sb.append(";");
                sb.append(f2);
                sb.append(";");
                sb.append(substring);
                sb.append(";");
                sb.append(g2);
                sb.append(";");
                sb.append(f3);
                a5.a = sb.toString();
            }
            String str3 = com.alipay.sdk.util.a.b(context).p;
            String a8 = a6.a();
            String b3 = a6.b();
            String c3 = com.alipay.sdk.data.c.c();
            String b4 = com.alipay.sdk.data.c.b();
            if (a3 != null) {
                a5.c = a3.b();
            }
            b bVar = a2;
            String replace = Build.MANUFACTURER.replace(";", Token.SEPARATOR);
            JSONObject jSONObject3 = a4;
            try {
                String replace2 = Build.MODEL.replace(";", Token.SEPARATOR);
                boolean b5 = b.b();
                String str4 = a6.a;
                r18 = com.alipay.sdk.cons.b.b;
                WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
                String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
                Context context2 = context;
                WifiInfo connectionInfo2 = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
                if (connectionInfo2 != null) {
                    str2 = connectionInfo2.getBSSID();
                } else {
                    str2 = "00";
                }
                StringBuilder sb2 = new StringBuilder();
                c cVar = a3;
                sb2.append(a5.a);
                sb2.append(";");
                sb2.append(str3);
                sb2.append(";");
                sb2.append("-1;-1");
                sb2.append(";");
                sb2.append("1");
                sb2.append(";");
                sb2.append(a8);
                sb2.append(";");
                sb2.append(b3);
                sb2.append(";");
                sb2.append(a5.c);
                sb2.append(";");
                sb2.append(replace);
                sb2.append(";");
                sb2.append(replace2);
                sb2.append(";");
                sb2.append(b5);
                sb2.append(";");
                sb2.append(str4);
                sb2.append(";-1;-1;");
                sb2.append(a5.b);
                sb2.append(";");
                sb2.append(c3);
                sb2.append(";");
                sb2.append(b4);
                sb2.append(";");
                sb2.append(ssid);
                sb2.append(";");
                sb2.append(str2);
                if (cVar != null) {
                    HashMap hashMap = new HashMap();
                    Context context3 = context2;
                    hashMap.put("tid", c.a(context3).a());
                    hashMap.put("utdid", b.a().c());
                    String b6 = a5.b(context3, hashMap);
                    if (!TextUtils.isEmpty(b6)) {
                        sb2.append(";");
                        sb2.append(b6);
                    }
                }
                sb2.append(")");
                String sb3 = sb2.toString();
                jSONObject2 = jSONObject3;
                r2 = com.alipay.sdk.cons.b.b;
                try {
                    jSONObject2.put(com.alipay.sdk.cons.b.b, sb3);
                    b bVar2 = bVar;
                    jSONObject2.put(com.alipay.sdk.cons.b.e, l.c(bVar2.a));
                    jSONObject2.put(com.alipay.sdk.cons.b.f, l.b(bVar2.a));
                    jSONObject2.put(com.alipay.sdk.cons.b.d, str);
                    jSONObject2.put(com.alipay.sdk.cons.b.h, com.alipay.sdk.cons.a.d);
                    jSONObject2.put("utdid", bVar2.c());
                    jSONObject2.put(com.alipay.sdk.cons.b.j, cVar.b());
                    com.alipay.sdk.data.c.a();
                    jSONObject2.put("pa", com.alipay.sdk.data.c.a(bVar2.a));
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
                jSONObject2 = jSONObject3;
            }
        } catch (Throwable unused3) {
            jSONObject2 = a4;
        }
        return jSONObject2.toString();
    }

    private static String a(HttpResponse httpResponse, String str) {
        String str2 = null;
        if (httpResponse == null) {
            return null;
        }
        Header[] allHeaders = httpResponse.getAllHeaders();
        if (allHeaders != null && allHeaders.length > 0) {
            int length = allHeaders.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Header header = allHeaders[i2];
                if (header != null) {
                    String name = header.getName();
                    if (name != null && name.equalsIgnoreCase(str)) {
                        str2 = header.getValue();
                        break;
                    }
                }
                i2++;
            }
        }
        return str2;
    }

    public static String a(HashMap<String, String> hashMap, HashMap<String, String> hashMap2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        for (Entry next : hashMap.entrySet()) {
            jSONObject2.put((String) next.getKey(), next.getValue());
        }
        JSONObject jSONObject3 = new JSONObject();
        for (Entry next2 : hashMap2.entrySet()) {
            jSONObject3.put((String) next2.getKey(), next2.getValue());
        }
        jSONObject2.put("params", jSONObject3);
        jSONObject.put("data", jSONObject2);
        return jSONObject.toString();
    }

    private static boolean a(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("data");
            if (!jSONObject.has("params")) {
                return false;
            }
            String optString = jSONObject.getJSONObject("params").optString(m, null);
            if (!TextUtils.isEmpty(optString)) {
                b.a();
                com.alipay.sdk.data.c.a().a(optString);
                z = true;
            }
            return z;
        } catch (JSONException unused) {
        }
    }

    private static a b(Context context, String str) {
        if (t == null) {
            t = new a(context, str);
        } else if (!TextUtils.equals(str, t.b)) {
            t.b = str;
        }
        return t;
    }

    public final b a(Context context) throws Throwable {
        return a(context, "", k.a(context), true);
    }

    public b a(Context context, String str) throws Throwable {
        return a(context, str, k.a(context), true);
    }

    private b a(Context context, String str, String str2) throws Throwable {
        return a(context, str, str2, true);
    }

    public final b a(Context context, String str, String str2, boolean z) throws Throwable {
        e eVar = new e(this.s);
        c a2 = eVar.a(new b(c(), a(str, a())), this.r);
        if (t == null) {
            t = new a(context, str2);
        } else if (!TextUtils.equals(str2, t.b)) {
            t.b = str2;
        }
        HttpResponse a3 = t.a(a2.b, a(a2.a, str));
        String str3 = null;
        if (a3 != null) {
            Header[] allHeaders = a3.getAllHeaders();
            if (allHeaders != null && allHeaders.length > 0) {
                int length = allHeaders.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    Header header = allHeaders[i2];
                    if (header != null) {
                        String name = header.getName();
                        if (name != null && name.equalsIgnoreCase(a)) {
                            str3 = header.getValue();
                            break;
                        }
                    }
                    i2++;
                }
            }
        }
        b a4 = eVar.a(new c(Boolean.valueOf(str3).booleanValue(), b(a3)));
        return (a4 == null || !a(a4.a) || !z) ? a4 : a(context, str, str2, false);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:3|4|(3:5|6|(1:8)(1:31))|9|(2:11|12)|13|14|15) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0027 */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0034 A[SYNTHETIC, Splitter:B:23:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0039 A[SYNTHETIC, Splitter:B:27:0x0039] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(org.apache.http.HttpResponse r4) throws java.lang.IllegalStateException, java.io.IOException {
        /*
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            r1 = 0
            org.apache.http.HttpEntity r4 = r4.getEntity()     // Catch:{ all -> 0x0030 }
            java.io.InputStream r4 = r4.getContent()     // Catch:{ all -> 0x0030 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x002e }
            r2.<init>()     // Catch:{ all -> 0x002e }
        L_0x0012:
            int r1 = r4.read(r0)     // Catch:{ all -> 0x002b }
            r3 = -1
            if (r1 == r3) goto L_0x001e
            r3 = 0
            r2.write(r0, r3, r1)     // Catch:{ all -> 0x002b }
            goto L_0x0012
        L_0x001e:
            byte[] r0 = r2.toByteArray()     // Catch:{ all -> 0x002b }
            if (r4 == 0) goto L_0x0027
            r4.close()     // Catch:{ Exception -> 0x0027 }
        L_0x0027:
            r2.close()     // Catch:{ Exception -> 0x002a }
        L_0x002a:
            return r0
        L_0x002b:
            r0 = move-exception
            r1 = r2
            goto L_0x0032
        L_0x002e:
            r0 = move-exception
            goto L_0x0032
        L_0x0030:
            r0 = move-exception
            r4 = r1
        L_0x0032:
            if (r4 == 0) goto L_0x0037
            r4.close()     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            if (r1 == 0) goto L_0x003c
            r1.close()     // Catch:{ Exception -> 0x003c }
        L_0x003c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.d.b(org.apache.http.HttpResponse):byte[]");
    }

    private static boolean a(HttpResponse httpResponse) {
        String str = null;
        if (httpResponse != null) {
            Header[] allHeaders = httpResponse.getAllHeaders();
            if (allHeaders != null && allHeaders.length > 0) {
                int length = allHeaders.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    Header header = allHeaders[i2];
                    if (header != null) {
                        String name = header.getName();
                        if (name != null && name.equalsIgnoreCase(a)) {
                            str = header.getValue();
                            break;
                        }
                    }
                    i2++;
                }
            }
        }
        return Boolean.valueOf(str).booleanValue();
    }
}
