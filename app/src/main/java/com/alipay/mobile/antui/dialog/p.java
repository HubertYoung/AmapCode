package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUCustomDialog */
final class p implements OnClickListener {
    final /* synthetic */ AUCustomDialog a;

    p(AUCustomDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        if (this.a.closeClickListener != null) {
            this.a.closeClickListener.onClick(v);
        } else {
            this.a.dismiss();
        }
    }
}
