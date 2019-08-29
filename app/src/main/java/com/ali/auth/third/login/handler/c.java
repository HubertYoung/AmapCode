package com.ali.auth.third.login.handler;

import android.app.Activity;
import com.ali.auth.third.core.model.SystemMessageConstants;
import com.ali.auth.third.login.UTConstants;
import com.ali.auth.third.ui.context.CallbackContext;

class c implements Runnable {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public void run() {
        CallbackContext.activity = this.a.a;
        this.a.c.a((Activity) this.a.a.get(), UTConstants.E_IV_LOGIN_FAILURE, this.a.b, (int) SystemMessageConstants.H5_LOGIN_FAILURE);
        CallbackContext.activity = null;
    }
}
