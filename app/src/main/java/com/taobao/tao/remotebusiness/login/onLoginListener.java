package com.taobao.tao.remotebusiness.login;

public interface onLoginListener {
    void onLoginCancel();

    void onLoginFail();

    void onLoginSuccess();
}
