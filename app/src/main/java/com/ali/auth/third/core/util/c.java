package com.ali.auth.third.core.util;

import com.ali.auth.third.core.callback.FailureCallback;

final class c implements Runnable {
    final /* synthetic */ FailureCallback a;
    final /* synthetic */ int b;
    final /* synthetic */ String c;

    c(FailureCallback failureCallback, int i, String str) {
        this.a = failureCallback;
        this.b = i;
        this.c = str;
    }

    public final void run() {
        if (this.a != null) {
            this.a.onFailure(this.b, this.c);
        }
    }
}
