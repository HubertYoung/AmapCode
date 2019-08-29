package com.ali.auth.third.accountlink;

import com.ali.auth.third.accountlink.a.a;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.ui.context.CallbackContext;

class b implements LoginCallback {
    final /* synthetic */ LoginCallback a;
    final /* synthetic */ int b;
    final /* synthetic */ String c;
    final /* synthetic */ a d;

    b(a aVar, LoginCallback loginCallback, int i, String str) {
        this.d = aVar;
        this.a = loginCallback;
        this.b = i;
        this.c = str;
    }

    public void onFailure(int i, String str) {
        SDKLogger.d("login", "handleBindEvent auto login fail");
        this.d.a(1, this.b, this.c, this.a);
    }

    public void onSuccess(Session session) {
        SDKLogger.d("login", "handleBindEvent auto login success");
        if (this.a != null) {
            this.a.onSuccess(a.e.getSession());
        }
        if (CallbackContext.mGlobalLoginCallback != null) {
            CallbackContext.mGlobalLoginCallback.onSuccess(a.e.getSession());
        }
    }
}
