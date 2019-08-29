package com.ali.auth.third.login;

import android.app.Activity;
import com.ali.auth.third.core.WebViewProxy;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.callback.RefreshCookieCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.callback.LogoutCallback;

public interface LoginService {
    public static final String TAG = "login";

    void auth(Activity activity, LoginCallback loginCallback);

    void auth(LoginCallback loginCallback);

    boolean checkSessionValid();

    Session getSession();

    boolean isLoginUrl(String str);

    boolean isLogoutUrl(String str);

    void logout(Activity activity, LogoutCallback logoutCallback);

    void logout(LogoutCallback logoutCallback);

    void refreshCookie(RefreshCookieCallback refreshCookieCallback);

    void setLoginCallback(LoginCallback loginCallback);

    void setWebViewProxy(WebViewProxy webViewProxy);
}
