package com.ali.auth.third.accountlink;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.ui.context.CallbackContext;

class e implements Runnable {
    final /* synthetic */ Session a;
    final /* synthetic */ c b;

    e(c cVar, Session session) {
        this.b = cVar;
        this.a = session;
    }

    public void run() {
        if (CallbackContext.loginCallback != null) {
            ((LoginCallback) CallbackContext.loginCallback).onSuccess(this.a);
        }
        if (CallbackContext.mGlobalLoginCallback != null) {
            CallbackContext.mGlobalLoginCallback.onSuccess(this.a);
        }
    }
}
