package com.alipay.sdk.util;

import android.app.Activity;

final class n implements Runnable {
    final /* synthetic */ Activity a;

    n(Activity activity) {
        this.a = activity;
    }

    public final void run() {
        this.a.finish();
    }
}
