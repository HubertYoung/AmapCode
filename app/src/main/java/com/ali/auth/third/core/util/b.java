package com.ali.auth.third.core.util;

import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.model.ResultCode;

final class b implements Runnable {
    final /* synthetic */ FailureCallback a;
    final /* synthetic */ ResultCode b;

    b(FailureCallback failureCallback, ResultCode resultCode) {
        this.a = failureCallback;
        this.b = resultCode;
    }

    public final void run() {
        if (this.a != null) {
            this.a.onFailure(this.b.code, this.b.message);
        }
    }
}
