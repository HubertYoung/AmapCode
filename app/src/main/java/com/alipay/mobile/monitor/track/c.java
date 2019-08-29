package com.alipay.mobile.monitor.track;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;

/* compiled from: TrackAutoHelper */
class c implements OnAttachStateChangeListener {
    final /* synthetic */ String a;
    final /* synthetic */ TrackAutoHelper b;

    c(TrackAutoHelper this$0, String str) {
        this.b = this$0;
        this.a = str;
    }

    public void onViewDetachedFromWindow(View view) {
        this.b.b.remove(this.a);
    }

    public void onViewAttachedToWindow(View view) {
    }
}
