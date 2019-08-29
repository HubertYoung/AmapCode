package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUListDialog */
final class aa implements OnClickListener {
    final /* synthetic */ AUListDialog a;

    aa(AUListDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.cancel();
        if (this.a.mNegativeListener != null) {
            this.a.mNegativeListener.onClick(this.a.mCancelBtn);
        }
    }
}
