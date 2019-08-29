package com.xiaomi.push.service;

import android.content.SharedPreferences.Editor;

class be implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ bc d;

    be(bc bcVar, String str, String str2, String str3) {
        this.d = bcVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public void run() {
        Editor edit = this.d.b.getSharedPreferences(this.a, 4).edit();
        edit.putString(this.b, this.c);
        edit.commit();
    }
}
