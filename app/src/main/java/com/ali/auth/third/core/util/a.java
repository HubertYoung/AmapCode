package com.ali.auth.third.core.util;

import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.message.Message;

final class a implements Runnable {
    final /* synthetic */ FailureCallback a;
    final /* synthetic */ Message b;

    a(FailureCallback failureCallback, Message message) {
        this.a = failureCallback;
        this.b = message;
    }

    public final void run() {
        if (this.a != null) {
            this.a.onFailure(this.b.code, this.b.message);
        }
    }
}
