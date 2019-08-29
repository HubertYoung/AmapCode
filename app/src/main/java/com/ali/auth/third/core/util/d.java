package com.ali.auth.third.core.util;

import android.widget.Toast;
import com.ali.auth.third.core.context.KernelContext;

final class d implements Runnable {
    final /* synthetic */ String a;

    d(String str) {
        this.a = str;
    }

    public final void run() {
        Toast.makeText(KernelContext.getApplicationContext(), ResourceUtils.getString(this.a), 0).show();
    }
}
