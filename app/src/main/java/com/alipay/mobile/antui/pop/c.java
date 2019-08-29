package com.alipay.mobile.antui.pop;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUPopTipView */
final class c implements OnClickListener {
    final /* synthetic */ AUPopTipView a;

    c(AUPopTipView this$0) {
        this.a = this$0;
    }

    public final void onClick(View view) {
        if (this.a.tipClickedListener != null) {
            this.a.tipClickedListener.onToolTipClicked(view);
        }
    }
}
