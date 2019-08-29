package com.alipay.android.phone.inside.security.net;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.security.util.Rsa;
import com.alipay.android.phone.inside.storage.pref.PrefUtils;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;

public class PublicKeyManager {
    private static String a;
    private static String b;

    public static String a() {
        if (TextUtils.isEmpty(a)) {
            a = PrefUtils.b("sp_inside_public_key", "rpc", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAizFn/4NljF0reFYZtMFP+M9Hv5dnOJYtxAu1BtBFah4OE08+ZxLMnqUTPX7aZ4HWHHG9IuYn561WoaY2kHhR8kEm7MxrRLGOCGIV7hYAB6eRK4FhI8utKt1ntw5Y4QTuQxAwV59bcA3jmHa5T50kLZxSPfNQUAcLzSNp1XOLYT23WCHXRWHv+WJHU0e03kefIULR9hTrHZqLHfQLBVCrvavrVCXFrJrSsnb9CILb9kVEPqoVo2ipGSrXX9roqzi/TJtjYhomulnhfL4q8Nfjsk7kq9alq4pc9bZ4muVn3wp9MPVZWhWDMEXU7GXGVM3kwzOPQwSv7VOdCr+GUqkMaQIDAQAB");
        }
        a("get rpc pk", a);
        return a;
    }

    public static String b() {
        if (TextUtils.isEmpty(b)) {
            b = PrefUtils.b("sp_inside_public_key", ReportManager.LOG_PATH, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVimG0UFl3/sujKEARpmOxoweaqHtRK1EY03hd+bYFTe6Bnm/t4nMNEBHr2yF0GC2PmdJ5a5h2/ppKruYYXrTsH4ierw7kS62I/9mGU6k2sqYMolO2tA6LM/0OnRo0QXQA07tmzxcirY8aW/rpUQnzDZJJv7zgDnrJkoXndd4M9wIDAQAB");
        }
        a("get log pk", b);
        return b;
    }

    public static boolean a(String str) {
        a("set rpc pk", str);
        if (!c(str)) {
            return false;
        }
        a = str;
        return PrefUtils.a("sp_inside_public_key", "rpc", a);
    }

    public static boolean b(String str) {
        a("set log pk", str);
        if (!c(str)) {
            return false;
        }
        b = str;
        return PrefUtils.a("sp_inside_public_key", ReportManager.LOG_PATH, b);
    }

    private static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            LoggerFactory.e().a((String) "security", (String) "CheckPublicKeyNull", "");
            return false;
        }
        boolean z = true;
        try {
            Rsa.a((String) "MAGIC", str);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "security", (String) "CheckPublicKeyEx", th);
            z = false;
        }
        return z;
    }

    public static void c() {
        b = null;
        PrefUtils.b("sp_inside_public_key", ReportManager.LOG_PATH);
    }

    public static void d() {
        a = null;
        PrefUtils.b("sp_inside_public_key", "rpc");
    }

    private static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && str2.length() > 30) {
            str2 = str2.substring(0, 30);
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        f.b((String) "inside", sb.toString());
    }
}
