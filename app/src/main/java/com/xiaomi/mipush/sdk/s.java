package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;

final class s implements Runnable {
    final /* synthetic */ Context a;

    s(Context context) {
        this.a = context;
    }

    public final void run() {
        try {
            PackageInfo packageInfo = this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 4612);
            r.c(this.a);
            r.d(this.a, packageInfo);
            r.c(this.a, packageInfo);
        } catch (Throwable unused) {
        }
    }
}
