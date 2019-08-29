package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUListDialog */
final class ab implements OnClickListener {
    final /* synthetic */ AUListDialog a;

    ab(AUListDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.dismiss();
        if (this.a.mPositiveListener != null) {
            this.a.mPositiveListener.onClick(this.a.mEnsureBtn);
        }
    }
}
