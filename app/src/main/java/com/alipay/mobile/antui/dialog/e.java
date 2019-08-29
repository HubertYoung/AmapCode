package com.alipay.mobile.antui.dialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/* compiled from: AUAuthorizeDialog */
final class e implements OnCancelListener {
    final /* synthetic */ AUAuthorizeDialog a;

    e(AUAuthorizeDialog this$0) {
        this.a = this$0;
    }

    public final void onCancel(DialogInterface dialog) {
        if (this.a.mOnAuthListener != null) {
            this.a.mOnAuthListener.onCancel();
        }
    }
}
