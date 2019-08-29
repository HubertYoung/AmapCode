package com.uc.webview.export.internal.android;

import android.os.Build.VERSION;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import com.uc.webview.export.WebView;
import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.internal.interfaces.ICookieManager;

@Interface
/* compiled from: ProGuard */
public class CookieManagerAndroid implements ICookieManager {
    private CookieManager a = getSystemCookieManager();

    public CookieManager getSystemCookieManager() {
        try {
            return CookieManager.getInstance();
        } catch (Exception unused) {
            return null;
        }
    }

    public synchronized void setAcceptCookie(boolean z) {
        if (this.a != null) {
            this.a.setAcceptCookie(z);
        }
    }

    public synchronized boolean acceptCookie() {
        if (this.a == null) {
            return false;
        }
        return this.a.acceptCookie();
    }

    public void setCookie(String str, String str2) {
        if (this.a != null) {
            this.a.setCookie(str, str2);
        }
    }

    public synchronized String getCookie(String str) {
        if (this.a == null) {
            return "";
        }
        return this.a.getCookie(str);
    }

    public void removeSessionCookie() {
        if (this.a != null) {
            this.a.removeSessionCookie();
        }
    }

    public void removeAllCookie() {
        if (this.a != null) {
            this.a.removeAllCookie();
        }
    }

    public synchronized boolean hasCookies() {
        if (this.a == null) {
            return false;
        }
        return this.a.hasCookies();
    }

    public void setCookie(String str, String str2, ValueCallback<Boolean> valueCallback) {
        if (this.a != null && VERSION.SDK_INT >= 21) {
            this.a.setCookie(str, str2, valueCallback);
        }
    }

    public void setAcceptThirdPartyCookies(WebView webView, boolean z) {
        if (this.a != null && VERSION.SDK_INT >= 21) {
            this.a.setAcceptThirdPartyCookies((android.webkit.WebView) webView.getCoreView(), z);
        }
    }

    public boolean acceptThirdPartyCookies(WebView webView) {
        if (this.a == null || VERSION.SDK_INT < 21) {
            return false;
        }
        return this.a.acceptThirdPartyCookies((android.webkit.WebView) webView.getCoreView());
    }

    public boolean allowFileSchemeCookiesImpl() {
        if (this.a != null) {
            return CookieManager.allowFileSchemeCookies();
        }
        return false;
    }

    public void setAcceptFileSchemeCookiesImpl(boolean z) {
        if (this.a != null) {
            CookieManager.setAcceptFileSchemeCookies(z);
        }
    }

    public void flush() {
        if (this.a != null && VERSION.SDK_INT >= 21) {
            this.a.flush();
        }
    }

    public void removeAllCookies(ValueCallback<Boolean> valueCallback) {
        if (this.a != null && VERSION.SDK_INT >= 21) {
            this.a.removeAllCookies(valueCallback);
        }
    }

    public void removeSessionCookies(ValueCallback<Boolean> valueCallback) {
        if (this.a != null && VERSION.SDK_INT >= 21) {
            this.a.removeSessionCookies(valueCallback);
        }
    }
}
