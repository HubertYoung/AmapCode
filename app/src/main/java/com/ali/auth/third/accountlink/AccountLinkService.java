package com.ali.auth.third.accountlink;

import com.ali.auth.third.core.callback.LoginCallback;

public interface AccountLinkService {
    public static final String TAG = "login";

    void bind(LoginCallback loginCallback);

    BindCallback getBindCallback();

    boolean isBind();

    boolean isLoginUrl(String str);

    boolean isLogoutUrl(String str);

    void setBindCallback(BindCallback bindCallback);

    void unBind(LoginCallback loginCallback);
}
