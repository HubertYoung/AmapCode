package com.alipay.android.phone.inside.log.net;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.log.util.sec.EncryptUtil.Rsa;
import com.alipay.android.phone.inside.log.util.storage.PrefUtils;

public class PublicKeyManager {
    private static String a;

    public static String a() {
        if (TextUtils.isEmpty(a)) {
            a = PrefUtils.b((String) "sp_inside_log", (String) "log_key", (String) "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVimG0UFl3/sujKEARpmOxoweaqHtRK1EY03hd+bYFTe6Bnm/t4nMNEBHr2yF0GC2PmdJ5a5h2/ppKruYYXrTsH4ierw7kS62I/9mGU6k2sqYMolO2tA6LM/0OnRo0QXQA07tmzxcirY8aW/rpUQnzDZJJv7zgDnrJkoXndd4M9wIDAQAB");
        }
        a("get log pk", a);
        return a;
    }

    public static boolean a(String str) {
        a("set log pk", str);
        if (!b(str)) {
            return false;
        }
        a = str;
        return PrefUtils.a((String) "sp_inside_log", (String) "log_key", a);
    }

    private static boolean b(String str) {
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

    public static void b() {
        a = null;
        PrefUtils.a("sp_inside_log", "log_key");
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
