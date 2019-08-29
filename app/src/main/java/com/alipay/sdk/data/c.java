package com.alipay.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobilesecuritysdk.face.SecurityClientMobile;
import com.alipay.sdk.cons.a;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.k;
import com.alipay.sdk.util.l;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class c {
    private static final String d = "virtualImeiAndImsi";
    private static final String e = "virtual_imei";
    private static final String f = "virtual_imsi";
    private static c g;
    public String a;
    public String b = "sdk-and-lite";
    public String c;

    private static String e() {
        return "1";
    }

    private static String f() {
        return "-1;-1";
    }

    private String d() {
        return this.c;
    }

    private c() {
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            if (g == null) {
                g = new c();
            }
            cVar = g;
        }
        return cVar;
    }

    public final synchronized void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            PreferenceManager.getDefaultSharedPreferences(b.a().a).edit().putString(com.alipay.sdk.cons.b.i, str).commit();
            a.c = str;
        }
    }

    private static String b(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    private String a(com.alipay.sdk.tid.c cVar) {
        String str;
        Context context = b.a().a;
        com.alipay.sdk.util.a a2 = com.alipay.sdk.util.a.a(context);
        if (TextUtils.isEmpty(this.a)) {
            String b2 = l.b();
            String c2 = l.c();
            String f2 = l.f(context);
            String a3 = k.a(context);
            String substring = a3.substring(0, a3.indexOf("://"));
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
            this.a = sb.toString();
        }
        String str2 = com.alipay.sdk.util.a.b(context).p;
        String a4 = a2.a();
        String b3 = a2.b();
        String c3 = c();
        String b4 = b();
        if (cVar != null) {
            this.c = cVar.b();
        }
        String replace = Build.MANUFACTURER.replace(";", Token.SEPARATOR);
        String replace2 = Build.MODEL.replace(";", Token.SEPARATOR);
        boolean b5 = b.b();
        String str3 = a2.a;
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
        Context context2 = context;
        WifiInfo connectionInfo2 = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        if (connectionInfo2 != null) {
            str = connectionInfo2.getBSSID();
        } else {
            str = "00";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.a);
        sb2.append(";");
        sb2.append(str2);
        sb2.append(";");
        sb2.append("-1;-1");
        sb2.append(";");
        sb2.append("1");
        sb2.append(";");
        sb2.append(a4);
        sb2.append(";");
        sb2.append(b3);
        sb2.append(";");
        sb2.append(this.c);
        sb2.append(";");
        sb2.append(replace);
        sb2.append(";");
        sb2.append(replace2);
        sb2.append(";");
        sb2.append(b5);
        sb2.append(";");
        sb2.append(str3);
        sb2.append(";-1;-1;");
        sb2.append(this.b);
        sb2.append(";");
        sb2.append(c3);
        sb2.append(";");
        sb2.append(b4);
        sb2.append(";");
        sb2.append(ssid);
        sb2.append(";");
        sb2.append(str);
        if (cVar != null) {
            HashMap hashMap = new HashMap();
            Context context3 = context2;
            hashMap.put("tid", com.alipay.sdk.tid.c.a(context3).a());
            hashMap.put("utdid", b.a().c());
            String b6 = b(context3, hashMap);
            if (!TextUtils.isEmpty(b6)) {
                sb2.append(";");
                sb2.append(b6);
            }
        }
        sb2.append(")");
        return sb2.toString();
    }

    public static String b() {
        String b2;
        Context context = b.a().a;
        SharedPreferences sharedPreferences = context.getSharedPreferences("virtualImeiAndImsi", 0);
        String string = sharedPreferences.getString("virtual_imei", null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.c.a(context).a())) {
                b2 = g();
            } else {
                b2 = com.alipay.sdk.util.a.a(context).b();
            }
            string = b2;
            sharedPreferences.edit().putString("virtual_imei", string).commit();
        }
        return string;
    }

    public static String c() {
        String str;
        Context context = b.a().a;
        SharedPreferences sharedPreferences = context.getSharedPreferences("virtualImeiAndImsi", 0);
        String string = sharedPreferences.getString("virtual_imsi", null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        if (TextUtils.isEmpty(com.alipay.sdk.tid.c.a(context).a())) {
            String c2 = b.a().c();
            if (TextUtils.isEmpty(c2)) {
                str = g();
            } else {
                str = c2.substring(3, 18);
            }
        } else {
            str = com.alipay.sdk.util.a.a(context).a();
        }
        String str2 = str;
        sharedPreferences.edit().putString("virtual_imsi", str2).commit();
        return str2;
    }

    private static String g() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(hexString);
        sb.append(random.nextInt(9000) + 1000);
        return sb.toString();
    }

    private static String c(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getSSID() : "-1";
    }

    private static String d(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getBSSID() : "00";
    }

    /* access modifiers changed from: 0000 */
    public static String a(Context context, HashMap<String, String> hashMap) {
        String str;
        try {
            str = SecurityClientMobile.GetApdid(context, hashMap);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a((String) com.alipay.sdk.app.statistic.c.e, (String) com.alipay.sdk.app.statistic.c.h, th);
            str = "";
        }
        if (TextUtils.isEmpty(str)) {
            com.alipay.sdk.app.statistic.a.a((String) com.alipay.sdk.app.statistic.c.e, (String) com.alipay.sdk.app.statistic.c.i, (String) "apdid == null");
        }
        return str;
    }

    public final String b(Context context, HashMap<String, String> hashMap) {
        try {
            return (String) Executors.newFixedThreadPool(2).submit(new d(this, context, hashMap)).get(BalloonLayout.DEFAULT_DISPLAY_DURATION, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a((String) com.alipay.sdk.app.statistic.c.e, (String) com.alipay.sdk.app.statistic.c.j, th);
            r4 = "";
            return "";
        }
    }

    public static String a(Context context) {
        if (context != null) {
            try {
                StringBuilder sb = new StringBuilder();
                String packageName = context.getPackageName();
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                sb.append("(");
                sb.append(packageName);
                sb.append(";");
                sb.append(packageInfo.versionCode);
                sb.append(")");
                return sb.toString();
            } catch (Exception unused) {
            }
        }
        return "";
    }
}
