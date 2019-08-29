package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.utils.MiscUtils;

public class CookieAccessHelper {
    private static final String TAG = "CookieAccessHelper";
    private static CookieManager cookieManager;

    public static CookieManager getCookieManager() {
        if (cookieManager != null) {
            return cookieManager;
        }
        synchronized (CookieManager.class) {
            if (cookieManager != null) {
                CookieManager cookieManager2 = cookieManager;
                return cookieManager2;
            }
            cookieManager = CookieManager.getInstance();
            return cookieManager;
        }
    }

    public static synchronized void removeAllCookie(Context context) {
        synchronized (CookieAccessHelper.class) {
            try {
                createCookieSyncManager(context);
                getCookieManager().removeAllCookie();
            } catch (Throwable th) {
                LoggerFactory.f().b(TAG, "removeAllCookie exception ", th);
            }
        }
    }

    public static synchronized void setCookie(String str, String str2, Context context) {
        synchronized (CookieAccessHelper.class) {
            try {
                createCookieSyncManager(context);
                getCookieManager().setCookie(str, str2);
                CookieSyncManager.getInstance().sync();
            } catch (Throwable th) {
                LoggerFactory.f().b(TAG, "setCookie exception:", th);
            }
        }
    }

    public static synchronized String getCookie(String str, Context context) {
        String cookie;
        synchronized (CookieAccessHelper.class) {
            if (MiscUtils.isDebugger(context)) {
                try {
                    if (str.contains(".dl.alipaydev.com")) {
                        str = str.replace(".dl.alipaydev.com", ".alipay.net");
                    }
                } catch (Throwable th) {
                    LoggerFactory.f().b(TAG, "getCookie exception:", th);
                }
            }
            try {
                createCookieSyncManager(context);
                cookie = getCookieManager().getCookie(str);
            } catch (Throwable th2) {
                LoggerFactory.f().b(TAG, "getCookie exception:", th2);
                return "";
            }
        }
        return cookie;
    }

    private static final void createCookieSyncManager(Context context) {
        try {
            CookieSyncManager.createInstance(context);
        } catch (Throwable th) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("createCookieSyncManager ex:");
            sb.append(th.toString());
            f.d(TAG, sb.toString());
        }
    }
}
