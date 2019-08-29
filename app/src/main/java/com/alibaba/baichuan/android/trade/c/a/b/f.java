package com.alibaba.baichuan.android.trade.c.a.b;

import android.app.Activity;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.c.a.a.b.b;
import com.alibaba.baichuan.android.trade.c.a.a.b.c;

public class f implements b {
    public boolean a(c cVar) {
        AlibcLogin.getInstance().logout((Activity) cVar.d.getContext(), new g(this));
        cVar.d.loadUrl(AlibcContext.HOME_URL);
        return true;
    }
}
