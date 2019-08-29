package com.ali.auth.third.ui.context;

import android.text.TextUtils;

class b implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ a b;

    b(a aVar, String str) {
        this.b = aVar;
        this.a = str;
    }

    public void run() {
        String str;
        if (TextUtils.isEmpty(this.a)) {
            str = String.format("javascript:window.HavanaBridge.onSuccess(%s);", new Object[]{Integer.valueOf(this.b.b)});
        } else {
            str = String.format("javascript:window.HavanaBridge.onSuccess(%s,'%s');", new Object[]{Integer.valueOf(this.b.b), a.e(this.a)});
        }
        this.b.d(str);
    }
}
