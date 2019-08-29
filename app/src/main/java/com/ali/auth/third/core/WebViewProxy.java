package com.ali.auth.third.core;

public interface WebViewProxy {
    void flush();

    String getCookie(String str);

    void removeAllCookie();

    void removeExpiredCookie();

    void removeSessionCookie();

    void setAcceptCookie(boolean z);

    void setCookie(String str, String str2);
}
