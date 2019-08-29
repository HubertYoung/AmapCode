package com.alipay.mobile.antui.basic;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.basic.AUCardInteractView.Interaction;

/* compiled from: AUCardInteractView */
final class c implements OnClickListener {
    final /* synthetic */ Interaction a;
    final /* synthetic */ int b;
    final /* synthetic */ AUCardInteractView c;

    c(AUCardInteractView this$0, Interaction interaction, int i) {
        this.c = this$0;
        this.a = interaction;
        this.b = i;
    }

    public final void onClick(View v) {
        if (this.c.mOnItemClickListener != null) {
            this.c.mOnItemClickListener.onItemClick(v, this.a, this.b);
        }
    }
}
