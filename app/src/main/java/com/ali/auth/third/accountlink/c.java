package com.ali.auth.third.accountlink;

import android.app.Activity;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.core.service.MemberExecutorService;

class c implements LoginCallback {
    final /* synthetic */ Activity a;
    final /* synthetic */ a b;

    c(a aVar, Activity activity) {
        this.b = aVar;
        this.a = activity;
    }

    public void onFailure(int i, String str) {
        ((MemberExecutorService) KernelContext.getService(MemberExecutorService.class)).postUITask(new d(this, i, str));
    }

    public void onSuccess(Session session) {
        if (this.a != null && !this.a.isFinishing()) {
            this.a.finish();
        }
        ((MemberExecutorService) KernelContext.getService(MemberExecutorService.class)).postUITask(new e(this, session));
    }
}
