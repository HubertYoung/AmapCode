package com.ali.auth.third.login.handler;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.Session;
import java.lang.ref.WeakReference;

class b implements LoginCallback {
    final /* synthetic */ WeakReference a;
    final /* synthetic */ LoginCallback b;
    final /* synthetic */ LoginActivityResultHandler c;

    b(LoginActivityResultHandler loginActivityResultHandler, WeakReference weakReference, LoginCallback loginCallback) {
        this.c = loginActivityResultHandler;
        this.a = weakReference;
        this.b = loginCallback;
    }

    public void onFailure(int i, String str) {
        KernelContext.executorService.postUITask(new c(this));
    }

    public void onSuccess(Session session) {
        KernelContext.executorService.postUITask(new d(this));
    }
}
