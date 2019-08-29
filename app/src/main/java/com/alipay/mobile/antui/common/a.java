package com.alipay.mobile.antui.common;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUPageFooterView */
final class a implements OnClickListener {
    final /* synthetic */ int a;
    final /* synthetic */ AUPageFooterView b;

    a(AUPageFooterView this$0, int i) {
        this.b = this$0;
        this.a = i;
    }

    public final void onClick(View v) {
        if (this.b.linkClickListener != null) {
            this.b.linkClickListener.OnLinkClick(this.a);
        }
    }
}
