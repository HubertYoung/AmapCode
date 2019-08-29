package com.ali.user.mobile.login;

public interface AccountResponsable {
    void clearAccount();

    void clearPassword();

    String getLoginAccount();

    String getLoginPassword();

    String getShownAccount();

    void onNewAccount(String str, int i);

    void requestAccountFocus();

    void requestPasswordFocus();

    void showInputPassword();
}
