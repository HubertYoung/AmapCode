package com.alibaba.baichuan.android.trade.adapter.login;

import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.callback.AlibcLoginCallback;

class b implements LoginCallback {
    final /* synthetic */ AlibcLoginCallback a;
    final /* synthetic */ AlibcLogin b;

    b(AlibcLogin alibcLogin, AlibcLoginCallback alibcLoginCallback) {
        this.b = alibcLogin;
        this.a = alibcLoginCallback;
    }

    public void onFailure(int i, String str) {
        AlibcLogin alibcLogin = this.b;
        StringBuilder sb = new StringBuilder("code = ");
        sb.append(i);
        sb.append(" ,msg=");
        sb.append(str);
        alibcLogin.a(sb.toString());
        this.a.onFailure(i, str);
    }

    public void onSuccess(Session session) {
        this.b.c();
        this.a.onSuccess();
    }
}
