package com.alipay.mobile.common.transport.http;

import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class EmptyAndroidCookieManager extends CookieManager {
    private static EmptyAndroidCookieManager a;

    public static CookieManager getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (EmptyAndroidCookieManager.class) {
            try {
                if (a != null) {
                    EmptyAndroidCookieManager emptyAndroidCookieManager = a;
                    return emptyAndroidCookieManager;
                }
                a = new EmptyAndroidCookieManager();
                return a;
            }
        }
    }

    private EmptyAndroidCookieManager() {
    }

    public void setAcceptCookie(boolean b) {
    }

    public boolean acceptCookie() {
        return false;
    }

    public void setAcceptThirdPartyCookies(WebView webView, boolean b) {
    }

    public boolean acceptThirdPartyCookies(WebView webView) {
        return false;
    }

    public void setCookie(String s, String s1) {
    }

    public void setCookie(String s, String s1, ValueCallback<Boolean> valueCallback) {
    }

    public String getCookie(String s) {
        return null;
    }

    public void removeSessionCookie() {
    }

    public void removeSessionCookies(ValueCallback<Boolean> valueCallback) {
    }

    public void removeAllCookie() {
    }

    public void removeAllCookies(ValueCallback<Boolean> valueCallback) {
    }

    public boolean hasCookies() {
        return false;
    }

    public void removeExpiredCookie() {
    }

    public void flush() {
    }
}
