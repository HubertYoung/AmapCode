package com.alipay.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.i;
import com.alipay.sdk.app.j;
import com.alipay.sdk.app.statistic.c;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SetJavaScriptEnabled", "DefaultLocale"})
public final class l {
    static final String a = "com.alipay.android.app";
    public static final int b = 99;
    public static final int c = 73;
    private static final String d = "com.eg.android.AlipayGphone";
    private static final String e = "com.eg.android.AlipayGphoneRC";

    public static class a {
        public Signature[] a;
        public int b;

        public final boolean a() {
            boolean z = false;
            if (this.a == null || this.a.length <= 0) {
                return false;
            }
            int i = 0;
            while (true) {
                if (i >= this.a.length) {
                    break;
                }
                String a2 = l.a(this.a[i].toByteArray());
                if (a2 != null && !TextUtils.equals(a2, com.alipay.sdk.cons.a.h)) {
                    com.alipay.sdk.app.statistic.a.a((String) c.b, (String) c.u, a2);
                    z = true;
                    break;
                }
                i++;
            }
            return z;
        }
    }

    private static String f() {
        return "-1;-1";
    }

    public static String a() {
        return EnvUtils.isSandBox() ? e : "com.eg.android.AlipayGphone";
    }

    public static Map<String, String> a(String str) {
        String[] split;
        HashMap hashMap = new HashMap();
        for (String str2 : str.split("&")) {
            int indexOf = str2.indexOf("=", 1);
            hashMap.put(str2.substring(0, indexOf), URLDecoder.decode(str2.substring(indexOf + 1)));
        }
        return hashMap;
    }

    public static String a(String str, String str2, String str3) {
        try {
            int indexOf = str3.indexOf(str) + str.length();
            if (indexOf <= str.length()) {
                return "";
            }
            int i = 0;
            if (!TextUtils.isEmpty(str2)) {
                i = str3.indexOf(str2, indexOf);
            }
            if (i <= 0) {
                return str3.substring(indexOf);
            }
            return str3.substring(indexOf, i);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String b(String str, String str2, String str3) {
        try {
            int indexOf = str3.indexOf(str) + str.length();
            int i = 0;
            if (!TextUtils.isEmpty(str2)) {
                i = str3.indexOf(str2, indexOf);
            }
            if (i <= 0) {
                return str3.substring(indexOf);
            }
            return str3.substring(indexOf, i);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String a(byte[] bArr) {
        try {
            String obj = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey().toString();
            if (obj.indexOf("modulus") != -1) {
                return obj.substring(obj.indexOf("modulus") + 8, obj.lastIndexOf(",")).trim();
            }
        } catch (Exception e2) {
            com.alipay.sdk.app.statistic.a.a((String) "auth", (String) c.o, (Throwable) e2);
        }
        return null;
    }

    public static a a(Context context) {
        return a(context, a());
    }

    private static boolean a(PackageInfo packageInfo) {
        String str = "";
        boolean z = false;
        if (packageInfo == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("info == null");
            str = sb.toString();
        } else if (packageInfo.signatures == null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("info.signatures == null");
            str = sb2.toString();
        } else if (packageInfo.signatures.length <= 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("info.signatures.length <= 0");
            str = sb3.toString();
        } else {
            z = true;
        }
        if (!z) {
            com.alipay.sdk.app.statistic.a.a((String) "auth", (String) c.m, str);
        }
        return z;
    }

    private static PackageInfo b(Context context, String str) throws NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    private static PackageInfo c(Context context, String str) {
        for (PackageInfo next : context.getPackageManager().getInstalledPackages(192)) {
            if (next.packageName.equals(str)) {
                return next;
            }
        }
        return null;
    }

    private static a b(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return null;
        }
        a aVar = new a();
        aVar.a = packageInfo.signatures;
        aVar.b = packageInfo.versionCode;
        return aVar;
    }

    public static boolean b(Context context) {
        try {
            if (context.getPackageManager().getPackageInfo(a, 128) == null) {
                return false;
            }
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean c(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo != null && packageInfo.versionCode > 73) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a((String) c.b, (String) c.C, th);
            return false;
        }
    }

    public static boolean d(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo != null && packageInfo.versionCode < 99) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String e(Context context) {
        String b2 = b();
        String c2 = c();
        String f = f(context);
        String g = g(context);
        StringBuilder sb = new StringBuilder(" (");
        sb.append(b2);
        sb.append(";");
        sb.append(c2);
        sb.append(";");
        sb.append(f);
        sb.append(";;");
        sb.append(g);
        sb.append(")(sdk android)");
        return sb.toString();
    }

    public static String b() {
        StringBuilder sb = new StringBuilder("Android ");
        sb.append(VERSION.RELEASE);
        return sb.toString();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:0|(1:2)|3|(3:5|6|(1:8))|9|11|12|13|14|(1:16)|17|(1:20)|21|22) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x00c6 */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00d5 A[Catch:{ Throwable -> 0x00f1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.webkit.WebView a(android.app.Activity r5, java.lang.String r6, java.lang.String r7) {
        /*
            android.content.Context r0 = r5.getApplicationContext()
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 != 0) goto L_0x001f
            android.webkit.CookieSyncManager r1 = android.webkit.CookieSyncManager.createInstance(r0)
            r1.sync()
            android.webkit.CookieManager r1 = android.webkit.CookieManager.getInstance()
            r1.setCookie(r6, r7)
            android.webkit.CookieSyncManager r7 = android.webkit.CookieSyncManager.getInstance()
            r7.sync()
        L_0x001f:
            android.widget.LinearLayout r7 = new android.widget.LinearLayout
            r7.<init>(r0)
            android.widget.LinearLayout$LayoutParams r1 = new android.widget.LinearLayout$LayoutParams
            r2 = -1
            r1.<init>(r2, r2)
            r2 = 1
            r7.setOrientation(r2)
            r5.setContentView(r7, r1)
            android.webkit.WebView r5 = new android.webkit.WebView
            r5.<init>(r0)
            r3 = 1065353216(0x3f800000, float:1.0)
            r1.weight = r3
            r3 = 0
            r5.setVisibility(r3)
            r7.addView(r5, r1)
            android.webkit.WebSettings r7 = r5.getSettings()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = r7.getUserAgentString()
            r1.append(r4)
            java.lang.String r4 = e(r0)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r7.setUserAgentString(r1)
            android.webkit.WebSettings$RenderPriority r1 = android.webkit.WebSettings.RenderPriority.HIGH
            r7.setRenderPriority(r1)
            r7.setSupportMultipleWindows(r2)
            r7.setJavaScriptEnabled(r2)
            r7.setSavePassword(r3)
            r7.setJavaScriptCanOpenWindowsAutomatically(r2)
            int r1 = r7.getMinimumFontSize()
            int r1 = r1 + 8
            r7.setMinimumFontSize(r1)
            r7.setAllowFileAccess(r3)
            android.webkit.WebSettings$TextSize r1 = android.webkit.WebSettings.TextSize.NORMAL
            r7.setTextSize(r1)
            r5.setVerticalScrollbarOverlay(r2)
            com.alipay.sdk.util.m r7 = new com.alipay.sdk.util.m
            r7.<init>(r0)
            r5.setDownloadListener(r7)
            int r7 = android.os.Build.VERSION.SDK_INT
            r0 = 7
            if (r7 < r0) goto L_0x00b5
            android.webkit.WebSettings r7 = r5.getSettings()     // Catch:{ Exception -> 0x00b5 }
            java.lang.Class r7 = r7.getClass()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r0 = "setDomStorageEnabled"
            java.lang.Class[] r1 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x00b5 }
            java.lang.Class r4 = java.lang.Boolean.TYPE     // Catch:{ Exception -> 0x00b5 }
            r1[r3] = r4     // Catch:{ Exception -> 0x00b5 }
            java.lang.reflect.Method r7 = r7.getMethod(r0, r1)     // Catch:{ Exception -> 0x00b5 }
            if (r7 == 0) goto L_0x00b5
            android.webkit.WebSettings r0 = r5.getSettings()     // Catch:{ Exception -> 0x00b5 }
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00b5 }
            java.lang.Boolean r4 = java.lang.Boolean.TRUE     // Catch:{ Exception -> 0x00b5 }
            r1[r3] = r4     // Catch:{ Exception -> 0x00b5 }
            r7.invoke(r0, r1)     // Catch:{ Exception -> 0x00b5 }
        L_0x00b5:
            java.lang.String r7 = "searchBoxJavaBridge_"
            r5.removeJavascriptInterface(r7)     // Catch:{ Throwable -> 0x00c6 }
            java.lang.String r7 = "accessibility"
            r5.removeJavascriptInterface(r7)     // Catch:{ Throwable -> 0x00c6 }
            java.lang.String r7 = "accessibilityTraversal"
            r5.removeJavascriptInterface(r7)     // Catch:{ Throwable -> 0x00c6 }
            goto L_0x00f1
        L_0x00c6:
            java.lang.Class r7 = r5.getClass()     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r0 = "removeJavascriptInterface"
            java.lang.Class[] r1 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x00f1 }
            java.lang.reflect.Method r7 = r7.getMethod(r0, r1)     // Catch:{ Throwable -> 0x00f1 }
            if (r7 == 0) goto L_0x00f1
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r1 = "searchBoxJavaBridge_"
            r0[r3] = r1     // Catch:{ Throwable -> 0x00f1 }
            r7.invoke(r5, r0)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r1 = "accessibility"
            r0[r3] = r1     // Catch:{ Throwable -> 0x00f1 }
            r7.invoke(r5, r0)     // Catch:{ Throwable -> 0x00f1 }
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r1 = "accessibilityTraversal"
            r0[r3] = r1     // Catch:{ Throwable -> 0x00f1 }
            r7.invoke(r5, r0)     // Catch:{ Throwable -> 0x00f1 }
        L_0x00f1:
            int r7 = android.os.Build.VERSION.SDK_INT
            r0 = 19
            if (r7 < r0) goto L_0x00ff
            android.webkit.WebSettings r7 = r5.getSettings()
            r0 = 2
            r7.setCacheMode(r0)
        L_0x00ff:
            r5.loadUrl(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.l.a(android.app.Activity, java.lang.String, java.lang.String):android.webkit.WebView");
    }

    public static String c() {
        String e2 = e();
        int indexOf = e2.indexOf("-");
        if (indexOf != -1) {
            e2 = e2.substring(0, indexOf);
        }
        int indexOf2 = e2.indexOf("\n");
        if (indexOf2 != -1) {
            e2 = e2.substring(0, indexOf2);
        }
        return "Linux ".concat(String.valueOf(e2));
    }

    private static String e() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(readLine);
            if (!matcher.matches()) {
                return "Unavailable";
            }
            if (matcher.groupCount() < 4) {
                return "Unavailable";
            }
            StringBuilder sb = new StringBuilder(matcher.group(1));
            sb.append("\n");
            sb.append(matcher.group(2));
            sb.append(Token.SEPARATOR);
            sb.append(matcher.group(3));
            sb.append("\n");
            sb.append(matcher.group(4));
            return sb.toString();
        } catch (IOException unused) {
            return "Unavailable";
        } catch (Throwable th) {
            bufferedReader.close();
            throw th;
        }
    }

    public static String f(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    private static DisplayMetrics j(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private static String k(Context context) {
        String a2 = k.a(context);
        return a2.substring(0, a2.indexOf("://"));
    }

    public static String d() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    sb.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 65.0d))));
                    break;
                case 1:
                    sb.append(String.valueOf((char) ((int) Math.round((Math.random() * 25.0d) + 97.0d))));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    public static boolean b(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    public static String h(Context context) {
        String str = "";
        try {
            for (RunningAppProcessInfo next : ((ActivityManager) context.getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (next.processName.equals(a())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("#M");
                    str = sb.toString();
                } else {
                    String str2 = next.processName;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(a());
                    sb2.append(":");
                    if (str2.startsWith(sb2.toString())) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append(MetaRecord.LOG_SEPARATOR);
                        String str3 = next.processName;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(a());
                        sb4.append(":");
                        sb3.append(str3.replace(sb4.toString(), ""));
                        str = sb3.toString();
                    }
                }
            }
        } catch (Throwable unused) {
            str = "";
        }
        if (str.length() > 0) {
            str = str.substring(1);
        }
        return str.length() == 0 ? "N" : str;
    }

    public static String i(Context context) {
        try {
            List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                int i2 = packageInfo.applicationInfo.flags;
                if ((i2 & 1) == 0 && (i2 & 128) == 0) {
                    if (packageInfo.packageName.equals(a())) {
                        sb.append(packageInfo.packageName);
                        sb.append(packageInfo.versionCode);
                        sb.append("-");
                    } else if (!packageInfo.packageName.contains("theme") && !packageInfo.packageName.startsWith("com.google.") && !packageInfo.packageName.startsWith("com.android.")) {
                        sb.append(packageInfo.packageName);
                        sb.append("-");
                    }
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a((String) c.b, (String) "GetInstalledAppEx", th);
            return "";
        }
    }

    @SuppressLint({"InlinedApi"})
    private static boolean c(PackageInfo packageInfo) {
        int i = packageInfo.applicationInfo.flags;
        return (i & 1) == 0 && (i & 128) == 0;
    }

    public static boolean a(WebView webView, String str, Activity activity) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (str.toLowerCase().startsWith(com.alipay.sdk.cons.a.i.toLowerCase()) || str.toLowerCase().startsWith(com.alipay.sdk.cons.a.j.toLowerCase())) {
            try {
                a a2 = a((Context) activity);
                if (a2 != null) {
                    if (!a2.a()) {
                        if (str.startsWith("intent://platformapi/startapp")) {
                            str = str.replaceFirst("intent://platformapi/startapp\\?", com.alipay.sdk.cons.a.i);
                        }
                        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                        return true;
                    }
                }
                return true;
            } catch (Throwable unused) {
            }
        } else if (TextUtils.equals(str, com.alipay.sdk.cons.a.l) || TextUtils.equals(str, com.alipay.sdk.cons.a.m)) {
            i.a = i.a();
            activity.finish();
            return true;
        } else if (str.startsWith(com.alipay.sdk.cons.a.k)) {
            try {
                String substring = str.substring(str.indexOf(com.alipay.sdk.cons.a.k) + 24);
                int parseInt = Integer.parseInt(substring.substring(substring.lastIndexOf(com.alipay.sdk.cons.a.n) + 10));
                if (parseInt != j.SUCCEEDED.h) {
                    if (parseInt != j.PAY_WAITTING.h) {
                        j a3 = j.a(j.FAILED.h);
                        i.a = i.a(a3.h, a3.i, "");
                        activity.runOnUiThread(new n(activity));
                        return true;
                    }
                }
                if (com.alipay.sdk.cons.a.r) {
                    StringBuilder sb = new StringBuilder();
                    String decode = URLDecoder.decode(str);
                    String decode2 = URLDecoder.decode(decode);
                    int indexOf = decode.indexOf(com.alipay.sdk.cons.a.p) + 12;
                    sb.append(decode2.substring(decode2.indexOf(com.alipay.sdk.cons.a.k) + 24, decode2.lastIndexOf(com.alipay.sdk.cons.a.n)).split(com.alipay.sdk.cons.a.p)[0]);
                    sb.append(com.alipay.sdk.cons.a.p);
                    sb.append(decode.substring(indexOf, decode.indexOf("&", indexOf)));
                    sb.append(decode.substring(decode.indexOf("&", indexOf)));
                    str2 = sb.toString();
                } else {
                    String decode3 = URLDecoder.decode(str);
                    str2 = decode3.substring(decode3.indexOf(com.alipay.sdk.cons.a.k) + 24, decode3.lastIndexOf(com.alipay.sdk.cons.a.n));
                }
                j a4 = j.a(parseInt);
                i.a = i.a(a4.h, a4.i, str2);
            } catch (Exception unused2) {
                j a5 = j.a(j.PARAMS_ERROR.h);
                i.a = i.a(a5.h, a5.i, "");
            }
            activity.runOnUiThread(new n(activity));
            return true;
        } else {
            webView.loadUrl(str);
            return true;
        }
    }

    private static a a(Context context, String str) {
        PackageInfo packageInfo;
        try {
            PackageInfo packageInfo2 = context.getPackageManager().getPackageInfo(str, 192);
            if (!a(packageInfo2)) {
                try {
                    packageInfo = c(context, str);
                } catch (Throwable th) {
                    com.alipay.sdk.app.statistic.a.a((String) "auth", (String) c.n, th);
                }
                if (a(packageInfo) || packageInfo == null) {
                    return null;
                }
                a aVar = new a();
                aVar.a = packageInfo.signatures;
                aVar.b = packageInfo.versionCode;
                return aVar;
            }
            packageInfo = packageInfo2;
        } catch (Throwable th2) {
            com.alipay.sdk.app.statistic.a.a((String) "auth", (String) c.n, th2);
            packageInfo = null;
        }
        if (a(packageInfo)) {
            return null;
        }
        a aVar2 = new a();
        aVar2.a = packageInfo.signatures;
        aVar2.b = packageInfo.versionCode;
        return aVar2;
        throw th;
    }

    public static String g(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
        StringBuilder sb = new StringBuilder();
        sb.append(displayMetrics.widthPixels);
        sb.append("*");
        sb.append(displayMetrics.heightPixels);
        return sb.toString();
    }
}
