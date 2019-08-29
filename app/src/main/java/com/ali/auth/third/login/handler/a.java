package com.ali.auth.third.login.handler;

import android.app.Activity;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.SystemMessageConstants;
import com.ali.auth.third.login.UTConstants;
import com.ali.auth.third.ui.context.CallbackContext;
import java.lang.ref.WeakReference;

class a implements Runnable {
    final /* synthetic */ WeakReference a;
    final /* synthetic */ LoginCallback b;
    final /* synthetic */ LoginActivityResultHandler c;

    a(LoginActivityResultHandler loginActivityResultHandler, WeakReference weakReference, LoginCallback loginCallback) {
        this.c = loginActivityResultHandler;
        this.a = weakReference;
        this.b = loginCallback;
    }

    public void run() {
        CallbackContext.activity = this.a;
        this.c.a((Activity) this.a.get(), UTConstants.E_IV_LOGIN_FAILURE, this.b, (int) SystemMessageConstants.H5_LOGIN_FAILURE);
        CallbackContext.activity = null;
    }
}
