package com.alipay.mobile.antui.adapter;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: TextButtonListAdapter */
final class i implements OnClickListener {
    final /* synthetic */ int a;
    final /* synthetic */ TextButtonListAdapter b;

    i(TextButtonListAdapter this$0, int i) {
        this.b = this$0;
        this.a = i;
    }

    public final void onClick(View v) {
        if (this.b.itemListener != null) {
            this.b.itemListener.onClick(v, this.a);
        }
    }
}
