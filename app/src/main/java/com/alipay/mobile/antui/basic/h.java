package com.alipay.mobile.antui.basic;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUFilterMenuView */
final class h implements OnClickListener {
    final /* synthetic */ AUFilterMenuView a;

    h(AUFilterMenuView this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.clearSelect();
    }
}
