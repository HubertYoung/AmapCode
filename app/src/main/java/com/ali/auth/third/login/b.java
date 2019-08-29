package com.ali.auth.third.login;

import com.ali.auth.third.core.broadcast.LoginAction;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.ui.context.CallbackContext;

class b implements LoginCallback {
    final /* synthetic */ LoginCallback a;
    final /* synthetic */ LoginServiceImpl b;

    b(LoginServiceImpl loginServiceImpl, LoginCallback loginCallback) {
        this.b = loginServiceImpl;
        this.a = loginCallback;
    }

    public void onFailure(int i, String str) {
        SDKLogger.d("login", "auth auto login success");
        this.b.goLogin(this.a);
    }

    public void onSuccess(Session session) {
        SDKLogger.d("login", "auth auto login success");
        if (this.a != null) {
            this.a.onSuccess(this.b.getSession());
        }
        if (CallbackContext.mGlobalLoginCallback != null) {
            CallbackContext.mGlobalLoginCallback.onSuccess(this.b.getSession());
        }
        CommonUtils.sendBroadcast(LoginAction.NOTIFY_LOGIN_SUCCESS);
    }
}
