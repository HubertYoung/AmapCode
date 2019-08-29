package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUImageDialog */
final class s implements OnClickListener {
    final /* synthetic */ AUImageDialog a;

    s(AUImageDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.cancel();
    }
}
