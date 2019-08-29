package com.huawei.android.pushselfshow.richpush.favorites;

import com.huawei.android.pushagent.a.a.c;

class b implements Runnable {
    final /* synthetic */ FavoritesActivity a;

    b(FavoritesActivity favoritesActivity) {
        this.a = favoritesActivity;
    }

    public void run() {
        c.a("PushSelfShowLog", "onCreate mThread run");
        this.a.h.b();
        this.a.a.sendEmptyMessage(1000);
    }
}
