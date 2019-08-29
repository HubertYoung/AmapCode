package com.alipay.mobile.antui.picker;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUConfirmPopup */
final class c implements OnClickListener {
    final /* synthetic */ AUConfirmPopup a;

    c(AUConfirmPopup this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.dismiss();
        this.a.onCancel();
    }
}
