package com.ali.auth.third.core.task;

import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.util.CommonUtils;

class e implements InitResultCallback {
    final /* synthetic */ InitWaitTask a;

    e(InitWaitTask initWaitTask) {
        this.a = initWaitTask;
    }

    public void onFailure(int i, String str) {
        CommonUtils.onFailure(this.a.failureCallback, i, str);
    }

    public void onSuccess() {
        this.a.a.run();
    }
}
