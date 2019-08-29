package com.taobao.tao.remotebusiness.login;

public interface IRemoteLogin {
    LoginContext getLoginContext();

    boolean isLogining();

    boolean isSessionValid();

    void login(onLoginListener onloginlistener, boolean z);
}
