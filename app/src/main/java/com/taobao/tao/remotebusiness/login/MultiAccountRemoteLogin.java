package com.taobao.tao.remotebusiness.login;

import android.support.annotation.Nullable;

public abstract class MultiAccountRemoteLogin implements IRemoteLogin {
    public abstract LoginContext getLoginContext(@Nullable String str);

    public abstract boolean isLogining(@Nullable String str);

    public abstract boolean isSessionValid(@Nullable String str);

    public abstract void login(@Nullable String str, onLoginListener onloginlistener, boolean z);

    @Deprecated
    public void login(onLoginListener onloginlistener, boolean z) {
        login(null, onloginlistener, z);
    }

    @Deprecated
    public boolean isSessionValid() {
        return isSessionValid(null);
    }

    @Deprecated
    public boolean isLogining() {
        return isLogining(null);
    }

    @Deprecated
    public LoginContext getLoginContext() {
        return getLoginContext(null);
    }
}
