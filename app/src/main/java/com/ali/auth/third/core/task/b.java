package com.ali.auth.third.core.task;

import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.model.ResultCode;

class b implements InitResultCallback {
    final /* synthetic */ InitResultCallback a;
    final /* synthetic */ a b;

    b(a aVar, InitResultCallback initResultCallback) {
        this.b = aVar;
        this.a = initResultCallback;
    }

    public void onFailure(int i, String str) {
        if (this.a != null) {
            this.a.onFailure(i, str);
        }
        this.b.a(false, i, str);
    }

    public void onSuccess() {
        if (this.a != null) {
            this.a.onSuccess();
        }
        this.b.a(true, ResultCode.SUCCESS.code, (String) null);
    }
}
