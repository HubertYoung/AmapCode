package com.alipay.mobile.antui.basic;

import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/* compiled from: AUTextView */
final class ao implements OnGlobalLayoutListener {
    final /* synthetic */ AUTextView a;

    private ao(AUTextView aUTextView) {
        this.a = aUTextView;
    }

    /* synthetic */ ao(AUTextView x0, byte b) {
        this(x0);
    }

    public final void onGlobalLayout() {
        try {
            this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } catch (Throwable tr) {
            Log.e("commonui", tr.toString());
        }
    }
}
