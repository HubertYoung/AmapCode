package com.ali.user.mobile.accountbiz;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.common.util.StringUtils;
import com.alipay.mobile.nebula.util.H5SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SecurityUtil {
    private static final List<String> a;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add("alipays://platformapi/startapp?appid=20000009");
        a.add("alipays://platformapi/startapp?appid=20000060");
        a.add("alipays://platformapi/startapp?appid=20000015");
        a.add("alipays://platformapi/startapp?appid=20000013");
    }

    public static boolean a(String str) {
        try {
            if (a != null && !a.isEmpty() && !StringUtils.a(str)) {
                String lowerCase = str.toLowerCase();
                for (String lowerCase2 : a) {
                    if (lowerCase.startsWith(lowerCase2.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            AliUserLog.a((String) H5SecurityUtil.TAG, th);
        }
        return false;
    }

    public static void a(Runnable runnable) {
        Executors.newCachedThreadPool().execute(runnable);
    }

    public static void a(Runnable runnable, int i, TimeUnit timeUnit) {
        Executors.newScheduledThreadPool(5).schedule(runnable, (long) i, timeUnit);
    }

    public static synchronized void a() {
        CookieManager instance;
        synchronized (SecurityUtil.class) {
            try {
                instance = CookieManager.getInstance();
                instance.setAcceptCookie(true);
                String[] split = instance.getCookie(".alipay.com").split(";");
                for (String split2 : split) {
                    String[] split3 = split2.split("=");
                    StringBuilder sb = new StringBuilder();
                    sb.append(split3[0]);
                    sb.append("=; Expires=Wed, 31 Dec 2015 23:59:59 GMT");
                    instance.setCookie(".alipay.com", sb.toString());
                }
            } catch (Throwable th) {
                AliUserLog.a(H5SecurityUtil.TAG, "resetCookie error", th);
                return;
            }
            a(instance);
            CookieSyncManager.createInstance(AliUserInit.b());
            CookieSyncManager.getInstance().sync();
        }
    }

    private static void a(CookieManager cookieManager) {
        try {
            String[] split = cookieManager.getCookie(".alipay.net").split(";");
            for (String split2 : split) {
                String[] split3 = split2.split("=");
                StringBuilder sb = new StringBuilder();
                sb.append(split3[0]);
                sb.append("=; Expires=Wed, 31 Dec 2015 23:59:59 GMT");
                cookieManager.setCookie(".alipay.net", sb.toString());
            }
        } catch (Throwable th) {
            AliUserLog.a(H5SecurityUtil.TAG, "resetCookie offline error", th);
        }
    }
}
