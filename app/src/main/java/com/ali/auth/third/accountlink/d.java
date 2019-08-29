package com.ali.auth.third.accountlink;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.ui.context.CallbackContext;

class d implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ String b;
    final /* synthetic */ c c;

    d(c cVar, int i, String str) {
        this.c = cVar;
        this.a = i;
        this.b = str;
    }

    public void run() {
        if (this.c.a != null && !this.c.a.isFinishing()) {
            this.c.a.finish();
        }
        if (CallbackContext.loginCallback != null) {
            if (CallbackContext.loginCallback != null) {
                ((LoginCallback) CallbackContext.loginCallback).onFailure(this.a, this.b);
            }
            if (CallbackContext.mGlobalLoginCallback != null) {
                CallbackContext.mGlobalLoginCallback.onFailure(this.a, this.b);
            }
        }
    }
}
