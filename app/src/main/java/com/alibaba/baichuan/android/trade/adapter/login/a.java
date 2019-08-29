package com.alibaba.baichuan.android.trade.adapter.login;

import com.ali.auth.third.core.callback.InitResultCallback;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

class a implements InitResultCallback {
    final /* synthetic */ AlibcLogin a;

    a(AlibcLogin alibcLogin) {
        this.a = alibcLogin;
    }

    public void onFailure(int i, String str) {
        StringBuilder sb = new StringBuilder("MemberSDK init error, code: ");
        sb.append(i);
        sb.append(" message: ");
        sb.append(str);
        AlibcLogger.e("AlibcLogin", sb.toString());
    }

    public void onSuccess() {
        AlibcLogger.d("AlibcLogin", "MemberSDK init success");
    }
}
