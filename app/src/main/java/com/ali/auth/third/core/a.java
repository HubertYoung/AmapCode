package com.ali.auth.third.core;

import com.ali.auth.third.core.callback.ResultCallback;

final class a implements Runnable {
    final /* synthetic */ Class a;
    final /* synthetic */ ResultCallback b;

    a(Class cls, ResultCallback resultCallback) {
        this.a = cls;
        this.b = resultCallback;
    }

    public final void run() {
        this.b.onSuccess(MemberSDK.getService(this.a));
    }
}
