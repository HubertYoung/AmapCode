package com.alipay.deviceid.module.rpc.mrpc.core;

import android.content.Context;

public final class h extends w {
    /* access modifiers changed from: private */
    public Context a;

    public h(Context context) {
        this.a = context;
    }

    public final <T> T a(Class<T> cls, aa aaVar) {
        return new x(new i(this, aaVar)).a(cls);
    }
}
