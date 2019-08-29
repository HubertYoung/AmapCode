package com.ali.auth.third.login.task;

import android.text.TextUtils;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.login.UTConstants;
import java.util.HashMap;

class c implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ String b;
    final /* synthetic */ LoginByIVTokenTask c;

    c(LoginByIVTokenTask loginByIVTokenTask, int i, String str) {
        this.c = loginByIVTokenTask;
        this.a = i;
        this.b = str;
    }

    public void run() {
        HashMap hashMap = new HashMap();
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        hashMap.put("code", sb.toString());
        if (!TextUtils.isEmpty(this.b)) {
            hashMap.put("message", this.b);
        }
        ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_H5_LOGIN_FAILURE, hashMap);
        if (this.c.a != null) {
            this.c.a.onFailure(this.a, this.b);
        }
    }
}
