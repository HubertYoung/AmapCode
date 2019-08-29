package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.api.OnItemClickListener;

/* compiled from: AUBaseDialog */
final class g implements OnClickListener {
    final /* synthetic */ OnItemClickListener a;
    final /* synthetic */ AUBaseDialog b;

    g(AUBaseDialog this$0, OnItemClickListener onItemClickListener) {
        this.b = this$0;
        this.a = onItemClickListener;
    }

    public final void onClick(View v) {
        if (this.a != null) {
            this.a.onClick(this.b.mCancelBtn, 1);
        }
    }
}
