package com.ali.user.mobile.authlogin.auth;

public interface IAlipaySSOEventHandler {
    void onAuthFailed(int i);

    void startLogin(String str);
}
