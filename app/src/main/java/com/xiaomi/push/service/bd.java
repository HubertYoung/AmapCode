package com.xiaomi.push.service;

import android.content.SharedPreferences.Editor;

class bd implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ Boolean c;
    final /* synthetic */ bc d;

    bd(bc bcVar, String str, String str2, Boolean bool) {
        this.d = bcVar;
        this.a = str;
        this.b = str2;
        this.c = bool;
    }

    public void run() {
        Editor edit = this.d.b.getSharedPreferences(this.a, 4).edit();
        edit.putBoolean(this.b, this.c.booleanValue());
        edit.commit();
    }
}
