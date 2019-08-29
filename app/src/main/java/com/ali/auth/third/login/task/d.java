package com.ali.auth.third.login.task;

import com.ali.auth.third.core.context.KernelContext;

class d implements Runnable {
    final /* synthetic */ LoginByIVTokenTask a;

    d(LoginByIVTokenTask loginByIVTokenTask) {
        this.a = loginByIVTokenTask;
    }

    public void run() {
        if (this.a.a != null) {
            this.a.a.onSuccess(KernelContext.credentialService.getSession());
        }
    }
}
