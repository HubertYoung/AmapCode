package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent;
import com.amap.bundle.logs.AMapLog;
import java.util.HashMap;

/* renamed from: aay reason: default package */
/* compiled from: SecurityGuardSign */
public class aay {
    private static Context a = null;
    private static String b = null;
    private static int c = -1;
    private static j d;

    public static String a(String... strArr) {
        if (strArr == null || strArr.length != 2) {
            return "";
        }
        String str = strArr[0];
        String str2 = strArr[1];
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        return a(String.format("&&&%s&%s&%s&&&&&&", new Object[]{c(), agy.a(str), str2}));
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        HashMap hashMap = new HashMap();
        hashMap.put("INPUT", str);
        if (d() == 2) {
            hashMap.put("ATLAS", "daily");
        }
        SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
        securityGuardParamContext.appKey = c();
        securityGuardParamContext.paramMap = hashMap;
        securityGuardParamContext.requestType = 7;
        try {
            return SecurityGuardManager.getInstance(e()).getSecureSignatureComp().signRequest(securityGuardParamContext, "");
        } catch (SecException e) {
            StringBuilder sb = new StringBuilder("white box sign error, errorCode = ");
            sb.append(e.getErrorCode());
            sb.append(", errorMsg = ");
            sb.append(e.getLocalizedMessage());
            AMapLog.e("SecurityGuardSign", sb.toString());
            return "";
        }
    }

    public static String a() {
        try {
            return ((ISecurityBodyComponent) SecurityGuardManager.getInstance(e()).getInterface(ISecurityBodyComponent.class)).getSecurityBodyDataEx(null, null, null, null, 8, d());
        } catch (SecException e) {
            StringBuilder sb = new StringBuilder("mini wua error, errorCode = ");
            sb.append(e.getErrorCode());
            sb.append(", errorMsg = ");
            sb.append(e.getLocalizedMessage());
            AMapLog.e("SecurityGuardSign", sb.toString());
            return "";
        }
    }

    public static boolean b() {
        if (d == null) {
            d = aaf.a == null ? null : aaf.a.o();
        }
        if (d != null) {
            return d.a();
        }
        return true;
    }

    public static String c() {
        String str;
        if (b != null) {
            return b;
        }
        synchronized (aay.class) {
            try {
                b c2 = aaf.c();
                if (c2 == null) {
                    throw new IllegalStateException("you must init network context first!");
                }
                if (b == null) {
                    b = c2.a();
                }
                str = b;
            }
        }
        return str;
    }

    private static int d() {
        int i;
        if (c >= 0) {
            return c;
        }
        synchronized (aay.class) {
            if (c < 0) {
                b c2 = aaf.c();
                if (c2 == null) {
                    throw new IllegalStateException("you must init network context first!");
                }
                String b2 = c2.b();
                if (b2.equalsIgnoreCase("test")) {
                    c = 2;
                } else if (b2.equalsIgnoreCase("preview")) {
                    c = 1;
                } else if (b2.equalsIgnoreCase("release")) {
                    c = 0;
                }
            }
            i = c;
        }
        return i;
    }

    private static Context e() {
        Context context;
        if (a != null) {
            return a;
        }
        synchronized (aay.class) {
            if (a == null) {
                a = aaf.a();
            }
            context = a;
        }
        return context;
    }
}
