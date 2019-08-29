package com.ali.auth.third.core.a;

import android.os.Build.VERSION;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.ali.auth.third.core.WebViewProxy;
import com.ali.auth.third.core.context.KernelContext;

public class a implements WebViewProxy {
    private static volatile a a;

    private a() {
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a();
                }
            }
        }
        return a;
    }

    public void flush() {
        if (VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.createInstance(KernelContext.getApplicationContext()).sync();
        }
    }

    public String getCookie(String str) {
        CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        return instance.getCookie(".taobao.com");
    }

    public void removeAllCookie() {
        CookieManager.getInstance().removeAllCookie();
    }

    public void removeExpiredCookie() {
        CookieManager.getInstance().removeExpiredCookie();
    }

    public void removeSessionCookie() {
        CookieManager.getInstance().removeSessionCookie();
    }

    public void setAcceptCookie(boolean z) {
        CookieManager.getInstance().setAcceptCookie(z);
    }

    public void setCookie(String str, String str2) {
        CookieManager.getInstance().setCookie(str, str2);
    }
}
