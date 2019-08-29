package com.alipay.mobile.antui.picker;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUConfirmPopup */
final class b implements OnClickListener {
    final /* synthetic */ AUConfirmPopup a;

    b(AUConfirmPopup this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.dismiss();
        this.a.onSubmit();
    }
}
