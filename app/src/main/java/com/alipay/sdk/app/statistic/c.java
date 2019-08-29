package com.alipay.sdk.app.statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.sdk.cons.a;
import com.alipay.sdk.sys.b;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class c {
    public static final String A = "ClientBindServiceFailed";
    public static final String B = "BindWaitTimeoutEx";
    public static final String C = "CheckClientExistEx";
    public static final String D = "CheckClientSignEx";
    public static final String E = "GetInstalledAppEx";
    public static final String F = "GetInstalledAppEx";
    public static final String G = "tid_context_null";
    public static final String H = "partner";
    public static final String I = "out_trade_no";
    public static final String J = "trade_no";
    public static final String a = "net";
    public static final String b = "biz";
    public static final String c = "cp";
    public static final String d = "auth";
    public static final String e = "third";
    public static final String f = "tid";
    public static final String g = "FormatResultEx";
    public static final String h = "GetApdidEx";
    public static final String i = "GetApdidNull";
    public static final String j = "GetApdidTimeout";
    public static final String k = "GetUtdidEx";
    public static final String l = "GetPackageInfoEx";
    public static final String m = "NotIncludeSignatures";
    public static final String n = "GetInstalledPackagesEx";
    public static final String o = "GetPublicKeyFromSignEx";
    public static final String p = "H5PayNetworkError";
    public static final String q = "H5AuthNetworkError";
    public static final String r = "SSLError";
    public static final String s = "H5PayDataAnalysisError";
    public static final String t = "H5AuthDataAnalysisError";
    public static final String u = "PublicKeyUnmatch";
    public static final String v = "ClientBindFailed";
    public static final String w = "TriDesEncryptError";
    public static final String x = "TriDesDecryptError";
    public static final String y = "ClientBindException";
    public static final String z = "SaveTradeTokenError";
    private String K;
    private String L;
    private String M;
    private String N;
    private String O;
    private String P;
    private String Q;
    private String R;
    private String S = "";
    private String T;

    public c(Context context) {
        context = context != null ? context.getApplicationContext() : context;
        this.K = String.format("123456789,%s", new Object[]{new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date())});
        this.M = a(context);
        this.N = String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,-,-", new Object[]{b((String) a.f), b((String) a.g)});
        this.O = String.format("%s,%s,-,-,-", new Object[]{b(com.alipay.sdk.tid.c.a(b.a().a).a()), b(b.a().c())});
        this.P = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", new Object[]{b(com.alipay.sdk.util.a.d(context)), "android", b(VERSION.RELEASE), b(Build.MODEL), "-", b(com.alipay.sdk.util.a.a(context).a()), b(com.alipay.sdk.util.a.b(context).p), "gw", b(com.alipay.sdk.util.a.a(context).b())});
        this.Q = "-";
        this.R = "-";
        this.T = "-";
    }

    private boolean a() {
        return TextUtils.isEmpty(this.S);
    }

    public final void a(String str, String str2, Throwable th) {
        a(str, str2, a(th));
    }

    private void a(String str, String str2, Throwable th, String str3) {
        a(str, str2, a(th), str3);
    }

    public final void a(String str, String str2, String str3, String str4) {
        String str5 = "";
        if (!TextUtils.isEmpty(this.S)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str5);
            sb.append("^");
            str5 = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str5);
        sb2.append(String.format("%s,%s,%s,%s", new Object[]{str, str2, b(str3), str4}));
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.S);
        sb4.append(sb3);
        this.S = sb4.toString();
    }

    public final void a(String str, String str2, String str3) {
        a(str, str2, str3, (String) "-");
    }

    private static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str.replace("[", "【").replace("]", "】").replace("(", "（").replace(")", "）").replace(",", "，").replace("-", "=").replace("^", Constants.WAVE_SEPARATOR);
    }

    static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(":");
            stringBuffer.append(th.getMessage());
            stringBuffer.append(" 》 ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(stackTraceElement.toString());
                    sb.append(" 》 ");
                    stringBuffer.append(sb.toString());
                }
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }

    @SuppressLint({"SimpleDateFormat"})
    private static String b() {
        return String.format("123456789,%s", new Object[]{new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date())});
    }

    private static String c(String str) {
        String str2;
        String[] split = str.split("&");
        String str3 = null;
        if (split != null) {
            str2 = null;
            String str4 = null;
            for (String split2 : split) {
                String[] split3 = split2.split("=");
                if (split3 != null && split3.length == 2) {
                    if (split3[0].equalsIgnoreCase(H)) {
                        split3[1].replace("\"", "");
                    } else if (split3[0].equalsIgnoreCase(I)) {
                        str2 = split3[1].replace("\"", "");
                    } else if (split3[0].equalsIgnoreCase(J)) {
                        str4 = split3[1].replace("\"", "");
                    }
                }
            }
            str3 = str4;
        } else {
            str2 = null;
        }
        String b2 = b(str3);
        String b3 = b(str2);
        return String.format("%s,%s,-,%s,-,-,-", new Object[]{b2, b3, b(b3)});
    }

    private static String a(Context context) {
        String str = "-";
        String str2 = "-";
        if (context != null) {
            try {
                Context applicationContext = context.getApplicationContext();
                String packageName = applicationContext.getPackageName();
                try {
                    str2 = applicationContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
                } catch (Throwable unused) {
                }
                str = packageName;
            } catch (Throwable unused2) {
            }
        }
        return String.format("%s,%s,-,-,-", new Object[]{str, str2});
    }

    private static String c() {
        return String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,-,-", new Object[]{b((String) a.f), b((String) a.g)});
    }

    private static String d() {
        return String.format("%s,%s,-,-,-", new Object[]{b(com.alipay.sdk.tid.c.a(b.a().a).a()), b(b.a().c())});
    }

    private static String b(Context context) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", new Object[]{b(com.alipay.sdk.util.a.d(context)), "android", b(VERSION.RELEASE), b(Build.MODEL), "-", b(com.alipay.sdk.util.a.a(context).a()), b(com.alipay.sdk.util.a.b(context).p), "gw", b(com.alipay.sdk.util.a.a(context).b())});
    }

    public final String a(String str) {
        String str2;
        if (TextUtils.isEmpty(this.S)) {
            return "";
        }
        String[] split = str.split("&");
        String str3 = null;
        if (split != null) {
            str2 = null;
            String str4 = null;
            for (String split2 : split) {
                String[] split3 = split2.split("=");
                if (split3 != null && split3.length == 2) {
                    if (split3[0].equalsIgnoreCase(H)) {
                        split3[1].replace("\"", "");
                    } else if (split3[0].equalsIgnoreCase(I)) {
                        str2 = split3[1].replace("\"", "");
                    } else if (split3[0].equalsIgnoreCase(J)) {
                        str4 = split3[1].replace("\"", "");
                    }
                }
            }
            str3 = str4;
        } else {
            str2 = null;
        }
        String b2 = b(str3);
        String b3 = b(str2);
        this.L = String.format("%s,%s,-,%s,-,-,-", new Object[]{b2, b3, b(b3)});
        return String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", new Object[]{this.K, this.L, this.M, this.N, this.O, this.P, this.Q, this.R, this.S, this.T});
    }
}
