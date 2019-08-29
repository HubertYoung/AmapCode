package com.alipay.mobile.antui.bar;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUVerticalTabView */
final class a implements OnClickListener {
    final /* synthetic */ AUVerticalTabView a;

    a(AUVerticalTabView this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Integer) {
            this.a.setSelected(((Integer) tag).intValue());
            if (this.a.mOnItemClickListener != null) {
                this.a.mOnItemClickListener.onItemClick(v, ((Integer) tag).intValue());
            }
        }
    }
}
