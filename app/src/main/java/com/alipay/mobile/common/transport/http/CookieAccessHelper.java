package com.alipay.mobile.common.transport.http;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;

public class CookieAccessHelper {
    private static CookieManager a = null;
    private static AsyncFlushCookieRunnable b;

    class AsyncFlushCookieRunnable implements Runnable {
        private AsyncFlushCookieRunnable() {
        }

        public void run() {
            CookieAccessHelper.flushCookie();
        }
    }

    public static CookieManager getCookieManager() {
        if (a != null) {
            return a;
        }
        synchronized (CookieManager.class) {
            if (a != null) {
                CookieManager cookieManager = a;
                return cookieManager;
            }
            try {
                a = CookieManager.getInstance();
                return a;
            } catch (Throwable e) {
                LogCatUtil.error((String) "CookieAccessHelper", "getCookieManager fail. will use EmptyAndroidCookieManager. exception msg: " + e.toString());
                return EmptyAndroidCookieManager.getInstance();
            }
        }
    }

    @Deprecated
    public static synchronized void removeAllCookie() {
        synchronized (CookieAccessHelper.class) {
            getCookieManager().removeAllCookie();
        }
    }

    public static synchronized void removeAllCookie(Context context) {
        synchronized (CookieAccessHelper.class) {
            try {
                a(context);
                getCookieManager().removeAllCookie();
            } catch (Throwable e) {
                LogCatUtil.error("CookieAccessHelper", "removeAllCookie exception ", e);
            }
        }
        return;
    }

    @Deprecated
    public static synchronized void setCookie(String domain, String cookieString) {
        synchronized (CookieAccessHelper.class) {
            getCookieManager().setCookie(domain, cookieString);
            CookieSyncManager.getInstance().sync();
        }
    }

    public static synchronized void setCookie(String domain, String cookieString, Context context) {
        synchronized (CookieAccessHelper.class) {
            try {
                a(context);
                getCookieManager().setCookie(domain, cookieString);
                CookieSyncManager.getInstance().sync();
            } catch (Throwable e) {
                LogCatUtil.error("CookieAccessHelper", "setCookie exception:", e);
            }
        }
        return;
    }

    public static synchronized void applyCookie(String domain, String cookieString, Context context) {
        synchronized (CookieAccessHelper.class) {
            try {
                a(context);
                getCookieManager().setCookie(domain, cookieString);
            } catch (Throwable e) {
                LogCatUtil.error("CookieAccessHelper", "setCookie exception:", e);
            }
        }
        return;
    }

    public static final void asyncFlushCookie() {
        NetworkAsyncTaskExecutor.executeIO(a());
    }

    public static final void flushCookie() {
        try {
            CookieSyncManager.getInstance().sync();
        } catch (Throwable e) {
            LogCatUtil.error("CookieAccessHelper", "flushCookie exception:", e);
        }
    }

    @Deprecated
    public static synchronized String getCookie(String url) {
        String cookie;
        synchronized (CookieAccessHelper.class) {
            cookie = getCookieManager().getCookie(url);
        }
        return cookie;
    }

    public static synchronized String getCookie(String url, Context context) {
        String str;
        synchronized (CookieAccessHelper.class) {
            try {
                a(context);
                str = getCookieManager().getCookie(url);
            } catch (Throwable e) {
                LogCatUtil.error("CookieAccessHelper", "getCookie exception:", e);
                str = "";
            }
        }
        return str;
    }

    private static final void a(Context context) {
        try {
            CookieSyncManager.createInstance(context);
        } catch (Throwable e) {
            LogCatUtil.error("CookieAccessHelper", "createCookieSyncManager exception:", e);
        }
    }

    private static AsyncFlushCookieRunnable a() {
        if (b != null) {
            return b;
        }
        synchronized (CookieAccessHelper.class) {
            if (b != null) {
                AsyncFlushCookieRunnable asyncFlushCookieRunnable = b;
                return asyncFlushCookieRunnable;
            }
            b = new AsyncFlushCookieRunnable();
            return b;
        }
    }
}
