package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUImageDialog */
final class t implements OnClickListener {
    final /* synthetic */ AUImageDialog a;

    t(AUImageDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.cancel();
        if (this.a.mCloseBtnClickListener != null) {
            this.a.mCloseBtnClickListener.onClick(v);
        }
    }
}
