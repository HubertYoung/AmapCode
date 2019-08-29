package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUInputDialog */
final class z implements OnClickListener {
    final /* synthetic */ AUInputDialog a;

    z(AUInputDialog this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.dismiss();
        if (this.a.mPositiveListener == null) {
            return;
        }
        if (this.a.inputContent.getText() != null) {
            this.a.mPositiveListener.onClick(this.a.inputContent.getText().toString());
        } else {
            this.a.mPositiveListener.onClick(null);
        }
    }
}
