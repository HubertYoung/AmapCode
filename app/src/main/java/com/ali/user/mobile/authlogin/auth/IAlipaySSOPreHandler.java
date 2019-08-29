package com.ali.user.mobile.authlogin.auth;

public interface IAlipaySSOPreHandler {
    void dismissPreProgress();

    void openAlipaySuccess();

    void preAuthFailed(int i);

    void showPreProgress();
}
