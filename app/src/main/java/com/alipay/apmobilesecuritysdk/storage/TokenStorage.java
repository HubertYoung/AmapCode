package com.alipay.apmobilesecuritysdk.storage;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.loggers.LoggerUtil;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.util.HashMap;
import java.util.Map;

public class TokenStorage {
    private static String a = "";
    private static String b = "";
    private static String c = "";
    private static String d = "";
    private static String e = "";
    private static Map<String, String> f = new HashMap();

    public static void h() {
    }

    public static synchronized String a(String str) {
        synchronized (TokenStorage.class) {
            String concat = "apdidTokenCache".concat(String.valueOf(str));
            if (f.containsKey(concat)) {
                String str2 = f.get(concat);
                if (CommonUtils.isNotBlank(str2)) {
                    return str2;
                }
            }
            return "";
        }
    }

    public static synchronized String a() {
        String str;
        synchronized (TokenStorage.class) {
            str = a;
        }
        return str;
    }

    public static synchronized void b(String str) {
        synchronized (TokenStorage.class) {
            a = str;
        }
    }

    public static synchronized String b() {
        String str;
        synchronized (TokenStorage.class) {
            str = b;
        }
        return str;
    }

    public static synchronized void c(String str) {
        synchronized (TokenStorage.class) {
            b = str;
        }
    }

    public static synchronized String c() {
        String str;
        synchronized (TokenStorage.class) {
            str = d;
        }
        return str;
    }

    public static synchronized void d(String str) {
        synchronized (TokenStorage.class) {
            d = str;
        }
    }

    public static synchronized String d() {
        String str;
        synchronized (TokenStorage.class) {
            str = e;
        }
        return str;
    }

    public static synchronized void e(String str) {
        synchronized (TokenStorage.class) {
            e = str;
        }
    }

    public static synchronized String e() {
        String str;
        synchronized (TokenStorage.class) {
            str = c;
        }
        return str;
    }

    public static synchronized ApdidStorageModelV4 f() {
        ApdidStorageModelV4 apdidStorageModelV4;
        synchronized (TokenStorage.class) {
            apdidStorageModelV4 = new ApdidStorageModelV4(a, b, c, d, e);
        }
        return apdidStorageModelV4;
    }

    public static synchronized void a(String str, String str2) {
        synchronized (TokenStorage.class) {
            String concat = "apdidTokenCache".concat(String.valueOf(str));
            if (f.containsKey(concat)) {
                f.remove(concat);
            }
            f.put(concat, str2);
        }
    }

    public static synchronized void f(String str) {
        synchronized (TokenStorage.class) {
            c = str;
        }
    }

    private static synchronized void a(ApdidStorageModelV4 apdidStorageModelV4) {
        synchronized (TokenStorage.class) {
            if (apdidStorageModelV4 != null) {
                b(apdidStorageModelV4.a());
                c(apdidStorageModelV4.b());
                d(apdidStorageModelV4.d());
                e(apdidStorageModelV4.e());
                f(apdidStorageModelV4.c());
            }
        }
    }

    private static synchronized void a(ApdidStorageModel apdidStorageModel) {
        synchronized (TokenStorage.class) {
            if (apdidStorageModel != null) {
                b(apdidStorageModel.a());
                c(apdidStorageModel.b());
                f(apdidStorageModel.c());
            }
        }
    }

    public static synchronized boolean a(Context context, String str) {
        synchronized (TokenStorage.class) {
            try {
                long a2 = SettingsStorage.a(context);
                LoggerFactory.f().b((String) CONST.LOG_TAG, "[*] isTokenValid, timeinterval = ".concat(String.valueOf(a2)));
                if (Math.abs(System.currentTimeMillis() - SettingsStorage.g(context, str)) < a2) {
                    return true;
                }
            } catch (Throwable th) {
                LoggerUtil.a(th);
            }
            return false;
        }
    }

    public static synchronized void g() {
        synchronized (TokenStorage.class) {
            f.clear();
            a = "";
            b = "";
            d = "";
            e = "";
            c = "";
        }
    }

    public static synchronized String b(Context context, String str) {
        synchronized (TokenStorage.class) {
            try {
                String a2 = a(str);
                if (!CommonUtils.isBlank(a2)) {
                    return a2;
                }
                String a3 = OpenApdidTokenStorage.a(context, str);
                a(str, a3);
                if (!CommonUtils.isBlank(a3)) {
                    return a3;
                }
                return "";
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized String a(Context context) {
        synchronized (TokenStorage.class) {
            TraceLogger f2 = LoggerFactory.f();
            try {
                String a2 = a();
                if (!CommonUtils.isBlank(a2)) {
                    return a2;
                }
                ApdidStorageModelV4 b2 = ApdidStorageV4.b(context);
                if (b2 != null) {
                    f2.b((String) CONST.LOG_TAG, (String) "[*] read v4 storage model, update local memory cache successfully.");
                    a(b2);
                    String a3 = b2.a();
                    if (CommonUtils.isNotBlank(a3)) {
                        return a3;
                    }
                }
                ApdidStorageModel b3 = ApdidStorage.b(context);
                if (b3 != null) {
                    f2.b((String) CONST.LOG_TAG, (String) "read v3 storage model, update local memory cache successfully.");
                    a(b3);
                    String a4 = b3.a();
                    if (CommonUtils.isNotBlank(a4)) {
                        return a4;
                    }
                }
                return "";
            } catch (Throwable unused) {
            }
        }
    }

    public static synchronized String b(Context context) {
        String a2;
        synchronized (TokenStorage.class) {
            try {
                a2 = a(context);
                if (CommonUtils.isBlank(a2)) {
                    a2 = SettingsStorage.h(context);
                }
            }
        }
        return a2;
    }
}
