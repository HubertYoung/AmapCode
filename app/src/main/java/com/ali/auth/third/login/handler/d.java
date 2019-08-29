package com.ali.auth.third.login.handler;

import android.app.Activity;
import com.ali.auth.third.login.UTConstants;
import com.ali.auth.third.ui.context.CallbackContext;

class d implements Runnable {
    final /* synthetic */ b a;

    d(b bVar) {
        this.a = bVar;
    }

    public void run() {
        CallbackContext.activity = this.a.a;
        this.a.c.a((Activity) this.a.a.get(), UTConstants.E_H5_LOGIN_SUCCESS, this.a.b);
        CallbackContext.activity = null;
    }
}
